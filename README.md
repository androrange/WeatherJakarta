# WeatherJakarta — API Automation Testing

Automated API testing project for OpenWeatherMap using Katalon Studio.
Covers 5-Day Weather Forecast and Current Air Pollution data for Jakarta Selatan.

## 📌 Test Scenarios
- ✅ Get 5 Day / 3-Hour Weather Forecast — Jakarta Selatan
- ✅ Get Current Air Pollution — Jakarta Selatan
- ❌ Negative: Invalid API Key → 401 Unauthorized
- ❌ Negative: Missing Parameters → 400 Bad Request

## 🔍 Assertions per Test Case
- HTTP status code
- Response time < 5000ms
- JSON schema validation
- Field type & value range validation
- Business logic validation (AQI range, 3-hour intervals, coordinate accuracy)

## 🛠️ Tech Stack
- **Tool**: Katalon Studio 10.4.2
- **Language**: Groovy
- **API**: OpenWeatherMap (Free Tier)
- **Location**: Jakarta Selatan (lat: -6.2615, lon: 106.8106)

## 📂 Project Structure
```
WeatherJakarta/
├── Test Cases/
│   ├── WeatherAPI/
│   │   ├── TC_01_Get5DayForecast_JakartaSelatan
│   │   ├── TC_03_InvalidAPIKey_Returns401
│   │   └── TC_04_MissingParams_Returns400
│   └── AirPollutionAPI/
│       └── TC_02_GetCurrentAirPollution_JakartaSelatan
├── Test Suites/
│   └── TestSuites
└── Profiles/
    └── default.glbl
```

## ▶️ How to Run
1. Clone this repository
2. Open Katalon Studio → Open Project → select this folder
3. Set the API key in `Profiles/default` → `API_KEY` variable
4. Run `Test Suites/TestSuites`
5. View the report in the Log Viewer tab or the `Reports/` folder

## 🔑 Setup API Key
Sign up for free at [openweathermap.org](https://openweathermap.org) →
My API Keys → copy key → paste it into the `API_KEY` variable in Profiles.

> ⚠️ Do not commit your actual API key to a public repository.

## 📊 Test Result
| TC | Scenario | Status |
|---|---|---|
| TC_01 | 5-Day Forecast Jakarta Selatan | ✅ Pass |
| TC_02 | Current Air Pollution Jakarta Selatan | ✅ Pass |
| TC_03 | Invalid API Key → 401 | ✅ Pass |
| TC_04 | Missing Parameters → 400 | ✅ Pass |
