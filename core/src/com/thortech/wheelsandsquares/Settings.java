package com.thortech.wheelsandsquares;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by ToHj on 20-01-2016.
 */
public class Settings {
    public static final String TAG = com.thortech.wheelsandsquares.Settings.class.getName();
    public static Settings instance = new Settings();

    public static final Skin skin = new Skin(Gdx.files.internal("General/uiskin.json"));

    //Todo: Create constructor and load / save function and get/set  for variables + incorporate the structure in the game
    private static Music themeMusic;
    private static float themeMusicLevel;
    private static float soundLevel;

    private static boolean vibrate;

    private static boolean muteMusicOff;
    private static boolean muteSoundOff;
    private static int screenHeightPixels;  //Is set by the launcher class
    private static int screenWidthPixels;   //Is set by the launcher class
    private static int aspectRatio;
    private static Preferences preferences;
    private static final String preFile = "thortech_wheelsandsquares_settings.pre";
    
    private static int seedForRandom =     1543982;//1543981;
    private static int seedForRandomMax = 10000000;
    private static int levelReachedByPlayer = 1;

    public static final float PHYSICALWIDTH = 1.0f;    //Size of the physical world widthin meters, the stage of the game
    public static float PHYSICALHEIGHT = 1.0f;         //Size of the physical world height - calculated in the constructor
    public static float PHYSICALSCALE;                 //Scale ratio from screen size to physical world - calculated in the constructor

    //Make it a singleton
    private Settings(){
        PHYSICALSCALE = PHYSICALWIDTH / screenWidthPixels;
    }

    public static Settings getInstance()
    {
        if (instance == null)
            instance = new Settings();
        return instance;
    }

    public void init() {
        loadAll();
    }

    public static void loadAll() {
        try {
            preferences = Gdx.app.getPreferences(preFile);

            themeMusic = Gdx.audio.newMusic(Gdx.files.internal("General/thememusic.mp3"));
            screenWidthPixels = Gdx.graphics.getWidth();
            screenHeightPixels = Gdx.graphics.getHeight();

            themeMusicLevel = preferences.getFloat("music_level", 0.4f);
            soundLevel = preferences.getFloat("sound_level", 0.4f);
            vibrate = preferences.getBoolean("vibrate", true);
            muteMusicOff = preferences.getBoolean("mute_music", false);
            muteSoundOff = preferences.getBoolean("mute_sound", false);


            seedForRandom = (preferences.getInteger("seedForRandom") != 0) ? preferences.getInteger("seedForRandom") : seedForRandom;
            seedForRandomMax = (preferences.getInteger("seedForRandomMax") != 0) ? preferences.getInteger("seedForRandomMax") : seedForRandomMax;
            levelReachedByPlayer = (preferences.getInteger("levelReachedByPlayer") != 0) ? preferences.getInteger("levelReachedByPlayer") : levelReachedByPlayer;
        }
        catch (Exception ex) {
            //TODO: handle exception ex
        }
    }

    public static void saveAll(){
        try {
            //// TODO: make sure that the preferences are saved to preferences file
            preferences.putFloat("music_level", themeMusicLevel);
            preferences.putFloat("sound_level", soundLevel);
            preferences.putBoolean("vibrate", vibrate);
            preferences.putBoolean("mute_music", muteMusicOff);
            preferences.putBoolean("mute_sound", muteSoundOff);
            preferences.putInteger("seedForRandom", seedForRandom);
            preferences.putInteger("seedForRandomMax", seedForRandomMax);
            preferences.putInteger("levelReachedByPlayer", levelReachedByPlayer);

            preferences.flush();
        }
        catch (Exception ex) {
            //TODO: handle exception ex
        }
    }

    public static Music getThemeMusic() {
        return themeMusic;
    }
    public static void setThemeMusic(Music themeMusic) {
        Settings.themeMusic = themeMusic;
        //Update the new music
    }
    public static float getThemeMusicLevel() {
        return themeMusicLevel;
    }

    public static void setThemeMusicLevel(float themeMusicLevel) {
        Settings.themeMusicLevel = themeMusicLevel;
    }

    public static float getSoundLevel() {
        return soundLevel;
    }

    public static void setSoundLevel(float _soundLevel) {
        soundLevel = _soundLevel;
    }

    /***
     *
     * @param width
     * @param height
     * @return aspect ratio in values 1, 2 and 3
     */
    public static int resizeScreenPixels(int width, int height) {
        screenWidthPixels = width;
        screenHeightPixels = height;

        return getAspectRatio();
    }

    public static int getScreenHeightPixels() {
        return screenHeightPixels;
    }

    public static int getScreenWidthPixels() {
        return screenWidthPixels;
    }

    public static int getAspectRatio() {
        if (aspectRatio == 0)
            resizeScreenPixels(screenWidthPixels, screenHeightPixels);
        return aspectRatio;
    }


    public static boolean isVibrate() {
        return vibrate;
    }

    public static void setVibrate(boolean vibrate) {
        Settings.vibrate = vibrate;
    }

    public static boolean isMuteMusicOff() {
        return muteMusicOff;
    }

    public static void setMuteMusicOff(boolean muteMusicOff) {
        Settings.muteMusicOff = muteMusicOff;
    }

    public static boolean isMuteSoundOff() {
        return muteSoundOff;
    }

    public static void setMuteSoundOff(boolean muteSoundOff) {
        Settings.muteSoundOff = muteSoundOff;
    }

    public static void calculateAspectRatio(){
        if (screenHeightPixels != 0 && screenWidthPixels != 0) {
            float ratioValue = ((float) screenWidthPixels / (float) screenHeightPixels);
            if (ratioValue <= 1.05) {aspectRatio = 1;}      //Equivalent to 1:1 (1)
            if (ratioValue > 1.05 && ratioValue <= 1.62) {aspectRatio = 2;}  //Equivalent to 4:3 (1.33)
            if (ratioValue > 1.62){aspectRatio = 3;}    //Equivalent to 16:9 / 16:10 (1.78)
        }
    }

    public static int getSeedForRandom()    { return seedForRandom; }

    public static int getSeedForRandomMax() { return seedForRandomMax; }

    public static int getLevelReachedByPlayer() { return levelReachedByPlayer; };
}