import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.RestRequestObjectBuilder
import groovy.json.JsonSlurper
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable

String base   = GlobalVariable.BASE_URL
String lat    = GlobalVariable.LAT
String lon    = GlobalVariable.LON
String badKey = "INVALID_KEY_00000000000000000000"
def slurper   = new JsonSlurper()

def resF = WS.sendRequest(new RestRequestObjectBuilder()
    .withRestUrl("${base}/data/2.5/forecast?lat=${lat}&lon=${lon}&appid=${badKey}")
    .withRestRequestMethod("GET")
    .build())

WS.verifyResponseStatusCode(resF, 401)
def fBody = slurper.parseText(resF.getResponseText())
assert fBody.cod?.toString() == "401"
assert fBody.containsKey("message")
KeywordUtil.logInfo("[PASS] Forecast 401 OK")

def resA = WS.sendRequest(new RestRequestObjectBuilder()
    .withRestUrl("${base}/data/2.5/air_pollution?lat=${lat}&lon=${lon}&appid=${badKey}")
    .withRestRequestMethod("GET")
    .build())

WS.verifyResponseStatusCode(resA, 401)
def aBody = slurper.parseText(resA.getResponseText())
assert aBody.cod?.toString() == "401"
KeywordUtil.logInfo("[PASS] Air pollution 401 OK")

KeywordUtil.logInfo("=== TC_03 ALL PASSED ===")