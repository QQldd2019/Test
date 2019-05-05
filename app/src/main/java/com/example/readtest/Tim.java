package com.example.readtest;

import java.util.Timer;

public class Tim {
    private static Timer timer =null;
    public static Timer getTimer() {
        if(timer==null){
            timer = new Timer();
        }
        return timer;
    }
}
