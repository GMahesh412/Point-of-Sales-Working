package com.pos.point_of_sales.controller.pos;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * ConfigReader - Utility class for reading configuration properties from a file.
 * @author Mahesh Yadav
 */
public class ConfigReader {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigReader.class);
    private static final String CONFIG_FILE = "src/main/resources/application.properties";

    /**
     * readConfig() -Reads configuration properties from the specified file.
     *
     * @return Properties object containing the configuration properties
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static Properties readConfig() throws IOException {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE))
        {
            properties.load(fis);
            LOG.info("Configuration properties loaded successfully from {}", CONFIG_FILE);
            LOG.debug("Configuration properties loaded successfully from {}", CONFIG_FILE + fis);
        } catch (IOException e)
        {
            LOG.error("Error reading configuration properties from {}", CONFIG_FILE, e);
            throw new RuntimeException("Error reading configuration properties", e);
        }
        return properties;
    }
}

      /*  InputStream inputStream = ConfigReader.class.getResourceAsStream(CONFIG_FILE);

        try {
            FileInputStream fis = new FileInputStream(String.valueOf(inputStream));

            properties.load(fis);
            String a = properties.getProperty("discountAmount");
        } catch (Exception e){
            e.printStackTrace();
        }
        return properties;
    }*/

