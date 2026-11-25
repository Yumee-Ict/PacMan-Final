package p;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class AudioSettings {

    private static double volume = 1.0;
    private static boolean muted = false;
    private static boolean musicEnabled = true;
    private static boolean failedSoundEnabled = true;
    private static boolean congratulationsSoundEnabled = true;
    private static final List<Runnable> listeners = new CopyOnWriteArrayList<>();

    private AudioSettings() {
    }

    public static double getVolume() {
        return volume;
    }

    public static void setVolume(double newVolume) {
        volume = Math.max(0.0, Math.min(1.0, newVolume));
        fireChanged();
    }

    public static boolean isMuted() {
        return muted;
    }

    public static void setMuted(boolean newMuted) {
        muted = newMuted;
        fireChanged();
    }

    public static boolean isMusicEnabled() {
        return musicEnabled;
    }

    public static void setMusicEnabled(boolean newMusicEnabled) {
        musicEnabled = newMusicEnabled;
        fireChanged();
    }

    public static boolean isFailedSoundEnabled() {
        return failedSoundEnabled;
    }

    public static void setFailedSoundEnabled(boolean enabled) {
        failedSoundEnabled = enabled;
        fireChanged();
    }

    public static boolean isCongratulationsSoundEnabled() {
        return congratulationsSoundEnabled;
    }

    public static void setCongratulationsSoundEnabled(boolean enabled) {
        congratulationsSoundEnabled = enabled;
        fireChanged();
    }

    public static double getEffectiveVolume() {
        return muted ? 0.0 : volume;
    }

    public static void addChangeListener(Runnable listener) {
        listeners.add(listener);
    }

    private static void fireChanged() {
        for (Runnable listener : listeners) {
            listener.run();
        }
    }
}
