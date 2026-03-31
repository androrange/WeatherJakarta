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
1. Clone repo ini
2. Buka Katalon Studio → Open Project → pilih folder ini
3. Set API Key di `Profiles/default` → variabel `API_KEY`
4. Run `Test Suites/TestSuites`
5. Lihat report di tab Log Viewer atau folder `Reports/`

## 🔑 Setup API Key
Daftar gratis di [openweathermap.org](https://openweathermap.org) →
My API Keys → copy key → paste ke variabel `API_KEY` di Profiles.

> ⚠️ Jangan commit API key asli ke repository publik.

## 📊 Test Result
| TC | Scenario | Status |
|---|---|---|
| TC_01 | 5-Day Forecast Jakarta Selatan | ✅ Pass |
| TC_02 | Current Air Pollution Jakarta Selatan | ✅ Pass |
| TC_03 | Invalid API Key → 401 | ✅ Pass |
| TC_04 | Missing Parameters → 400 | ✅ Pass |
