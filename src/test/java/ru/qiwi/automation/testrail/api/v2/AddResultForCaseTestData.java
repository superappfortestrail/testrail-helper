package ru.qiwi.automation.testrail.api.v2;

/**
 * POJO класс для более удобного отображения входных праметров тестов для AddResultForCaseTests
 */

public class AddResultForCaseTestData {

    private String userName;
    private String password;
    private String testCaseResult;
    private int responseCode;
    private int testRunId;
    private String expectedResponse;

    @Override
    public String toString(){
        return String.format("userName: %s, testRunId: %s, testCaseResult: %s, responseCode: %s", userName, testRunId, testCaseResult, responseCode);
    }

    public int getTestRunId() {
        return testRunId;
    }

    public void setTestRunId(int testRunId) {
        this.testRunId = testRunId;
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

    public String getTestCaseResult() {
        return testCaseResult;
    }

    public void setTestCaseResult(String testCaseResult) {
        this.testCaseResult = testCaseResult;
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
