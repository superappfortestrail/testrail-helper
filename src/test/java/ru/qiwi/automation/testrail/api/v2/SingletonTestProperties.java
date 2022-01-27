package ru.qiwi.automation.testrail.api.v2;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.net.URI;
import java.util.Properties;

/**
 */
public class SingletonTestProperties {

    public static Logger LOG = Logger.getLogger(SingletonTestProperties.class);

    private static final Properties instance;

    static {
        try {
            LOG.debug("Загружаем 'test.run.properties'");
            instance = new Properties();
            URI uri = new URI(instance.getClass().getResource("/test.run.properties").toString());
            instance.load(new FileInputStream(uri.getPath()));
            LOG.debug("'test.run.properties' успешно загружены...");
        } catch (Exception e){
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Properties getInstance(){
        return instance;
    }
}
