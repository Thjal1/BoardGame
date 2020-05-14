package com.thortech.wheelsandsquares;

/**
 * Created by ToHj on 16-02-2016.
 */
public interface PlatformSpecific {
    void login();
    void showBannerAd();
    void hideBannerAd();
    void unlockAchievement(int higsocreId);
    void postHighscore(int score);
    boolean isDebug();
}
