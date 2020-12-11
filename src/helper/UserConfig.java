package helper;

import config.configReader;

public class UserConfig {
    private String username;
    private int address;
    private Status status;
    private Boolean ImIntheWC;

    private static UserConfig instance;

    private UserConfig() {
        ImIntheWC = false;
        address = Integer.valueOf(configReader.getParam("address"));
    }

    public static synchronized UserConfig getInstance() {
        if (instance == null) {
            instance = new UserConfig();
        }
        return instance;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setImIntheWC(Boolean imIntheWC) {
        ImIntheWC = imIntheWC;
    }

    public Boolean getImIntheWC() {
        return ImIntheWC;
    }
}
