help:
	@echo ' run make install to setup Java environment'

# Install build environment
install:
	@echo installing java environment
	@sudo apt-get install openjdk-11-jdk
	@sudo apt-get install maven

# Build Java SDK library
build:
	@mvn clean install