# Combine Datasets Application
## Overview
The Combine Datasets Application is designed to merge public information about ATMs with weather data based on their coordinates.
The application retrieves ATM data via an API, stores this information in a POJO (Plain Old Java Object), and then calls an external weather
API to fetch humidity and temperature data. This data is then combined into a CombinedDataPojo using the Builder pattern.

## Configuration

Define your API keys in the `application.properties` file:

```
spring.application.name=CombineDatasets
api.key=YOUR_API_KEY_HERE       
api.keyCS=YOUR_API_KEY_CS_HERE  
apiUrlCS=YOUR_URL
apiUrlWeather=https://api.openweathermap.org/data/2.5/weather
```

## API Usage
Access the combined data at:

```
GET http://localhost:8080/getCombinedData
```

