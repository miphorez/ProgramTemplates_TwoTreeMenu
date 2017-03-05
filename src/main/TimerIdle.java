package main;

import main.gui.GUIProgram;

import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;

public class TimerIdle {
    private Logger logger;
    private LocalDateTime waitTerminate = LocalDateTime.now();
    private int secTerminate = 10;

    private static TimerIdle instance;

    public static TimerIdle getInstance() {
        if (instance == null) {
            instance = new TimerIdle();
        }
        return instance;
    }

    public void setInstanceParam(Logger logger) {
        this.logger = logger;
    }

    public void go(){
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (isTimeOver()) {
                    setNextTime();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);
        setNextTime();
    }

    private void setNextTime() {
        waitTerminate = LocalDateTime.now();
        waitTerminate.plusSeconds(secTerminate);
    }

    private boolean isTimeOver() {
        LocalDateTime localDateTime = LocalDateTime.now();
        if (localDateTime.isAfter(waitTerminate)) {
                return true;
        }
        return false;
    }

}
