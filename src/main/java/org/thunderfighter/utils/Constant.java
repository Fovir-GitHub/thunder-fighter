package org.thunderfighter.utils;

/** Constant */
public class Constant {

  public static final int ENEMY_NUMBER_LIMIT = 16;

  public static final int SCORE_PER_ROUND = 1000;
  public static final int GENERATE_ELITE_SCORE = 500;
  public static final int GENERATE_BOSS_SCORE = 800;

  public static final int TPS = 60;

  public static enum Phase {
    NORMAL,
    ELITE,
    BOSS
  }

  public static enum GAME_STATE {
    MENU,
    RUNNING,
    PAUSE,
    OVER,
  }
}
