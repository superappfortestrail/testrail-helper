package ru.qiwi.automation.testrail.api.v2;

import com.rmn.testrail.service.TestRailService;
import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Properties;

/**
 */
public class BaseApiTest {

    private static Logger LOG = Logger.getLogger(BaseApiTest.class);

    protected String buildVersion;
    protected TestRailService service;

    public TestRailService getService(){
        return service;
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() throws Exception{
        //Предполагается, что для тестирования у нас будет дамп БД тестрейла с заведеными
        //там соотвествющими проектами
        Properties properties = SingletonTestProperties.getInstance();
        String testRailUrl = properties.getProperty("test.testRail.url");
        String testRailUserName = properties.getProperty("test.testRail.userName");
        String testRailPassword = properties.getProperty("test.testRail.password");
        service = new TestRailService();
        service.setApiEndpoint(new URL(testRailUrl));
        service.setUsername(testRailUserName);
        service.setPassword(testRailPassword);
        buildVersion = properties.getProperty("test.buildVersion");
        LOG.info("Загружены свойства:");
        LOG.info("test.testRailUrl: " + testRailUrl);
        LOG.info("test.testRailUserName: " + testRailUserName);
        LOG.info("test.testRailPassword: " + testRailPassword);
        LOG.info("test.buildVersion: " + buildVersion);
        prepareTestData();
        LOG.info("Before suite completed");
    }

    private void prepareTestData(){
        /*
            Модель подготовки тестовых данных заключается в том,
            что мы храним дамп тестовой базы развернутого TestRail со всеми тестовыми данными.

            Как вариант - можем создавать необходимые данные модифицируя БД и передавать необходимые
            для тестов данные через параметризацию тестов
        */
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method){
        String description = "";
        Test testAnnotation = (Test) method.getAnnotation(Test.class);
        if (testAnnotation != null){
            description = testAnnotation.description();
        }
        //LOG.info("test.buildVersion: " + buildVersion);
        LOG.info("Выполняется тест: " + method.getName() + ". " + description);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result, Method method ){
        LOG.info(result);
    }


}
