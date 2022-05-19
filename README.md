# Examen Java EE

Le sujet choisi est le sujet 2.

## Installation

Compiler le projet avec Maven :

```bash
$ mvn compile
```

Dans le dossier `webapps` de Tomcat, créer un dossier `examen` puis à
l'intérieur du dossier `examen`, créer un autre dossier `WEB-INF`. Placer
ensuite les fichiers `app.html` et `web.xml` et le dossier `classes` dans le
dossier `examen` selon cette structure :

```
examen/
 |
 |-- app.html
 |
 |-- WEB-INF/
        |
        |-- web.xml
        |
        |-- classes
```

Le dossier `classes` se trouve après la compilation Maven dans le dossier
`target` à la racine du dépôt git.

Télécharger le [driver JDBC pour sqlite3](https://github.com/xerial/sqlite-jdbc/releases)
et placer le fichier `sqlite-jdbc.jar` dans le dossier `lib` de Tomcat.

## Lancement

Après avoir démarré Tomcat, ouvrir un navigateur web et se connecter sur
http://localhost:8080/examen/app.html
