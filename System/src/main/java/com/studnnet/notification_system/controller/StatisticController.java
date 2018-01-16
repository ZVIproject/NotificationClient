package com.studnnet.notification_system.controller;

import com.studnnet.notification_system.component.entity.StatisticEntity;
import com.studnnet.notification_system.component.repositories.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("rest/v1/ns/statistic")
public class StatisticController {

    @Autowired
    private StatisticRepository statisticRepository;

    @GetMapping("/")
    public List<StatisticEntity> getStatistic() {
        return statisticRepository.findAll();
    }
}
