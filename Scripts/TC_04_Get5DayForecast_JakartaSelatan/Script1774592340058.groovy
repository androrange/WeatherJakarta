import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.testobject.RestRequestObjectBuilder
import groovy.json.JsonSlurper
import com.kms.katalon.core.util.KeywordUtil
import internal.GlobalVariable

String base   = GlobalVariable.BASE_URL
String apiKey = GlobalVariable.API_KEY
def slurper   = new JsonSlurper()

def resF = WS.sendRequest(new RestRequestObjectBuilder()
    .withRestUrl("${base}/data/2.5/forecast?appid=${apiKey}")
    .withRestRequestMethod("GET")
    .build())

int statusF = resF.getStatusCode()
assert statusF == 400 : "Expected 400, got ${statusF}"
def fBody = slurper.parseText(resF.getResponseText())
assert fBody.containsKey("message")
KeywordUtil.logInfo("[PASS] Forecast: 400 for missing lat/lon | msg: ${fBody.message}")

def resA = WS.sendRequest(new RestRequestObjectBuilder()
    .withRestUrl("${base}/data/2.5/air_pollution?appid=${apiKey}")
    .withRestRequestMethod("GET")
    .build())

int statusA = resA.getStatusCode()
assert statusA == 400 : "Expected 400, got ${statusA}"
KeywordUtil.logInfo("[PASS] Air pollution: 400 for missing lat/lon")

KeywordUtil.logInfo("=== TC_04 ALL PASSED ===")