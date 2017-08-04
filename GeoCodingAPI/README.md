# GeoCodingAPI

This API exposes two endpoints - 1. To get the address information for a given set of co-ordinates 2. To retrieve the last 10 results from the local cache which is refreshed after every request. The service used to get the address information the Google Geo coding service which offers a free tier upto 1000 requests a day. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

1. Java Run time environment version 7 & above
2. Maven 

### Installing

1. Extract the zip file to a folder location
2. Open a command line tool
3. Navigate to folder and one level inside the application
4. Execute command "mvn clean install"
5. Navigate to target directory which is another level inside
3. Execute command - "java -jar GeoCodingAPI-0.0.1-SNAPSHOT.jar"

## Running the tests

1. All Junit tests are automatically run when you build using Maven - pom.xml 

## Authors

Mehul Dodia


