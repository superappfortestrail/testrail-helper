package ru.qiwi.automation.testrail.api.v2;

/**
 * POJO класс для более удобного отображения входных праметров тестов для AddResultTests
 */
public class AddResultTestData {

    private String userName;
    private String password;
    private String testResult;
    private int responseCode;
    private String expectedResponse;

    @Override
    public String toString(){
        return String.format("userName: %s, testResult: %s, responseCode: %s", userName, testResult, responseCode);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getExpectedResponse() {
        return expectedResponse;
    }

    public void setExpectedResponse(String expectedResponse) {
        this.expectedResponse = expectedResponse;
    }
}
