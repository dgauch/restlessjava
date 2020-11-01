package dev.gauch.restlessjava.control;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CpuBurner {

    private static final Logger LOG = Logger.getLogger(CpuBurner.class.getName());

    private CpuBurner() {
    }

    public static int burnCold(int timeInMs) {
        if (timeInMs <= 0)
            return 0;
        try {
            Thread.sleep(timeInMs);
        } catch (InterruptedException e) {
            LOG.log(Level.SEVERE, "Sleeping was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return timeInMs;
    }

    public static int burnHot(int timeInMs) {
        if (timeInMs <= 0)
            return 0;

        long started = System.currentTimeMillis();
        long endTime = started + timeInMs;
        while (System.currentTimeMillis() < endTime);
        return (int)(System.currentTimeMillis() - started);
    }

    public static int burnCold(int minTimeInMs, int maxTimeInMs) {
        return burnCold(randmonMinMax(minTimeInMs, maxTimeInMs));
    }

    public static int burnHot(int minTimeInMs, int maxTimeInMs) {
        return burnHot(randmonMinMax(minTimeInMs, maxTimeInMs));
    }

    private static int randmonMinMax(int minTimeInMs, int maxTimeInMs) {
        return (minTimeInMs == maxTimeInMs) ? minTimeInMs
                : minTimeInMs + new Random().nextInt(maxTimeInMs - minTimeInMs);
    }
}
