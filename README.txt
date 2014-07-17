
Git-Repository: git@github.com:mfroehlich/flightplan2.git

Diese Datei geht davon aus, dass der Code aus dem Repository heruntergeladen wurde, da sie selbst Teil des Repositories ist :-)
Im folgenden wird der Ordner des Hauptprojekts dieses heruntergeladenen Codes als %HAUPTORDNER% referenziert.


Voraussetzung:
	* Maven 3 ist installiert: http://maven.apache.org/download.cgi
    * Java 8 ist installiert: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
    * MySQL 5.6 ist installiert: http://dev.mysql.com/downloads/mysql/
    * Wildfly 8.0.0-Final ist installiert: http://wildfly.org/downloads/


Einrichten der Datenbank

    * Datenbank sollte �ber Port 3306 erreichbar sein
    	-> falls anderer Port gew�hlt wird, bitte entsprechend bei der Einrichtung der DataSources beachten!
    	
    * Es sollte ein spezieller Datenbankuser angelegt sein, welcher dann beim Einrichten der DataSources angegeben werden kann.
    	
	* Zur Initialisierung der Test-Datenbank folgendes Skript ausf�hren: %HAUPTORDNER%/flightplan-assembly/database/create_datamodel_test.sql
	* Zur Initialisierung der Live-Datenbank folgendes Skript ausf�hren: %HAUPTORDNER%/flightplan-assembly/database/create_datamodel.sql
	
	* Herunterladen des Treibers: mysql-connector-java-5.1.29-bin.jar
      Link: http://dev.mysql.com/downloads/connector/j/5.1.html
	 
 	
Einrichten des ApplicationServers

	* standalone.xml anpassen mit Snippets 
		ACHTUNG: Hierf�r vorher den Wildfly herunterfahren, dass dieser die standalone.xml beim Herunterfahren nicht wieder �berschreibt!
		-> Einf�gen der Snippets ist beschrieben in %HAUPTORDNER%/flightplan-assembly/applicationserver/standalone-snippets.txt
		
    * standalone.xml:
    	-> Folgenden Eintrag entfernen: <default-security-domain value="other"/> 
    	
    ### Wildfly starten ###

	* Mysql-Driver in Wildfly bekanntmachen
		-> den heruntergeladenen Treiber in folgenden Ordner ablegen: %WILDFLY-Installationsverzeichnis%/standalone/deployments
		-> Wildfly f�hrt dann ein Auto-Deployment durch

	* DataSources einrichten
		-> DataSources k�nnen �ber die Admin-Console des Wildfly administriert werden: http://localhost:9990
		-> Einrichtung der DataSources ist beschrieben in %HAUPTORDNER%/flightplan-assembly/applicationserver/datasources.txt
					
 
Vorbedingungen:
 	* Datenbank starten
 	* Wildfly starten
 	  WICHTIG: sicherstellen, dass die Applikation nicht bereits im Wildfly installiert ist (sonst laufen die Tests beim Installieren nicht)
 	  => dieses Problem tritt nur auf, wenn man denselben ApplicationServer sowohl zum Bauen/Testen verwendet als auch zum Deployen verwendet.

Installationsanleitung
	
	(1) In den Ordner des Hauptprojekts %HAUPTORDNER% wechseln
	(2) Folgenden Befehl ausf�hren: "mvn clean install"
	
	(3) In den Unterordner "flightplan-assembly" wechseln
	(4) Folgenden Befehl ausf�hren: "mvn install"
		=> Ergebnis: in %Hauptordner%/flightplan-assembly/target liegt eine Datei: flightplan-assembly.zip
	
	(5) Datei flightplan-assembly.zip an beliebiger Stelle entpacken
	    Im Unterordner assembly liegen folgende Dateien:
	     * flightplan-ear-VERSION.ear
	     * flightplan-frontend-VERSION.jar
	  
	(6) flightplan-ear-VERSION.ear im Wildfly deployen
		daf�r: 
		 * Datei ablegen in %WILDFLY-Installationsverzeichnis%/standalone/deployments
		 * Wildfly f�hrt automatisch das deployment durch
		 
	(7) flightplan-frontend-VERSION.jar starten (executable jar)
	    => Die Benutzeroberfl�che startet	