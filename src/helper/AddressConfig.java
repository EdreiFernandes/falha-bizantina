package helper;

public class AddressConfig {
    private int firstAddress = 4240;
    private int lastAddress = 4244;

    private static AddressConfig instance;

    private AddressConfig() {
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
