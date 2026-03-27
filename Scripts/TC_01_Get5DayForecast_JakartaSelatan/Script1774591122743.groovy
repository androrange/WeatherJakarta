import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.ConditionType
import groovy.json.JsonSlurper
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable

String endpoint = "${GlobalVariable.BASE_URL}/data/2.5/forecast?lat=${GlobalVariable.LAT}&lon=${GlobalVariable.LON}&appid=${GlobalVariable.API_KEY}&units=${GlobalVariable.UNITS}&cnt=40"

RequestObject req = new RequestObject()
req.setRestUrl(endpoint)
req.setRestRequestMethod('GET')
req.setHttpHeaderProperties([
    new TestObjectProperty('Accept', ConditionType.EQUALS, 'application/json')
])

def response = WS.sendRequest(req)
def body = new JsonSlurper().parseText(response.getResponseText())

WS.verifyResponseStatusCode(response, 200)
KeywordUtil.logInfo("[PASS] A1: HTTP 200")

assert response.getWaitingTime() < 5000
KeywordUtil.logInfo("[PASS] A2: Response time OK")

["cod","message","cnt","list","city"].each { assert body.containsKey(it) }
KeywordUtil.logInfo("[PASS] A3: Top-level schema OK")

assert body.cod.toString() == "200"
assert body.city.country == "ID"
assert Math.abs((body.city.coord.lat as double) - (-6.2615)) < 0.5
assert Math.abs((body.city.coord.lon as double) - 106.8106) < 0.5
KeywordUtil.logInfo("[PASS] A4: City = ${body.city.name}, country = ${body.city.country}")

List list = body.list
assert list?.size() > 0 && list.size() <= 40
KeywordUtil.logInfo("[PASS] A5: ${list.size()} forecast entries")

list.eachWithIndex { item, i ->
    double temp = item.main.temp as double
    assert temp >= 15 && temp <= 50 : "list[${i}] temp out of range: ${temp}"

    int humidity = item.main.humidity as int
    assert humidity >= 0 && humidity <= 100

    double windSpeed = item.wind.speed as double
    assert windSpeed >= 0

    int cloud = item.clouds.all as int
    assert cloud >= 0 && cloud <= 100

    int weatherId = item.weather[0].id as int
    assert weatherId >= 200 && weatherId <= 804

    double pop = item.pop as double
    assert pop >= 0 && pop <= 1.0

    assert item.dt_txt ==~ /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/
}
KeywordUtil.logInfo("[PASS] A6: All ${list.size()} items passed field assertions")

for (int i = 1; i < list.size(); i++) {
    long diff = ((list[i].dt as long) - (list[i-1].dt as long)) / 3600
    assert diff == 3 : "Interval [${i-1}]->[${i}] = ${diff}h, expected 3h"
}
KeywordUtil.logInfo("[PASS] A7: 3-hour intervals confirmed")

KeywordUtil.logInfo("=== TC_01 ALL PASSED | Temp: ${list[0].main.temp}C | ${list[0].weather[0].description} ===")