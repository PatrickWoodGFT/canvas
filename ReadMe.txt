To build, run
mvn clean install
or
mvn -DskipTests=true clean install

Then 
cd target
java -jar canvas-1.0.jar

Alternatively, to build and run directly using Maven:
mvn compile exec:java

Java version >= 14 is required.