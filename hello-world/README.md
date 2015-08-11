# Hello World example with Apache Commons SCXML

## How to run with Maven

- Move to hello-world folder
- Run the following:

    $ mvn clean package
    $ mvn exec:java

- You will see a message like the following:

    [INFO] --- exec-maven-plugin:1.4.0:java (default-cli) @ commons-scxml-examples-hello-world ---
    Aug 03, 2015 8:12:59 AM org.apache.commons.scxml2.SCXMLExecutionContext execute
    INFO: null: Hello, World!
