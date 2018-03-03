package com.studnnet.notification_system.service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Test {

    @org.junit.Test
    public void name() throws Exception {
        int[] arr = {1,2,3,4,5};

        Arrays.stream(Arrays.copyOfRange(arr,
            3, 8)).forEach(System.out::println);

    }


    @org.junit.Test
    public void name1() throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<Integer> rez = executorService.submit(()->{
            return 123;
        });

        System.out.println(rez.get());
    }
}
