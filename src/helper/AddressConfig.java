package helper;

import config.configReader;

public class AddressConfig {
    private int firstAddress;
    private int lastAddress;

    private static AddressConfig instance;

    private AddressConfig() {
        firstAddress = Integer.valueOf(configReader.getParam("firstAddress"));
        lastAddress = Integer.valueOf(configReader.getParam("lastAddress"));
    }

    public static synchronized AddressConfig getInstance() {
        if (instance == null) {
            instance = new AddressConfig();
        }
        return instance;
    }

    public static boolean isInDomainRange(int _address) {
        return _address >= getInstance().firstAddress && _address <= getInstance().lastAddress;
    }

    public int getFirstAddress() {
        return firstAddress;
    }
}
