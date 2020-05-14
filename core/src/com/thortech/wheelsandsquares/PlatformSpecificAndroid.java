package com.thortech.wheelsandsquares;

import com.thortech.wheelsandsquares.PlatformSpecific;

/**
 * Created by ToHj on 16-02-2016.
 */
public class PlatformSpecificAndroid implements PlatformSpecific {

    public static boolean isDebug;
    public PlatformSpecificAndroid()
    {
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
    public boolean isDebug(){
        return isDebug;
    }
}
