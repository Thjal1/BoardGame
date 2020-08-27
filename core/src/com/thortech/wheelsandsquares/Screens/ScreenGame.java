package com.thortech.wheelsandsquares.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.*;
import com.thortech.wheelsandsquares.AbstractGameScreen;
import com.thortech.wheelsandsquares.Actors.Board;
import com.thortech.wheelsandsquares.Logic.GameLogics;
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
    private Board board;     //= new Board(game);

    private OrthographicCamera cameraGame;      //For the game play

    private Viewport viewPort;
    private Hud hud;
    private DebugHud debugHud;
    private boolean debugPauseCamera = true;
    private Vector3 boardVector;



    public ScreenGame(WheelsAndSquares _game) {
            super(_game);
            cameraGame = new OrthographicCamera();
            cameraGame.setToOrtho(false, Settings.PHYSICALWIDTH, Settings.PHYSICALHEIGHT);

            //viewPort = new StretchViewport(Settings.PHYSICALWIDTH, Settings.PHYSICALHEIGHT, cameraGame);
            //viewPort = new ExtendViewport(Settings.PHYSICALWIDTH, Settings.PHYSICALHEIGHT, Settings.PHYSICALWIDTH*2, Settings.PHYSICALHEIGHT*2, cameraGame);
            viewPort = new FitViewport(cameraGame.viewportWidth, cameraGame.viewportHeight, cameraGame);
            viewPort.apply(true);

            hud = new Hud(game);

            if (WheelsAndSquares.platformSpecific.isDebug()) {
                debugHud = new DebugHud(game.batch);
            }

            cameraGame.position.set(viewPort.getWorldWidth() / 2, viewPort.getWorldHeight() / 2, 0);

            logics = new GameLogics(game);

            //Load level

            board = logics.getCurrentBoard();
            boardVector = new Vector3(cameraGame.position.x, cameraGame.position.y, 0);
            board.changePos(boardVector, true);

    }

    public void handlInput(float dt) {
    }

    public void update(float dt) {
        handlInput(dt);

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
        board.render(delta);
        game.batch.end();

        /*
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.update(delta);
        hud.stage.draw();
*/
        if (WheelsAndSquares.platformSpecific.isDebug()) {
       /*
            game.batch.setProjectionMatrix(debugHud.stage.getCamera().combined);
            debugHud.setCameraXPos(cameraGame.position.x);
            debugHud.update(delta);
            debugHud.stage.draw();
 */
        }
    }

    @Override
    //Input in pixels
    public void resize(int width, int height) {
        Settings.resizeScreenPixels(width, height);
        viewPort.update(width, height, true);
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
        //todo: dispose all the actors!
        ///hud.dispose();
        logics.dispose();
    }
}