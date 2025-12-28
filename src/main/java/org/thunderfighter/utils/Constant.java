package org.thunderfighter.utils;

/** Constant */
public class Constant {

  public static final int ENEMY_NUMBER_LIMIT = 16;

  public static final int GENERATE_ELITE_SCORE = 100;
  public static final int GENERATE_BOSS_SCORE = 500;

  public static final int TPS = 120;

  public static enum PHASE {
    NORMAL,
    ELITE,
    BOSS
  }

  public static enum GAME_STATE {
    MENU,
    RUNNING,
    PAUSE,
    SUCCESS,
    FAIL,
  }
}
