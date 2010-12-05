Short Version
=============

To build the LaCantina.war webapp
  ant

To generate PDFs from .odt:
  ant pdf
For this to work, ant 1.7 or higher and OpenOffice.org 3.1 or higher or
LibreOffice must be installed.

To view the helloWorld struts demo:
  http://localhost:8080/LaCantina/helloWorld.do

To view the struts-tiles example:
  http://localhost:8080/LaCantina/index.do

To import/use Eclipse:
  ant webapp-version
  Then import the project


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
