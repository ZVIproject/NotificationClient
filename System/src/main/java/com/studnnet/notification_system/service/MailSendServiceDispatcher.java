package com.studnnet.notification_system.service;

import com.studnnet.notification_system.interfacee.MailSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class MailSendServiceDispatcher {

    @Autowired(required = false)
    protected MailSendService[] mailSendServices;

    protected Map<String, MailSendService> serviceMap;

    @PostConstruct
    public void postConstruct() {
        serviceMap = new HashMap<>(mailSendServices.length);
        for (MailSendService svc : mailSendServices) {
            serviceMap.put(svc.name(), svc);
        }
    }

    public MailSendService get(String name) {
        MailSendService svc = serviceMap.get(name);
        if (null == svc) {
            throw new RuntimeException("mail service not found");
        }
        return svc;
    }
}
