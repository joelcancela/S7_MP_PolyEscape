import base64
import re
import shutil
import os
import signal
import sys
import configparser
import argparse
import zipfile
import time
from xml.dom import minidom
from subprocess import call, Popen
from datetime import datetime

__author__ = "Nikita ROUSSEAU"
__copyright__ = "Copyright 2017, Amadeus IT Group"
__credits__ = ["Nikita Rousseau"]
__license__ = "Proprietary - Amadeus IT Group"
__version__ = ""
__maintainer__ = "Nikita Rousseau"
__email__ = "nikita.rousseau@amadeus.com"
__status__ = "development"


"""
Private vars repository
"""
_git_url = ""  # Remote GIT URL
_git_branch = ""  # Git branch to clone
_wd = ""  # Neodeus working directory
_build_dir = ""  # Build directory where are located compiled and packaged jobs
_workspace_dir = ""  # Talend workspace directory where is located the `NEODEUS` project
_project_dir = ""  # Talend `NEODEUS` project directory
_temp_dir = ""  # Neodeus temporary directory

_talend_dir = ""  # TOS directory
_talend_bin = ""  # TOS binary

_lock_file = os.path.dirname(os.path.realpath(__file__)) + "\\" + ".lock"
_config_file = os.path.dirname(os.path.realpath(__file__)) + "\\" + "neodeus.ini"
_manifest_file = os.path.dirname(os.path.realpath(__file__)) + "\\" + "neodeus-manifest.xml"

_current_user = os.getlogin()
_run_as_service = False
_logging_date = datetime.now().strftime("%Y-%b-%d_%H-%M")


def sigint_handler(signal, frame):
    """
    Catch CTR+C / KILL signals
    Do housekeeping before leaving
    """
    bye()
signal.signal(signal.SIGINT, sigint_handler)
signal.signal(signal.SIGTERM, sigint_handler)


def create_lock():
    """
    Create a ".lock" if it does not exist
    Exit the program with error code "1" if it exists, "0" otherwise
    """
    # verify if the application is locked
    if os.path.exists(_lock_file):
        lock = open(_lock_file, 'r')
        lock_user = lock.readline().split(":")[1].strip()
        lock.close()
        print("[ERROR] Process locked by user '" + lock_user + "'.")
        return 1
    # create lock
    lock = open(_lock_file, 'w')
    lock.write(str(os.getpid()) + ":" + _current_user)
    lock.close()
    return 0


def free_lock():
    """
    Remove a lock only if it exists
    AND
    It is this instance of the program that made it
    """
    if os.path.exists(_lock_file):
        lock = open(_lock_file, 'r')
        lock_pid = int(lock.readline().split(":")[0].strip())
        lock.close()
        if lock_pid == os.getpid():
            os.remove(_lock_file)


def config_section_map(section, config_obj):
    """
    Helper function
    fetch the configuration value from a section + setting
    :param section:
    :param config_obj: Configuration Object
    :return:
    """
    d = {}
    options = config_obj.options(section)
    for option in options:
            d[option] = config_obj.get(section, option)
    return d


def del_rw(func, path, exec_info):
    """
    Callback function, called in order to force deletion of read only files
    :param func:
    :param path: file path
    :param exec_info:
    :return:
    """
    os.chmod(path, 128)  # force shutil.rmtree() to delete file by overriding perms
    os.remove(path)


def confirm(war):
    """
    Interactive helper in order to confirm some action
    :param war: the warning message
    """
    if _run_as_service:
        return True

    print(war)

    if str(input("(yes)|(no) : ")) in ['y', 'Y', 'yes', 'Yes', 'YES']:
        return True
    return False


def motd():
    """Print message of the day"""
    print("""
    _   __               __               
   / | / /__  ____  ____/ /__  __  _______
  /  |/ / _ \/ __ \/ __  / _ \/ / / / ___/
 / /|  /  __/ /_/ / /_/ /  __/ /_/ (__  ) 
/_/ |_/\___/\____/\____/\___/\____/____/  
""")


def bootstrap():
    """Load Neodeus configuration and check dependencies (Git, etc.)"""
    global __version__

    global _git_url
    global _git_branch
    global _wd
    global _build_dir
    global _workspace_dir
    global _project_dir
    global _temp_dir

    global _talend_dir
    global _talend_bin

    if not os.path.exists(_config_file):
        print("[ERROR] Error reading configuration file '" + _config_file + "' (not found)")
        bye(1)

    config = configparser.ConfigParser()
    config.read(_config_file)

    if not os.path.exists(_manifest_file):
        print("[ERROR] Error reading manifest file '" + _manifest_file + "' (not found)")
        bye(1)

    # GIT URL

    user = config_section_map("GIT", config)['user']
    # avoid plaintext password...
    password = base64.standard_b64decode(config_section_map("GIT", config)['password']).decode('utf-8')
    base_url = config_section_map("GIT", config)['url']
    _git_url = ("https://{0}:{1}@" + base_url).format(
        user,
        password
    )
    _git_branch = config_section_map("GIT", config)['branch']

    # WORKING DIRECTORIES AND PATH

    _wd = config_section_map("NEO", config)['working_dir']
    _build_dir = _wd + "/build"
    _workspace_dir = config_section_map("NEO", config)['workspace_dir']
    _project_dir = _workspace_dir + "/NEODEUS"
    _temp_dir = config_section_map("NEO", config)['temp_dir']

    # TALEND

    _talend_dir = config_section_map("NEO", config)['talend_dir']
    _talend_bin = config_section_map("NEO", config)['talend_bin']

    # SOFTWARE VERSION

    __version__ = config_section_map("VERSION", config)['version']

    print("[INFO] Starting Neodeus (" + __version__ + ")")
    print("[DATE] " + str(datetime.now()))
    print("[VERSION] " + __version__)
    print("[INFO] Checking Git...")
    try:
        call(["git", "version"])
    except OSError as e:
        print("Error : Git not installed. " + str(e))
        bye(1)


def update():
    """Delete old Neodeus project (located in Talend workspace) and update it from GIT"""
    global _project_dir
    global _temp_dir
    global _build_dir
    global _git_branch
    global _git_url

    if not confirm("This operation will delete all project files (plus `build`) and will update them, continue ?"):
        return

    if not confirm("/!\ [WARNING] /!\ - Uncommitted items at '" + _project_dir + "' will be lost, continue ?"):
        return

    if create_lock() == 1:
        return

    print("[INFO] Updating project...")
    print("[INFO] `NEODEUS` is located at : '" + _project_dir + "'")

    print(" * Flushing project...")
    if os.path.exists(_project_dir):
        shutil.rmtree(_project_dir, onerror=del_rw)

    print(" * Flushing temp...")
    if os.path.exists(_temp_dir):
        shutil.rmtree(_temp_dir, onerror=del_rw)

    print(" * Flushing build...")
    if os.path.exists(_build_dir):
        shutil.rmtree(_build_dir, onerror=del_rw)

    os.makedirs(_temp_dir)

    print(" * Downloading remote repository (" + _git_branch + ")...")
    os.system("git clone -b " + _git_branch + " " + _git_url + " " + _temp_dir)

    # Move project to its destination
    if os.path.exists(_temp_dir + "/project/NEODEUS"):
        shutil.copytree(_temp_dir + "/project/NEODEUS", _project_dir)
    # Move script files to build repository
    if os.path.exists(_temp_dir + "/scripts"):
        shutil.copytree(_temp_dir + "/scripts", _build_dir)

    # Cleaning temp
    if os.path.exists(_temp_dir):
        shutil.rmtree(_temp_dir, onerror=del_rw)

    free_lock()

    print("[INFO] Update OK.")


def clean_talend_temp():
    """Clean Talend_XXXX.xxx files in %TMP%"""

    print("[INFO] Cleaning `%TMP%` directory '" + os.environ["TMP"] + "'...")

    files = os.listdir(os.environ["TMP"])
    for file in files:
        if os.path.isfile((os.environ["TMP"] + '/' + file)):
            if re.search('talend_[0-9A-Z]{4}\.[a-zA-Z]+', file) is not None:
                try:
                    os.remove(os.environ["TMP"] + '/' + file)
                except OSError as e:
                    print("[WARNING] Unable to delete file '" + file + "' : " + str(e))
        else:
            if re.search('temp[0-9]*\.tmp', file) is not None:
                try:
                    shutil.rmtree(os.environ["TMP"] + '/' + file)
                except OSError as e:
                    print("[WARNING] Unable to delete folder '" + file + "' : " + str(e))


def clean():
    """Clean Neodeus temporary repositories"""
    global _temp_dir

    if create_lock() == 1:
        return

    print("[INFO] Cleaning `temp` repository at '" + _temp_dir + "'...")

    if os.path.exists(_temp_dir):
        shutil.rmtree(_temp_dir, onerror=del_rw)

    # clean talend specific stuff
    clean_talend_temp()

    free_lock()

    print("[INFO] Clean OK.")


def build():
    """Build and clean ALL jobs from the project workspace using codegen JAR ; unpack built zip."""
    global _run_as_service
    global _workspace_dir
    global _project_dir
    global _build_dir
    global _manifest_file

    global _logging_date

    global _talend_dir
    global _talend_bin

    build_list = minidom.parse(_manifest_file).getElementsByTagName('build')

    if not confirm("This operation will rebuild all Neodeus jobs (it may take some time, up to ~50 min), continue ?"):
        return

    if create_lock() == 1:
        return

    # verify that talend config is working
    if not os.path.exists(_talend_dir + "/" + _talend_bin):
        print("[ERROR] Bad Talend configuration !")
        print("[ERROR] Build KO.")
        free_lock()
        return 1

    echo_build_list()

    # For each job to build
    for j in build_list:
        j = j.attributes['name'].value

        # verify that job is valid
        if not os.path.exists(_project_dir + "/process/" + j + "_0.1.item"):
            print("[WARNING] Skipping '" + j + "' (job not found)")
            continue

        # verify if we need to build first
        if os.path.exists(_build_dir + '/' + j + "/jobInfo.properties") \
                and os.path.exists(_build_dir + '/' + j + '/' + j + '/neodeus'):
            ref_time = os.path.getmtime(_project_dir + "/process/" + j + "_0.1.item")  # MTIME
            target_time = os.path.getctime(_build_dir + '/' + j + "/jobInfo.properties")  # CTIME
            if target_time > ref_time:
                print("[INFO] Skipping '" + j + "' (job already up-to-date)")
                continue

        print("[INFO] Building '" + j + "'...")

        # clean previous build
        if os.path.exists(_build_dir + '/' + j):
            shutil.rmtree(_build_dir + '/' + j, onerror=del_rw)
        os.makedirs(_build_dir + '/' + j)

        # building
        try:
            if _run_as_service is True:
                # prepare logs
                log_path = os.path.dirname(os.path.realpath(__file__)) + "/logs/" + _logging_date + "/build"
                if not os.path.exists(log_path):
                    os.makedirs(log_path)

                logfile = open(log_path + "/" + j + ".txt", "w+")

                print("[INFO] Logfile is at : " + logfile.name)

                p = Popen([_talend_dir + "/" + _talend_bin,
                           '-nosplash',
                           '--launcher.suppressErrors',
                           '-data',
                           _workspace_dir,
                           '-application',
                           'au.org.emii.talend.codegen.Generator',
                           '-jobName',
                           j,
                           '-projectDir',
                           _project_dir,
                           '-targetDir',
                           _build_dir], shell=True, universal_newlines=True, stdout=logfile)
                return_code = p.wait()
                logfile.flush()
            else:
                p = Popen([_talend_dir + "/" + _talend_bin,
                           '-nosplash',
                           '--launcher.suppressErrors',
                           '-data',
                           _workspace_dir,
                           '-application',
                           'au.org.emii.talend.codegen.Generator',
                           '-jobName',
                           j,
                           '-projectDir',
                           _project_dir,
                           '-targetDir',
                           _build_dir], universal_newlines=True)
                return_code = p.wait()

            # Unpack
            print("[INFO] Unpacking...")
            job_pack = _build_dir + "/" + j + "_Latest.zip"
            with zipfile.ZipFile(job_pack, 'r') as zip_ref:
                zip_ref.extractall(_build_dir + "/" + j)
                zip_ref.close()

            # Clean pack
            os.remove(job_pack)

            # Fast integrity check
            if not os.path.exists(_build_dir + '/' + j + '/' + j + '/neodeus'):
                return_code = 1

            # check execution
            if return_code is not 0:
                print("[ERROR] Error while building job '" + j + "'")
                print("[ERROR] Build KO.")
                free_lock()
                return 1
            else:
                print("[INFO] Done.")

        except OSError as e:
            print("[CRITICAL] Neodeus critical error while building job '" + j + "'. Aborting...")
            print("[CRITICAL] Exception : " + str(e))
            print("[CRITICAL] Build KO.")
            bye(1)

    free_lock()

    print("[INFO] Build OK.")


def run():
    """Execute ALL jobs from the manifest file"""
    global _run_as_service
    global _build_dir
    global _manifest_file

    global _logging_date

    job_list = minidom.parse(_manifest_file).getElementsByTagName('job')

    if not confirm("This operation will execute all jobs, continue ?"):
        return

    if create_lock() == 1:
        return

    print("[INFO] Running Jobs...")

    echo_job_list()

    # For each job
    for j in job_list:
        j = j.attributes['name'].value

        # script path path
        j_bat = _build_dir + '/' + j + '/' + j + '/' + j + '_run.bat'

        # verify that job exists
        if not os.path.exists(j_bat):
            print("[WARNING] Skipping '" + j + "' (job not found)")
            continue

        # executing
        print("[INFO] Executing batch '" + j_bat + "'")
        try:
            if _run_as_service is True:
                # prepare logs

                logfile = open(os.path.dirname(os.path.realpath(__file__)) +
                               "/logs/" +
                               _logging_date +
                               "/" +
                               j +
                               ".txt",
                               "w+")

                print("[INFO] Logfile is at : " + logfile.name)

                p = Popen([j_bat], shell=True, universal_newlines=True, stdout=logfile)
                return_code = p.wait()
                logfile.flush()
            else:
                p = Popen([j_bat], universal_newlines=True)
                return_code = p.wait()

            # check execution
            if return_code is not 0:
                print("[ERROR] Error while executing job '" + j + "'")
                print("[ERROR] Run KO")
                free_lock()
                return 1
            else:
                print("[INFO] Done.")

        except OSError as e:
            print("[CRITICAL] Neodeus critical error while executing job '" + j + "'. Aborting...")
            print("[CRITICAL] Exception : " + str(e))
            print("[CRITICAL] Run KO")
            bye(1)

    free_lock()

    print("[INFO] Run OK.")


def start_service():
    """Start the Neo4j service"""

    print("[INFO] Trying to start 'neo4j' service...")
    try:
        call(["net", "start", "neo4j"])
    except OSError as e:
        print("[ERROR] Service start-up failed. " + str(e))
        bye(1)


def stop_service():
    """Stop the Neo4j service"""

    print("[INFO] Trying to stop 'neo4j' service...")
    try:
        call(["net", "stop", "neo4j"])
    except OSError as e:
        print("[ERROR] Service stop failed. " + str(e))
        bye(1)


def echo_build_list():
    """Print the build table"""
    global _manifest_file

    build_list = minidom.parse(_manifest_file).getElementsByTagName('build')

    print("Build table : ")
    for j in build_list:
        j = j.attributes['name'].value
        print(" * " + j)


def echo_job_list():
    """Print the job chrono table (execution order of jobs)"""
    global _manifest_file

    job_list = minidom.parse(_manifest_file).getElementsByTagName('job')

    print("Chrono table : ")
    for j in job_list:
        j = j.attributes['name'].value
        print(" * " + j)


def get_datetime():
    """Helper function that displays the current date"""

    print(_logging_date)


def bye(exit_code=0):
    """Exit safely the program, clean and release any locker"""

    clean()
    free_lock()
    time.sleep(1)
    sys.exit(exit_code)


def help_cmd():
    """Print available CLI commands"""

    print("Allowed commands :")
    for c in commands:
        print(" * " + c + " : " + commands[c].__doc__)


"""
Public CLI commands
"""
commands = {
    'help': help_cmd,
    'sh-crontab': echo_job_list,
    'sh-build': echo_build_list,
    'update': update,
    'build': build,
    'run': run,
    'clean': clean,
    'start-service': start_service,
    'stop-service': stop_service,
    'exit': bye
}


# Main function
if __name__ == '__main__':
    """Main CLI entry-point."""

    # Check args
    parser = argparse.ArgumentParser()
    parser.add_argument("--service",
                        help="skip Neodeus confirmation messages",
                        action="store_true")
    parser.add_argument("--clean",
                        help="do housekeeping",
                        action="store_true")
    parser.add_argument("--update",
                        help="update Neodeus project repository",
                        action="store_true")
    parser.add_argument("--build",
                        help="build Neodeus jobs from the project repo",
                        action="store_true")
    parser.add_argument("--run",
                        help="execute all Neodeus jobs",
                        action="store_true")
    parser.add_argument("--date",
                        help="fetch the current date and exit the program",
                        action="store_true")
    args = parser.parse_args()

    # Helpers
    if args.service:
        _run_as_service = True
    if args.date:
        get_datetime()
        sys.exit(0)

    # Application bootstrap
    bootstrap()

    if args.clean:
        clean()
    if args.update:
        update()
    if args.build:
        build()
    if args.run:
        run()

    # if any argument was given
    # kill program here
    if len(sys.argv) > 1:
        bye(0)

    motd()

    print("")
    print("Welcome to the Neodeus CLI (" + __version__ + ")")
    print("Nikita Rousseau - Amadeus IT Group - 2017")
    print("")

    help_cmd()
    print("")

    # Cmd main loop
    while True:
        com = input(_current_user + "@Neodeus:" + _wd + " # ")
        if com == '':
            continue
        if com in commands:
            commands[com]()
        else:
            print("Command not found")
            help_cmd()
