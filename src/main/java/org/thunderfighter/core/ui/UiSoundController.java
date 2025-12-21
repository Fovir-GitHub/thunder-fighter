package org.thunderfighter.core.ui;

// Sound (volume + switch)
public abstract class UiSoundController {
  protected boolean soundOn = true;
  protected double volume = 1.0;

  public final double getVolume() {
    return soundOn ? volume : 0.0;
  }

  public final boolean isSoundOn() {
    return soundOn;
  }

  public final void setSoundOn(boolean on) {
    soundOn = on;
    onSoundSwitch(on);
  }

  public final void setVolume(double v) {
    volume = limitation(v);
    onVolumeChanged(volume);
  }//set volume

  protected double limitation(double v) {
    if (v < 0.0) return 0.0;
    if (v > 1.0) return 1.0;
    return v;
  }//limitation between 0.0 and 1.0

  protected void onSoundSwitch(boolean on){};
  protected void onVolumeChanged(double volume){};
}
