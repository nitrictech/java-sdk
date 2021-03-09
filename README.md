# Nitric Java SDK

The Java  SDK supports the use of the cloud-portable [Nitric](http://nitric.io) framework with Java 11+.

> The Nitric Java SDK is in early stage development and is currently only available on XXX.

## Usage

### Nitric Functions (FaaS):

- Install the [Nitric CLI](#)
- Create / Open a Nitric Project
- Make a Java function

 ```bash
# Create a new project
nitric make:project example-java
cd example-java

# Create a java11 Nitric Function
nitric make:function java11 example-function
```

### Standard Java Project

- Install OpenJDK 11+
- Install Maven

```bash
# Build Nitric function Faas
mvn package
```

```bash
# Run Nitric function Faas
java -jar target/example-function-1.0-SNAPSHOT.jar
```
