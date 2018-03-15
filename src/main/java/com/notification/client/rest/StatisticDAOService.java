package com.notification.client.rest;

import com.notification.client.components.entities.Statistic;
import com.notification.client.configs.Urls;
import com.notification.client.http.GetRequestSender;

import java.util.List;

public class StatisticDAOService {

    private GetRequestSender getRequestSender;

    public StatisticDAOService() {
        getRequestSender = new GetRequestSender();
    }

    public List<Statistic> getStatistics() {
        List<Statistic> statistics = getRequestSender.getStatistics(Urls.STATISTICS_CONTROLLER);
        return statistics;
    }
}
