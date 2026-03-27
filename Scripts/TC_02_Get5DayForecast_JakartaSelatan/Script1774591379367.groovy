import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.TestObjectProperty
import com.kms.katalon.core.testobject.ConditionType
import groovy.json.JsonSlurper
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable

String endpoint = "${GlobalVariable.BASE_URL}/data/2.5/air_pollution?lat=${GlobalVariable.LAT}&lon=${GlobalVariable.LON}&appid=${GlobalVariable.API_KEY}"

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

assert body.containsKey("coord") && body.containsKey("list")
KeywordUtil.logInfo("[PASS] A3: Top-level schema OK")

assert Math.abs((body.coord.lat as double) - (-6.2615)) < 0.1
assert Math.abs((body.coord.lon as double) - 106.8106) < 0.1
KeywordUtil.logInfo("[PASS] A4: Coord near Jakarta Selatan")

List list = body.list
assert list?.size() > 0
KeywordUtil.logInfo("[PASS] A5: list not empty")

List comps = ["co","no","no2","o3","so2","pm2_5","pm10","nh3"]
list.eachWithIndex { item, i ->
    int aqi = item.main.aqi as int
    assert aqi >= 1 && aqi <= 5 : "list[${i}].aqi=${aqi} out of [1,5]"
    comps.each { k -> assert item.components.containsKey(k) }
    comps.each { k -> assert (item.components[k] as double) >= 0 }
    assert (item.components.pm2_5 as double) < 1000
    assert (item.components.pm10  as double) < 1500
    assert (item.components.no2   as double) < 1000
    assert (item.components.co    as double) < 50000
}
KeywordUtil.logInfo("[PASS] A6: All component assertions passed")

def c = list[0].components
def aqiLabel = [1:"Good",2:"Fair",3:"Moderate",4:"Poor",5:"Very Poor"][list[0].main.aqi as int]
KeywordUtil.logInfo("=== TC_02 ALL PASSED | AQI: ${list[0].main.aqi} (${aqiLabel}) | PM2.5: ${c.pm2_5} | PM10: ${c.pm10} ===")