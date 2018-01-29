package com.studnnet.notification_system.service;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Test {

    @org.junit.Test
    public void name() throws Exception {
        int[] arr = {1,2,3,4,5};

        Arrays.stream(Arrays.copyOfRange(arr,
            3, 8)).forEach(System.out::println);

    }
}
