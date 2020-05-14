package com.thortech.wheelsandsquares.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.thortech.wheelsandsquares.AbstractGameScreen;
import com.thortech.wheelsandsquares.Settings;
import com.thortech.wheelsandsquares.WheelsAndSquares;


/**
 * Created by ToHj on 23-01-2016.
 * Setting various preferences for the game - See the documentation for details
 * This screen is build by the use of libgdx tables, read more: https://github.com/libgdx/libgdx/wiki/Table
 */


//TODO: change screen resolution in to "Potato units"
public class ScreenPreferences extends AbstractGameScreen {
    OrthographicCamera camera;
    private Stage stage;
    private Viewport viewPort;

    private float themeMusicLevel;
    private float soundLevel;
    private Sound soundTest;
    private boolean vibrate;

    //Table filling the entire screen
    private Table rootTable;
    private Pixmap pixmap;

    //options
    private CheckBox chkSound;
    private CheckBox chkMusic;
    private CheckBox chkVibrate;
    private Slider sldMusic;
    private Slider sldSound;
    private Label lblMusicLevel;
    private Label lblSoundLevel;
    private Label lblVibrate;
    private Button btnSave;
    private Button btnBack;

    private TextButtonStyle buttonStyle;

    public ScreenPreferences(WheelsAndSquares _game) {
        super(_game);

        this.create();
    }
    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        //make a save button which calls settings.saveall();

        game.batch.begin();
        game.batch.end();

        //TODo: Omitted for test:
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        create();
        Gdx.input.setInputProcessor(stage); //** stage is responsive **//
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
        stage.dispose();
    }

    //@Override
    private void create(){

        //Create and set the stage
        stage  = new Stage();
        stage.clear();
        Gdx.input.setInputProcessor(stage); //** stage is responsive **//

        pixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        pixmap.setColor(Settings.skin.getColor("light-tan"));       //Use the color made in the uiskin.json file
        pixmap.fill();

        //create and set the rootTable
        rootTable = new Table(Settings.skin);
        stage.addActor(rootTable);
        rootTable.setFillParent(true);
        //Set the background of the rootTable to use pixmap.
        rootTable.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));

        if (WheelsAndSquares.platformSpecific.isDebug()){
            rootTable.setDebug(true);   //Turn on all debug lines - if only tables own lines are needed do: rootTable.debugTable(true);
        }

        //Create and set the camera
        camera = new OrthographicCamera();
        viewPort = new StretchViewport(Settings.PHYSICALWIDTH, Settings.PHYSICALHEIGHT,camera);

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        buttonStyle = new TextButtonStyle(Settings.skin.get(TextButtonStyle.class));
        btnSave = new TextButton("Save", buttonStyle);
        btnBack = new TextButton("Back", buttonStyle);

        sldMusic = new Slider(0, 1, 0.1f, false, Settings.skin);
        sldMusic.setAnimateDuration(0.3f);
        themeMusicLevel = Settings.getThemeMusicLevel();
        sldMusic.setValue(themeMusicLevel);
        lblMusicLevel = new Label("Music", Settings.skin);
/*
        soundTest = Gdx.audio.newSound(Gdx.files.internal("General/jump2.wav"));
        sldSound = new Slider(0, 1, 0.1f, false, Settings.skin);
        sldSound.setAnimateDuration(0.3f);
        soundLevel = Settings.getSoundLevel();
        sldSound.setValue(soundLevel);
        lblSoundLevel = new Label("Sound", Settings.skin);
*/
        chkMusic = new CheckBox("", Settings.skin, "switch");
        chkMusic.setChecked(Settings.isMuteMusicOff());

        chkSound = new CheckBox("", Settings.skin, "switch");
        chkSound.setChecked(Settings.isMuteMusicOff());

        lblVibrate = new Label("Vibrate", Settings.skin);
        chkVibrate = new CheckBox("", Settings.skin, "switch");

        btnSave.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Settings.setThemeMusicLevel(themeMusicLevel);
                Settings.setSoundLevel(soundLevel);
                Settings.saveAll();
                dispose();
                game.setScreen(WheelsAndSquares.screenMenu);
            }
        });

        btnBack.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(WheelsAndSquares.screenMenu);
            }
        });
        sldMusic.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (chkMusic.isChecked()) {
                    themeMusicLevel = sldMusic.getValue();
                    Settings.getThemeMusic().setVolume(themeMusicLevel);
                    Settings.getThemeMusic().play();
                }
            }
        });
/*
        sldSound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                soundLevel = sldSound.getValue();
                if (!sldSound.isDragging() && chkSound.isChecked()){
                    soundTest.play(soundLevel);
                }
                else {
                    soundTest.stop();
                }
            }
        });
*/
        chkMusic.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (chkMusic.isChecked()) {
                    Settings.setMuteMusicOff(true);
                    Settings.getThemeMusic().setVolume(themeMusicLevel);
                    Settings.getThemeMusic().play();
                } else {
                    Settings.setMuteMusicOff(false);
                    Settings.getThemeMusic().stop();
                }
            }
        });

        chkSound.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(chkSound.isChecked()) {
                    Settings.setMuteSoundOff(true);
                    soundTest.play(Settings.getSoundLevel());
                }
                else {
                    Settings.setMuteSoundOff(false);
                }
            }
        });

        chkVibrate.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(chkVibrate.isChecked()) {
                    Settings.setVibrate(true);
                    //TODO: TEST ON AN ANDROID IF THIS WORKS
                    Gdx.input.vibrate(1000);
                } else {
                    Settings.setVibrate(false);
                }
            }
        });
        rootTable.add(lblMusicLevel).colspan(2);
        rootTable.row();
        rootTable.add(sldMusic);
        rootTable.add(chkMusic).width(70);
        rootTable.row();
        rootTable.add(lblSoundLevel).colspan(2).padTop(20).bottom();
        rootTable.row();
//        rootTable.add(sldSound);
//        rootTable.add(chkSound).width(70);
        rootTable.row();
        rootTable.add(lblVibrate).colspan(2).padTop(20).bottom();
        rootTable.row();
        rootTable.add(chkVibrate).colspan(2);
        rootTable.row();
        rootTable.add(btnSave).padTop(20).bottom().left();
        rootTable.add(btnBack).padTop(20).bottom().right();
    }
}
