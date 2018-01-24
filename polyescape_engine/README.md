# PolyEscape Engine

## TODO

- Display all possibles answers for QCM
- Services using "input services" can't be made as plugins for now because we haven't found yet a way to create dynamic routes

## Notes

- QCM is case sensitive (equalsIgnoreCase TODO)
- Mail question (Zappier) is case sensitive too and the content of the message must only be the answer.


### How to create a plugin/service

- Create a class in src folder
- Extends either Plugin or Service
  - Constaints :

        * For plugin your constructor args must start with String description, followed by your attributes sorted by types (from java.lang) descending and sorted by name ascending
        (i.e. public CaesarCipherPlugin(String description, String plain_text, Integer cipher_padding))

- Override the corresponding methods
- Compile your class with the project
- Copy the class file from target in another folder (out of the project)
- Create a ini file matching those requirements :

```ini
[<Classname>]
arg_name_1=String
arg_name_2=Integer

[SCHEMA] (i.e. how to complete the plugin)
attempt=String

; Dependencies

[PLUGINS]
; plugins[] = plug

[SERVICES]
; services[] = service
```

- Your ini file and your class file should have the same name
- Zip them together, change the extension to jar
- Put your jar to /resources/plugins or services
- Reboot the server