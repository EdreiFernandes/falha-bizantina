package config;

import java.util.ResourceBundle;

public class configReader {
    private static final ResourceBundle userConfig = ResourceBundle.getBundle("config.app");

    public static String getParam(String _key) {
        if (userConfig.containsKey(_key)) {
            return userConfig.getString(_key);
        }
        return null;
    }
}
