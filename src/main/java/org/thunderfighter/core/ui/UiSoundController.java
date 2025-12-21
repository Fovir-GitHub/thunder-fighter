package org.thunderfighter.core.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/*
 The sound controller: used to manage global sound settings.
*/
public final class UiSoundController {
  /*
   The listener interface for sound settings changes.
   */
  public interface Listener {

    // to judge whether the sound is on / off
    default void onSoundSwitch(boolean on) {}

    // to get the current volume 
    default void onVolumeChanged(double volume) {}
  }

  // The sound switch
  private boolean soundOn = true;

  //The raw volume value
  private double volume = 1.0;

  // The listener list
  private final List<Listener> listeners = new ArrayList<>();

  // Get the final available volume
  public double getVolume() {
    return soundOn ? volume : 0.0;
  }

  // Get the raw volume
  public double getRawVolume() {
    return volume;
  }

  // judge whether the sound is on
  public boolean isSoundOn() {
    return soundOn;
  }

  // The sound switch setter
  public void setSoundOn(boolean on) {
    this.soundOn = on;
    for (Listener l : listeners) l.onSoundSwitch(on);
  }

  // The volume setter
  public void setVolume(double v) {
    this.volume = limitation(v);
    for (Listener l : listeners) l.onVolumeChanged(this.volume);
  }

  // Add a listener
  public void addListener(Listener listener) {
    if (listener == null) return;
    listeners.add(listener);
  }

  // Remove a listener
  public void removeListener(Listener listener) {
    listeners.remove(listener);
  }

  // Get the listeners list (read-only)
  public List<Listener> getListenersReadOnly() {
    return Collections.unmodifiableList(listeners);
  }

  // Limit the value to 0~1
  private double limitation(double v) {
    if (v < 0.0) return 0.0;
    if (v > 1.0) return 1.0;
    return v;
  }
}
