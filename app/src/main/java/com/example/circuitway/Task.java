package com.example.circuitway;

import java.util.concurrent.Callable;

public class Task {

    TaskObjective objective;
    TaskCondition condition;
    float value;

    Detail detail;

    Callable<Boolean> checkFunc;

    CircuitActivity circuitActivity;

    public Task(Detail d, TaskObjective o, TaskCondition c, float v,
                CircuitActivity c2){
        circuitActivity = c2;
        value = v;
        autoConfigure(o, c);
    }

    private void autoConfigure(TaskObjective o, TaskCondition c) {
        byte t = 0;

        switch (o){
            case CURRENT: t += 1;
            case VOLTAGE: t += 2;
            case RESISTANCE: t += 3;
        }

        switch (c){
            case MINIMUM: t += 10;
            case MAXIMUM: t += 20;
        }

        switch (t){
            case 11:
                checkFunc = () -> {
                    if (detail.getCurrent() > value) return true;
                    return false;
                };
            case 12:
                checkFunc = () -> {
                    if (detail.getCurrent() <= value) return true;
                    return false;
                };
            case 21:
                checkFunc = () -> {
                    if (detail.getVoltage() > value) return true;
                    return false;
                };
            case 22:
                checkFunc = () -> {
                    if (detail.getVoltage() <= value) return true;
                    return false;
                };
            case 31:
                checkFunc = () -> {
                    if (detail.getResistance() > value) return true;
                    return false;
                };
            case 32:
                checkFunc = () -> {
                    if (detail.getResistance() <= value) return true;
                    return false;
                };
        }
    }

    public boolean checkCompleteness() {
        try{
            return checkFunc.call();
        }
        catch (Exception e) { return false; }
    }


    public enum TaskObjective {
        CURRENT,
        VOLTAGE,
        RESISTANCE,
    }

    public enum TaskCondition {
        MINIMUM,
        MAXIMUM,
    }


}
