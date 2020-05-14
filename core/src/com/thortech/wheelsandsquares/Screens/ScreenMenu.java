package com.thortech.wheelsandsquares.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.thortech.wheelsandsquares.AbstractGameScreen;
import com.thortech.wheelsandsquares.Screens.ScreenLoad;
import com.thortech.wheelsandsquares.Settings;
import com.thortech.wheelsandsquares.WheelsAndSquares;


//TODO: change screen resolution in to "Potato units"
public class ScreenMenu extends AbstractGameScreen {

    private static final String TAG = ScreenLoad.class.getName();

    private OrthographicCamera camera;
    private FillViewport sViewport;
    private Stage stage;

    //Table filling the entire screen
    private Table rootTable;
    private Pixmap pixmap;

    //Buttons etc. for the screen
    private Button btnPreferences;
    private Button btnGame;
    //TODO ADD BUTTONS and stuff

    private TextButton.TextButtonStyle buttonStyle;


    public ScreenMenu(WheelsAndSquares _game) {
        super(_game);

        stage  = new Stage();
        stage.clear();
        Gdx.input.setInputProcessor(stage); //** stage is responsive **//

        pixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        pixmap.setColor(Settings.skin.getColor("tan"));       //Use the color made in the uiskin.json file
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

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.PHYSICALWIDTH, Settings.PHYSICALHEIGHT);
        sViewport = new FillViewport(Settings.PHYSICALWIDTH, Settings.PHYSICALHEIGHT, camera);
        sViewport.apply();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        camera.update();

        //TODO: Test
        /*
        Settings.getThemeMusic().setVolume(Settings.getThemeMusicLevel());
        Settings.getThemeMusic().play();
*/

        this.create();

        rootTable.add(btnPreferences).colspan(2).width(250);
//        rootTable.add(btnPreferences).width(camera.viewportWidth / 2).height(camera.viewportHeight / 8);
        rootTable.row();
//        rootTable.add(btnGame).width(camera.viewportWidth / 2).height(camera.viewportHeight / 8);
        rootTable.add(btnGame).colspan(2).width(250);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.2f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        //Draw all the actors offset 0,0 is lower left corner. camera.viewportWidth, camera.viewportHeight is upper right corner.
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        //TODO: it is not possible to hit the buttons after resize (needed for desktop app only)
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
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

    private void create(){
        buttonStyle = new TextButton.TextButtonStyle(Settings.skin.get(TextButton.TextButtonStyle.class));
//        buttonStyle = new TextButton.TextButtonStyle(Settings.skin.get(TextButton.TextButtonStyle.class));
//        buttonStyle = new ImageTextButton.ImageTextButtonStyle(Settings.skin.get(ImageTextButton.ImageTextButtonStyle.class));

        btnPreferences = new TextButton("Preferences", Settings.skin, "toggle");
        btnGame = new TextButton("Play", buttonStyle);

        btnPreferences.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(WheelsAndSquares.screenPreference);
                //dispose();
            }
        });

        btnGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //Todo Loglevel DEBUG - write to Log that button is pressed.
                game.setScreen(WheelsAndSquares.screenGame);
                dispose();
            }
        });
    }
}
