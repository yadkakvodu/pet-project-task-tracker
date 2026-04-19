package service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {

    public static final Properties prop = new Properties();

    public PropertiesUtil() {}

    static {
        loadProperties();
    }

    public static String get(String a) {
        return prop.getProperty(a);
    }

    public static void loadProperties() {
        try (InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream("resources.properties")) {
            prop.load(is);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
