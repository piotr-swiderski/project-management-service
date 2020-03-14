package com.managementservice.projectmanagement.utils;

public enum AccountTypeEnum {
    FACEBOOK("Facebook"),
    Google("Google"),
    NONE("None");

    private String service;

    AccountTypeEnum(String service) {
        this.service = service;
    }

    public String getService() {
        return service;
    }

}
