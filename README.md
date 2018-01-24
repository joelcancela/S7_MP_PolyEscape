# PolyEscape Team F

Nécessite maven, Node.js, grunt et bower

Lancement du serveur :

```bash
cd polyescape_engine
mvn install && mvn tomcat7:run-war
```
Serveur lancé par défaut sur le port 8080


Lancement de l'interface utilisateur :

```bash
cd polyescape_website

    //Si grunt et bower ne sont pas installés
    npm install -g grunt
    npm install -g bower

npm install
bower install
grunt serve
```
Interface lancée par défaut sur le port 9000

Documentation du moteur de jeu:

```bash
cd doc
./index.html
```

Gestion de la documentation: (avec apidoc)

* Installation d'*apidoc*

```bash
npm install -g apidoc
```

* Régénération de la documentation


```bash
apidoc -i polyescape_engine
```

Exemples de configuration d'Escape Games disponibles dans *escape_games_cfg_samples*
