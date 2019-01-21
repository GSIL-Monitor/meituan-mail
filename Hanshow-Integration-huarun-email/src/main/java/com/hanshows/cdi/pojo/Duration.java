package com.hanshows.cdi.pojo;

public class Duration {
    private long start;
    private long begin;
    private long end;

    public Duration() {
        start = begin = end = System.currentTimeMillis();
    }

    public long getDuration() {
        end = System.currentTimeMillis();
        long spent = end - begin;
        begin = end;
        return spent;
    }

    public long getTotal() {
        end = System.currentTimeMillis();
        return end - start;
    }
}
