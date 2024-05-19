# Combine Datasets Application
## Overview
The Combine Datasets Application is designed to merge public information about ATMs with weather data based on their coordinates.
The application retrieves ATM data via an API, stores this information in a POJO (Plain Old Java Object), and then calls an external weather
API to fetch humidity and temperature data. This data is then combined into a CombinedDataPojo using the Builder pattern.

## GETTING STARTED

### PREREQUISITES
- **Java 17 or later**: Make sure Java is installed on your system.
- **Maven**: This project uses Maven for managing dependencies and building the project.


### INSTALLATION
1. **Clone the repository**:
	```
	git clone https://github.com/HMika/CombineDatasets.git
	cd CombineDatasets
	```

2. **Configure API Keys**: 

	Edit the 'application.properties' file to include your API keys.

	This file is located in the **'src/main/resources'** directory. You will need to replace **'INSERT_VALUE'** with the keys provided by the 		data services you are using.

	```
	spring.application.name=CombineDatasets
	api.key=INSERT_VALUE
	api.keyCS=INSERT_VALUE
	apiUrlCS=INSERT_VALUE
	apiUrlWeather=INSERT_VALUE
	```

3. **Build the application**:

   Navigate to the root directory of the project and run the following command to build the application using Maven:

	```
	mvn clean install
	```

---

### RUNNING THE APPLICATION
After building the project, you can run it locally using the following Maven command:
```
mvn spring-boot:run
```

### ACCESSING THE API
Once the application is running, you can access the Combine Datasets API at:
```
GET http://localhost:8080/getCombinedData
```

This endpoint serves the combined data of ATM locations and corresponding weather conditions.

---

### TESTING
To ensure everything is set up correctly, you can run the automated tests with Maven by executing:
```
mvn test
```
This will run all the unit tests in the project, verifying that the initial setup is correct.


