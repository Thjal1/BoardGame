package com.thortech.wheelsandsquares;

import com.thortech.wheelsandsquares.PlatformSpecific;

/**
 * Created by ToHj on 16-02-2016.
 */
public class PlatformSpecificIOS implements PlatformSpecific {

    public static boolean isDebug = false;
    public PlatformSpecificIOS()
    {
  //      isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
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
