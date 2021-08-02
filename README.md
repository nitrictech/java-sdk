<p align="center">
  <img src="./src/javadoc/dot-matrix-logo-java.png" alt="Nitric Logo"/>
</p>

![Tests](https://github.com/nitrictech/java-sdk/actions/workflows/test.yaml/badge.svg?branch=main)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=nitrictech_java-sdk&metric=coverage)](https://sonarcloud.io/dashboard?id=nitrictech_java-sdk)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=nitrictech_java-sdk&metric=alert_status)](https://sonarcloud.io/dashboard?id=nitrictech_java-sdk)
![Maven Central](https://img.shields.io/maven-central/v/io.nitric/java-sdk)
[![javadoc](https://javadoc.io/badge2/io.nitric/java-sdk/javadoc.svg)](https://javadoc.io/doc/io.nitric/java-sdk)


# Nitric Java SDK
The Java SDK supports the use of the cloud-portable [Nitric](https://nitric.io) framework with Java 11.

> The SDK is available as early access and interfaces may still subject to breaking changes.

## Prerequisites
- OpenJDK 11+

## Getting Started

### Using the [Nitric CLI](https://github.com/nitric-tech/cli)

```bash
nitric make:project example
 ? Include an example service? function/java11
 ? Name for the example service?
 ✔ Making Project getting-started
 ✔ Making Service example
```

### Adding to an existing project
**Maven**
```xml
<dependency>
    <groupId>io.nitric</groupId>
    <artifactId>java-sdk</artifactId>
    <version>0.8.0</version>
</dependency>
```

## Javadoc
[![javadoc](https://javadoc.io/badge2/io.nitric/java-sdk/javadoc.svg)](https://javadoc.io/doc/io.nitric/java-sdk)