package com.i2i.intern.pixcell.TGF.command;

import com.i2i.intern.pixcell.TGF.constants.TransType;
import com.i2i.intern.pixcell.TGF.management.DelayManager;
import com.i2i.intern.pixcell.TGF.management.StatsManager;
import com.i2i.intern.pixcell.TGF.management.ThreadsManager;
import com.i2i.intern.pixcell.TGF.util.RandomGenerator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CommandHandler {

    private static final Scanner sc = new Scanner(System.in);

    private final ThreadsManager threadManager;
    private final StatsManager statsManager;
    private final DelayManager delayManager;

    public CommandHandler(ThreadsManager threadManager, StatsManager statsManager, DelayManager delayManager) {
        this.threadManager = threadManager;
        this.statsManager = statsManager;
        this.delayManager = delayManager;
    }

    public void startCommander() {

        int stopCounter =  0;

        outer:
        while(true) {

            System.out.println("enter command: ");
            String input = sc.nextLine();

            switch (input) {
                case "start" -> threadManager.startThreads();
                case "stop"          -> threadManager.stopThreads();
                case "setDelay"      -> updateDelayAll();
                case "setDelayVoice" -> updateDelay(TransType.VOICE);
                case "setDelayData"  -> updateDelay(TransType.DATA);
                case "setDelaySms"   -> updateDelay(TransType.SMS);
                case "printDelay"    -> delayManager.printDelay();
                case "printStats"    -> statsManager.printStats();
                case "resetStats"    -> statsManager.resetStats();
                case "updateMsisdn"  -> RandomGenerator.fetchMsisdn();  //update the set of msisdn from Hazelcast
                case "terminate"     -> {
                    try (sc) {
                        threadManager.stopThreads();
                    }
                    break outer;
                }

                default -> {

                    stopCounter++;
                    if(stopCounter > 3)
                        threadManager.stopThreads();
                    else
                        System.out.println("unrecognized command...");
                }
            }
        }

    }

    private void updateDelay(TransType type) {

        try {
            System.out.println("enter delay value:");

            switch (type) {
                case DATA -> delayManager.setDataDelay(sc.nextLong());

                case VOICE -> delayManager.setVoiceDelay(sc.nextLong());

                case SMS -> delayManager.setSmsDelay(sc.nextLong());
            }

        } catch (InputMismatchException e) {
            System.out.println("unsupported input format...");
        }
    }

    private void updateDelayAll() {

        try {
            System.out.println("enter delay value:");
            long delay = sc.nextLong();
            delayManager.setDelayAll(delay);

        } catch (InputMismatchException e) {
            System.out.println("unsupported input format...");
        }
    }

}