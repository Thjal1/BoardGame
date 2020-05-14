package com.thortech.wheelsandsquares;

import com.thortech.wheelsandsquares.PlatformSpecific;

/**
 * Created by ToHj on 28-02-2016.
 */
public class PlatformSpecificHtml implements PlatformSpecific {

    public static boolean isDebug;

    public void PlatformSpecificHtml() {
        isDebug = false;
    }

    @Override
    public void login() {

    }

    @Override
    public void showBannerAd() {

    }

    @Override
    public void hideBannerAd() {

    }

    @Override
    public void unlockAchievement(int higsocreId) {

    }

    @Override
    public void postHighscore(int score) {

    }

    @Override
    public boolean isDebug() {
        return isDebug;
    }
}
