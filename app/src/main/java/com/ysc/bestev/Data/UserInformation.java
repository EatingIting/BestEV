package com.ysc.bestev.Data;

public class UserInformation {
    private String userId;
    private String userName;


    public UserInformation(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getId() {
        return userId;
    }

    public void setId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }
}
