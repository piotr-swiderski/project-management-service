package com.managementservice.projectmanagement.utils;

public class ControllerUtil {

    //login error
    public static final String ERROR_LOGIN_BAD_CREDENSIALS = "error";
    public static final String ERROR_LOGIN_BAD_CREDENSIALS_MESSAGE = "wrong password or login";
    public static final String ERROR_LOGIN_OAUTH2_BAD_CREDENSIALS_MESSAGE = "Authorization error please try again later";

    // sprint error
    public static final String ERROR_ADDING_SPRINT_HANDLER = "error";
    public static final String ERROR_ADDING_SPRINT_MESSAGE = "Your Sprint date is not valid";

    //notification error
    public static final String ERROR_ADDING_NOTIFICATION = "error";
    public static final String ERROR_ADDING_NOTIFICATION_MESSAGE = "Failed to add user";
    public static final String ERROR_ADDING_NOTIFICATION_USERS = "error";
    public static final String ERROR_ADDING_NOTIFICATION_USERS_MESSAGE = "User not found";
    public static final String ERROR_DELETE_USERS_TO_PROJECT = "error";
    public static final String ERROR_DELETE_USERS_TO_PROJECT_MESSAGE = "Unable to delete project admin";



    public static final String PROJECT_HANDLER = "project";
    public static final String TASK_LIST = "tasksList";
    public static final String SPRINT_HANDLER = "sprint";


    // notification Succes
    public static final String SUCCSES_DELETE_USER = "succes";
    public static final String SUCCSES_DELETE_USER_MESSAGE = "User removed from project";
    public static final String SUCCSES_ADDING_NOTIFICATION = "succes";
    public static final String SUCCSES_ADDING_NOTIFICATION_MESSAGE = "Notification sent";


    public static final String ERROR_HANDLER = "ERROR_HANDLER";
    public static final String ERROR_MSG_ACCESS = "You don't have a access !!!";
    public static final String ERROR_HELP_HANDLER = "ERROR_HELP";
    public static final String ERROR_MSG_HELP_ACCESS = "You should contact with administrator !!!";


}
