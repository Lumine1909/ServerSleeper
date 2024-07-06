package io.github.lumine1909;

import org.spigotmc.WatchdogThread;

import java.lang.reflect.Field;

public class WatchdogHandler {
    private final Field WatchdogThread_timeoutTimeF;
    private final long wdTimeout;
    private final WatchdogThread wdInstance;
    public WatchdogHandler() {
        try {
            WatchdogThread_timeoutTimeF = WatchdogThread.class.getDeclaredField("timeoutTime");
            WatchdogThread_timeoutTimeF.setAccessible(true);
            Field watchdogThread_instanceF = WatchdogThread.class.getDeclaredField("instance");
            watchdogThread_instanceF.setAccessible(true);
            wdInstance = (WatchdogThread) watchdogThread_instanceF.get(null);
            wdTimeout = WatchdogThread_timeoutTimeF.getLong(watchdogThread_instanceF.get(wdInstance));
        } catch (Exception e) {
            ServerSleeper.instance.getLogger().warning("Failed to handle watchdog, this feature disabled!");
            throw new RuntimeException(e);
        }
    }
    public void enable() {
        try {
            WatchdogThread_timeoutTimeF.setLong(wdInstance, wdTimeout);
        } catch (Exception e) {
            ServerSleeper.instance.getLogger().warning("Failed to handle watchdog!");
            throw new RuntimeException(e);
        }
    }
    public void disable() {
        try {
            WatchdogThread_timeoutTimeF.setLong(wdInstance, -1L);
        } catch (Exception e) {
            ServerSleeper.instance.getLogger().warning("Failed to handle watchdog!");
            throw new RuntimeException(e);
        }
    }
}
