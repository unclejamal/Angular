package com.pduda.angular;

public class Main {

    public static void main(String[] args) {
        new Main().startTimeExpert();
    }

    private void startTimeExpert() {
        final TimeExpertServer timeExpert = new TimeExpertServer();
        try {
            timeExpert.start();
            timeExpert.join();
        } finally {
            timeExpert.stop();
        }
    }
}
