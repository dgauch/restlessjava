package dev.gauch.restlessjava.control;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CpuBurner {

    private static final Logger LOG = Logger.getLogger(CpuBurner.class.getName());

    private CpuBurner() {
    }

    public static int burnCold(int timeInMs) {
        if (timeInMs == 0)
            return 0;
        try {
            Thread.sleep(timeInMs);
        } catch (InterruptedException e) {
            LOG.log(Level.SEVERE, "Sleeping was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return timeInMs;
    }

    public static int burnCold(int minTimeInMs, int maxTimeInMs) {
        int timeInMs = (minTimeInMs == maxTimeInMs) ? minTimeInMs
                : minTimeInMs + new Random().nextInt(maxTimeInMs - minTimeInMs);
        return burnCold(timeInMs);
    }
}
