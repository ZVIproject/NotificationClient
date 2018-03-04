package com.notification.client.configs;

public class Urls {
    private static final String HOST = "";
    private static final String PORT = "";

    public static final String MESSAGE_CONTROLLER = HOST + ":" + PORT + "/rest/v1/ns/message/";
    public static final String SEND_CONTROLLER = HOST + ":" + PORT + "/rest/v1/mail/";  // /{sending_server}
    public static final String STATISTICS_CONTROLLER = HOST + ":" + PORT + "rest/v1/ns/statistic/";
    public static final String USER_CONTROLLER = HOST + ":" + PORT + "/rest/v1/ns/user/";
}
