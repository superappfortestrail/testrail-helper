package ru.qiwi.automation.testrail.api.v2;

import com.rmn.testrail.entity.TestResult;
import com.rmn.testrail.util.JSONUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;

/**
 */
public class AddResultTests extends BaseApiTest {

    private static Logger LOG = Logger.getLogger(AddResultTests.class);

    /*
        Предполагаем, что у нас имеется testRun для незакрытого проекта и в нём имеется test
        Также имеются пользователи, имеющие доступ к проекту и имеющие право добавлять результаты тестов
        Так же предполагаем, что различные custom поля не настроены (тесты для них можно добавить, расширив объектную модель библиотеки).
     */

    @DataProvider( name = "addResultDataProvider"   )
    public Object[][] addResultDataProvider() throws Exception {
        //как вариант добавления данных можно использовать разлинчые параметры для
        LOG.debug("Загружаем данные для testAddResult");
        URI uri = new URI(this.getClass().getResource("/addTestResult.testData").toString());
        FileInputStream fis = new FileInputStream(uri.getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
        String line;
        List<AddResultTestData> testDataList = new ArrayList<>();
        while ((line = reader.readLine()) != null){
            if (!line.startsWith("#")){
               String[] splitLine =line.split("\\s\\|\\s");
               AddResultTestData current = new AddResultTestData();
               current.setUserName(splitLine[0]);
               current.setPassword(splitLine[1]);
               current.setTestResult(splitLine[2]);
               current.setResponseCode(Integer.parseInt(splitLine[3]));
               current.setExpectedResponse(splitLine[4]);
               testDataList.add(current);
            }
        }

        Object[][] testData;
        testData = new Object[testDataList.size()][];
        for (int i = 0; i < testDataList.size(); i++){
            testData[i] = new AddResultTestData[1];
            testData[i][0] = testDataList.get(i);
        }

        return testData;
    }

    @Test ( description = "Проверка метода add_result",
            dataProvider = "addResultDataProvider",
            groups = {"integration"}
    )
    public void testAddResult_ID(AddResultTestData addResultTestData) throws IOException {
        assertThat("buildVersion", buildVersion, notNullValue());
        assertThat("getService()", getService(), notNullValue());
        getService().setUsername(addResultTestData.getUserName());
        getService().setPassword(addResultTestData.getPassword());
        TestResult result = JSONUtils.getMappedJsonObject(TestResult.class, addResultTestData.getTestResult(), true);
        result.setVersion(buildVersion);
        TestResult expectedResponseResult = JSONUtils.getMappedJsonObject(TestResult.class, addResultTestData.getExpectedResponse(), true);
        HttpResponse httpResponse = getService().addTestResult(result.getTestId(), result);
        assertThat("Response not null", httpResponse, notNullValue());
        int responseCode = addResultTestData.getResponseCode();
        assertThat("Response code", httpResponse.getStatusLine().getStatusCode(), equalTo(responseCode));
        String content = EntityUtils.toString(httpResponse.getEntity());
        LOG.info("Response:" + content);
        if (responseCode == 200) {
            assertThat("Response is not empty", content, not(isEmptyOrNullString()));
            TestResult response = JSONUtils.getMappedJsonObject(TestResult.class, content, true);
            assertThat("id", response.getId(), notNullValue());
            assertThat("test_id", response.getTestId(), equalTo(result.getTestId()));
            assertThat("status_id", response.getStatusId(), equalTo(expectedResponseResult.getStatusId()));
            assertThat("comment", response.getComment(), equalTo(expectedResponseResult.getComment()));
            assertThat("version", response.getVersion(), equalTo(buildVersion));
            assertThat("elapsed", response.getElapsed(), equalTo(expectedResponseResult.getElapsed()));
            assertThat("defects", response.getDefects(), equalTo(expectedResponseResult.getDefects()));
            assertThat("assignedto_id", response.getAssignedtoId(), equalTo(expectedResponseResult.getAssignedtoId()));
            assertThat("created_by", response.getCreatedBy(), notNullValue());
            assertThat("created_on", response.getCreatedBy(), notNullValue());
        }
        else {
            assertThat("Response is not empty", content, isEmptyOrNullString());
        }
    }

}
