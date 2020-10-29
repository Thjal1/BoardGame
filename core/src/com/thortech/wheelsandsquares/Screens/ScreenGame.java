package com.thortech.wheelsandsquares.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.*;
import com.thortech.wheelsandsquares.AbstractGameScreen;
import com.thortech.wheelsandsquares.Logic.GameLogics;
import com.thortech.wheelsandsquares.Scenes.BoardStage;
import com.thortech.wheelsandsquares.Scenes.DebugHud;
import com.thortech.wheelsandsquares.Scenes.Hud;
import com.thortech.wheelsandsquares.Settings;
import com.thortech.wheelsandsquares.WheelsAndSquares;

/**
 * Created by ToHj on 31-01-2020.
 *
 */
public class ScreenGame extends AbstractGameScreen {

    private static final String TAG = com.thortech.wheelsandsquares.Screens.ScreenGame.class.getName();

    private GameLogics logics;
    private BoardStage boardStage;

    private OrthographicCamera cameraGame;      //For the game play

    private Viewport viewPort;
    private Hud hud;
    private DebugHud debugHud;
    private boolean debugPauseCamera = true;
    private Vector3 boardVector;



    public ScreenGame(WheelsAndSquares game) {
            super(game);
            cameraGame = new OrthographicCamera();
            cameraGame.setToOrtho(false, Settings.PHYSICALWIDTH, Settings.PHYSICALHEIGHT);

            viewPort = new FitViewport(cameraGame.viewportWidth, cameraGame.viewportHeight, cameraGame);
            viewPort.apply(true);

            boardStage = new BoardStage(this.game);
            hud = new Hud(this.game);

            if (WheelsAndSquares.platformSpecific.isDebug()) {
                debugHud = new DebugHud(this.game.batch);
            }

            cameraGame.position.set(viewPort.getWorldWidth() / 2, viewPort.getWorldHeight() / 2, 0);

            logics = new GameLogics(this.game);

            //Load level

            boardStage = logics.getCurrentBoard();
            boardVector = new Vector3(cameraGame.position.x, cameraGame.position.y, 0);
            boardStage.changePos(boardVector, true);

    }

    public void handlInput(float dt) {
    }

    public void update(float dt) {
        handlInput(dt);
        hud.update(dt);

        }

    @Override
    public void render(float delta) {

        if (WheelsAndSquares.platformSpecific.isDebug()) {
            delta = 0.01f;
        }

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);


        //Let the fun begin
        game.batch.setProjectionMatrix(cameraGame.combined);
        cameraGame.update();

        game.batch.begin();
        //Render the board
        boardStage.render(delta);

        game.batch.end();




        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (WheelsAndSquares.platformSpecific.isDebug()) {
            game.batch.setProjectionMatrix(debugHud.stage.getCamera().combined);
            debugHud.setCameraXPos(cameraGame.position.x);
            debugHud.update(delta);
            debugHud.stage.draw();
        }
    }

    @Override
    //Input in pixels
    public void resize(int width, int height) {
        Settings.resizeScreenPixels(width, height);
        viewPort.update(width, height, true);
        boardStage.resize(width, height);
        //hud.resize(width, height);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(boardStage.stage);
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
        //todo: dispose all the actors!
        hud.dispose();
        logics.dispose();
    }
}