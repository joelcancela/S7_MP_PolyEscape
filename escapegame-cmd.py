#!/usr/bin/python3

import base64
import os
import signal
import sys
import configparser
from subprocess import call, Popen
from datetime import datetime

__author__ = "Nikita ROUSSEAU"
__copyright__ = "Copyright 2018, PolyEscape IT Group"
__credits__ = ["Nikita Rousseau"]
__license__ = "Proprietary - PolyEscape IT Group"
__version__ = ""
__maintainer__ = "Nikita Rousseau"
__email__ = "nikita.rousseau@etu.unice.com"
__status__ = "development"

"""
Private vars repository
"""
_git_url = ""  # Remote GIT URL
_git_branch = ""  # Git branch to clone

_wd = ""  # EscapeGame working directory

_lock_file = os.path.dirname(os.path.realpath(__file__)) + "/" + ".lock"
_config_file = os.path.dirname(os.path.realpath(__file__)) + "/" + "escapegame.ini"

_current_user = os.getlogin()
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


def confirm(war):
    """
    Interactive helper in order to confirm some action
    :param war: the warning message
    """
    print(war)

    if str(input("(yes)|(no) : ")) in ['y', 'Y', 'yes', 'Yes', 'YES']:
        return True
    return False


def motd():
    """Print message of the day"""
    print("""
 _______                              ______                   
(_______)                            / _____)                  
 _____    ___  ____ ____ ____   ____| /  ___  ____ ____   ____ 
|  ___)  /___)/ ___) _  |  _ \ / _  ) | (___)/ _  |    \ / _  )
| |_____|___ ( (__( ( | | | | ( (/ /| \____/( ( | | | | ( (/ / 
|_______|___/ \____)_||_| ||_/ \____)\_____/ \_||_|_|_|_|\____)
                        |_|                                    
""")


def bootstrap():
    """Load EscapeGame configuration and check dependencies (Git, etc.)"""
    global __version__

    global _git_url
    global _git_branch

    global _wd

    if not os.path.exists(_config_file):
        print("[ERROR] Error reading configuration file '" + _config_file + "' (not found)")
        bye(1)

    config = configparser.ConfigParser()
    config.read(_config_file)

    # GIT URL

    user = config_section_map("GIT", config)['user']
    base_url = config_section_map("GIT", config)['url']
    _git_url = ("https://{0}@" + base_url).format(
        user
    )
    _git_branch = config_section_map("GIT", config)['branch']

    # WORKING DIRECTORIES AND PATH

    _wd = config_section_map("ESCAPEGAME", config)['working_dir']

    # SOFTWARE VERSION

    __version__ = config_section_map("VERSION", config)['version']

    print("[INFO] Starting EscapeGame (" + __version__ + ")")
    print("[DATE] " + str(datetime.now()))
    print("[VERSION] " + __version__)
    print("[INFO] Checking Git...")
    try:
        call(["git", "version"])
    except OSError as e:
        print("Error : Git not installed. " + str(e))
        bye(1)


def update():
    """Delete old EscapeGame project and update it from GIT"""
    global _wd

    global _git_branch
    global _git_url

    if not confirm("This operation will delete all project files in '" + _wd + "' and will update them, continue ?"):
        return

    if not confirm("/!\ [WARNING] /!\ - Uncommitted items at '" + _wd + "' will be lost, continue ?"):
        return

    if create_lock() == 1:
        return

    print("[INFO] Updating project...")
    print("[INFO] `EscapeGame` is located at : '" + _wd + "'")

    print(" * Flushing project...")
    if os.path.exists(_wd):
        os.system("rm -rf " + _wd)

    print(" * Downloading remote repository (" + _git_branch + ")...")
    os.system("git clone -b " + _git_branch + " " + _git_url + " " + _wd)

    print(" * Fix pom.xml (polyescape_engine module)...")
    os.system("mv " + _wd + "/polyescape_engine/pom.xml " + _wd + "/polyescape_engine/pom.xml.bak")
    os.system("cp ~/catalina/pom.xml " + _wd + "/polyescape_engine")

    free_lock()

    print("[INFO] Update OK.")


def clean():
    """Clean EscapeGame repository"""

    if create_lock() == 1:
        return

    print("[INFO] Cleaning project...")

    if os.path.exists(_wd):
        os.system("mvn clean -f " + _wd + "/polyescape_engine/pom.xml")

    free_lock()

    print("[INFO] Clean OK.")


def start():
    """Start the EscapeGame service"""

    print("[INFO] Start server...")

    if os.path.exists(_wd):
        os.system("mvn install -f " + _wd + "/polyescape_engine/pom.xml -Dmaven.test.skip=true")
        os.system("screen mvn tomcat7:run-war -f " + _wd + "/polyescape_engine/pom.xml -Dmaven.test.skip=true")


def bye(exit_code=0):
    """Exit safely the program and release any locker"""

    free_lock()
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
    'update': update,
    'clean': clean,
    'start': start,
    'exit': bye
}

# Main function
if __name__ == '__main__':
    """Main CLI entry-point."""

    # Application bootstrap
    bootstrap()

    motd()

    print("")
    print("Welcome to the EscapeGame CLI (" + __version__ + ")")
    print("Nikita Rousseau - EscapeGame IT Group - 2018")
    print("")

    help_cmd()
    print("")

    # Cmd main loop
    while True:
        com = input(_current_user + "@EscapeGame:" + _wd + " # ")
        if com == '':
            continue
        if com in commands:
            commands[com]()
        else:
            print("Command not found")
            help_cmd()
