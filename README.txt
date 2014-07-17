
Git-Repository: git@github.com:mfroehlich/flightplan2.git

Diese Datei geht davon aus, dass der Code aus dem Repository heruntergeladen wurde, da sie selbst Teil des Repositories ist :-)
Im folgenden wird der Ordner des Hauptprojekts dieses heruntergeladenen Codes als %HAUPTORDNER% referenziert.


Voraussetzung:
	* Maven 3 ist installiert: http://maven.apache.org/download.cgi
    * Java 8 ist installiert: http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
    * MySQL 5.6 ist installiert: http://dev.mysql.com/downloads/mysql/
    * Wildfly 8.0.0-Final ist installiert: http://wildfly.org/downloads/


Einrichten der Datenbank
    * Datenbank sollte über Port 3306 erreichbar sein
    	-> falls anderer Port gewählt wird, bitte entsprechend bei der Einrichtung der DataSources beachten!
    	
	* Zur Initialisierung der Test-Datenbank folgendes Skript ausführen: %HAUPTORDNER%/flightplan-assembly/database/create_datamodel_test.sql
	* Zur Initialisierung der Live-Datenbank folgendes Skript ausführen: %HAUPTORDNER%/flightplan-assembly/database/create_datamodel.sql
	
	* Herunterladen des Treibers: mysql-connector-java-5.1.29-bin.jar
      Link: http://dev.mysql.com/downloads/connector/j/5.1.html
	 
 	
Einrichten des ApplicationServers
	* DataSources einrichten
		-> DataSources können über die Admin-Console des Wildfly administriert werden: http://localhost:9990
		-> Einrichtung der DataSources ist beschrieben in %HAUPTORDNER%/flightplan-assembly/applicationserver/datasources.txt

	* Mysql-Driver in Wildfly bekanntmachen
		-> unterhalb von %WILDFLY-HOME%/modules den Ordner org/mysql/main anlegen
		-> in diesen Ordner folgende Dateien legen:
			* mysql-connector-java-5.1.29-bin.jar
			* module.xml (Inhalt siehe %HAUPTORDNER%/flightplan-assembly/applicationserver/module.xml)
			
	* standalone.xml anpassen mit Snippets 
		-> Einfügen der Snippets ist beschrieben in %HAUPTORDNER%/flightplan-assembly/applicationserver/standalone-snippets.txt
		
 
Vorbedingungen:
 	* Datenbank starten
 	* Wildfly starten
 	  WICHTIG: sicherstellen, dass die Applikation nicht bereits im Wildfly installiert ist (sonst laufen die Tests beim Installieren nicht)
 	  => dieses Problem tritt nur auf, wenn man denselben ApplicationServer sowohl zum Bauen/Testen verwendet als auch zum Deployen verwendet.

Installationsanleitung
	
	(1) In den Ordner des Hauptprojekts %HAUPTORDNER% wechseln
	(2) Folgenden Befehl ausführen: "mvn clean install"
	
	(3) In den Unterordner "flightplan-assembly" wechseln
	(4) Folgenden Befehl ausführen: "mvn install"
		=> Ergebnis: in %Hauptordner%/flightplan-assembly/target liegt eine Datei: flightplan-assembly.zip
	
	(5) Datei flightplan-assembly.zip an beliebiger Stelle entpacken
	    Im Unterordner assembly liegen folgende Dateien:
	     * flightplan-ear-VERSION.ear
	     * flightplan-frontend-VERSION.jar
	  
	(6) flightplan-ear-VERSION.ear im Wildfly deployen
		dafür: 
		 * Datei ablegen in %WILDFLY-Installationsverzeichnis%/standalone/deployments
		 * Wildfly führt automatisch das deployment durch
		 
	(7) flightplan-frontend-VERSION.jar starten (executable jar)
	    => Die Benutzeroberfläche startet	