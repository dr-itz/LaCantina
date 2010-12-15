Build, Short Version
====================

To build the LaCantina.war webapp
  ant

To generate PDFs from .odt:
  ant pdf
For this to work, ant 1.7 or higher and OpenOffice.org 3.1 or higher or
LibreOffice must be installed.

To import/use Eclipse (when ant build not run otherwise):
1. ant webapp-version
2. Then import the project


Installing the application:
===========================

Prerequisites:
* MySQL 5.x with InnoDB
* Tomcat6

1. Create the database
   Create an empty database in MySQL, give privileges to a user of choice.
   Remember the user/pass/database name for later.
2. Import the schema
   Import the file db/schema.sql into the newly created database
3. Install the JDBC driver
   copy lib/tomcat/mysql-connector-java-5.1.13-bin.jar to your $TOMCAT/lib
   directory.
4. define the database connection in tomcat
   open the file $TOMCAT/conf/context.xml, add a section like this:
	<Resource name="jdbc/LaCantinaDB" auth="Container" type="javax.sql.DataSource"
		maxActive="100" maxIdle="30" maxWait="10000" validationQuery="SELECT 1;"
		username="USER" password="PASS" driverClassName="com.mysql.jdbc.Driver"
		url="jdbc:mysql://HOST:3306/DBNAME"
		removeAbandoned="true" removeAbandonedTimeout="60" logAbandoned="true"
	/>
   using the appropriate values in place of USER, PASS, HOST, DBNAME.
5. Start tomcat
6. Deploy the webapp
   Copy dist/LaCantina.war to $TOMCAT/webapps. In it's default configuration,
   tomcat will unpack the application.
7. Log in using your favorite browser (let's hope it's not IE):
  http://localhost:8080/LaCantina/
  user: admin
  pass: admin


ANT build targets
=================

all:		build the whole application
compile:	only compile sources, don't assemble .war
webapp:		compile and assemble webapp
clean:		cleans everything generated
compile-test:	compiles the unit tests
test:		compiles and runs the unit tests
webapp-version:	generates the Version.java file

pdf:		generate PDFs from .odt
cleanpdf:	cleans the generated PDF files
javadoc:	generates JavaDoc
coverage:	genrate HTML coverage report with cobertura
