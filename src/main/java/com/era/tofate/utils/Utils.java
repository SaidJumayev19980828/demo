package com.era.tofate.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Utils {
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();

        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}
