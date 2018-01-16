package com.studnnet.notification_system.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("email")
@Getter
@Setter
public class EmaiProperties {
    private final ConnectGmail gmail = new ConnectGmail();
    private final ConnectAmazon amazon = new ConnectAmazon();
    private Boolean debug;



    @Getter
    @Setter
    public static class ConnectGmail {
        private String host;
        private Integer port;
        private String username;
        private String password;
        private GmailProperty properties = new GmailProperty();
    }

    @Getter
    @Setter
    public static class GmailProperty {
        private String smtpAuth;
        private Boolean tlsEnable;
    }


    @Getter
    @Setter
    public static class ConnectAmazon {
        private String host;
        private Integer port;
        private String username;
        private String password;
        private AmazonProperty properties = new AmazonProperty();
    }

    @Getter
    @Setter
    public static class AmazonProperty {
        private String smtpPort;
        private String smtpAuth;
        private Boolean tlsEnable;
        private Boolean tlsRequired;
        private String transportProtocol;
    }
}
