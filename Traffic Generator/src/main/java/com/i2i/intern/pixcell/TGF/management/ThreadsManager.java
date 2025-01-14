package com.i2i.intern.pixcell.TGF.management;

import com.i2i.intern.pixcell.TGF.trafficGenerators.TrafficGenerator;

public class ThreadsManager {

    private final TrafficGenerator voiceGenerator;
    private final TrafficGenerator dataGenerator;
    private final TrafficGenerator smsGenerator;

    private Thread voiceThread;
    private Thread dataThread;
    private Thread smsThread;

    public ThreadsManager(TrafficGenerator voiceGenerator, TrafficGenerator dataGenerator, TrafficGenerator smsGenerator) {
        this.voiceGenerator = voiceGenerator;
        this.dataGenerator = dataGenerator;
        this.smsGenerator = smsGenerator;
    }

    public void startThreads() {

        if(isThreadsNull())
            runThreads();
        else if(isThreadsAlive())
            System.out.println("Generator already running...");
        else
            runThreads();

    }

    private void runThreads() {

//        resetStats();

        voiceThread = new Thread(voiceGenerator);
        dataThread = new Thread(dataGenerator);
        smsThread = new Thread(smsGenerator);

        voiceThread.start();
        dataThread.start();
        smsThread.start();

        System.out.println("Transaction generator started...");

    }

    public void stopThreads() {

        if(isThreadsNull())
            System.out.println("No generators are running...");
        else {
            voiceGenerator.stop();
            dataGenerator.stop();
            smsGenerator.stop();
        }
    }

    private boolean isThreadsNull() {
        return (voiceThread == null || dataThread == null || smsThread == null);
    }

    private boolean isThreadsAlive() {
        return (voiceThread.isAlive() || dataThread.isAlive() || smsThread.isAlive());
    }


}
