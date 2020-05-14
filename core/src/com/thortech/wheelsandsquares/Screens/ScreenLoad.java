package com.thortech.wheelsandsquares.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.thortech.wheelsandsquares.AbstractGameScreen;
import com.thortech.wheelsandsquares.Settings;
import com.thortech.wheelsandsquares.WheelsAndSquares;


//public class ScreenLoad implements Screen {
public class ScreenLoad extends AbstractGameScreen {

    private static final String TAG = com.thortech.wheelsandsquares.Screens.ScreenLoad.class.getName();

    private OrthographicCamera camera;
    private StretchViewport sViewport;

    private float elapsedTime = 0;
    private int regionHeight = 0;
    private int regionWidth =0;

    private long timeStart = 0;
    private long timeShown = 5000; //ms
    private float percentShown = (float) 0.0; // in percent
    private long timePast = 0;
    private float themeMusicLevel;
    private float musicLevel;

    public ScreenLoad(WheelsAndSquares _game) {
        super(_game);

        musicLevel = 0.0f;

        Settings.getThemeMusic().setLooping(true);
        Settings.getThemeMusic().setVolume(0.0f);
        Settings.getThemeMusic().play();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.PHYSICALWIDTH, Settings.PHYSICALHEIGHT);
        sViewport = new StretchViewport(Settings.PHYSICALWIDTH, Settings.PHYSICALHEIGHT, camera);
        sViewport.apply();

        camera.update();

        create();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255, 255, 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);
        //Add the graphics for the Splash screen
        game.batch.begin();

        //Add the rendering for the Splash screen
        //humpty.render(delta);

        game.batch.end();

        Settings.calculateAspectRatio();

        timePast = System.currentTimeMillis();

        //Fade in for the music
        if (Settings.isMuteMusicOff()) {
            percentShown = (float) (timePast - timeStart) / timeShown;
            musicLevel = percentShown * themeMusicLevel;
            Settings.getThemeMusic().setVolume(musicLevel);
            Settings.getThemeMusic().play();
        }

        if ((Gdx.input.isTouched() || (timePast - timeStart) > timeShown)) {
            game.setScreen(WheelsAndSquares.screenMenu);
            dispose();
        }
    }
    public void create(){
        themeMusicLevel = Settings.getThemeMusicLevel();
        timeStart = System.currentTimeMillis();

        /*
        humpty = new Humpty(game);
        humpty.changePos(new Vector3(camera.position.x, camera.position.y,0), true);
        humpty.setState("FLYING");

         */
    }

    @Override
    public void resize(int width, int height) {
        sViewport.update(width,height);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        //humpty.dispose();
    }
}
