# VerteilteAnwendungenKartenverkauf
Erstellt mit

adopt-openj9-1.8

IntelliJ Idea Ultimate 2020.3

## Diese Anwendung nutzt ursprünglich:

Tomcat 9.0.40

10.4.16-MariaDB

[mysql-connector-java-8.0.22.jar](https://dev.mysql.com/downloads/connector/j/)

(Genauere Details in der pom.xml)


## Tabellenaufbau:

"tickets" : (`id` TINYINT NOT NULL, `state` VARCHAR(9) NOT NULL, `name` VARCHAR(100), PRIMARY KEY (id))

"settings" : (`id` VARCHAR(20) NOT NULL, `state` TINYINT(1) NOT NULL, PRIMARY KEY (id))


