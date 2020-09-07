package com.thortech.wheelsandsquares.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.thortech.wheelsandsquares.Settings;
import com.thortech.wheelsandsquares.WheelsAndSquares;

/**
 * Created by ToHj on 19-02-2016.
 * ToDO: Modify for the game
 */
public class Hud {
    private static final String TAG = Hud.class.getName();

    public Stage stage;
    private Viewport viewport;

    private WheelsAndSquares game;

    private Integer worldTimer;
    private float timeCounter;
    private float timeCount;
    private Integer score;
    private Integer level;
    private StringBuilder strB1 = new StringBuilder();
    private StringBuilder strB2 = new StringBuilder();
    private StringBuilder strB3 = new StringBuilder();

    Label countdownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;

    public Hud (WheelsAndSquares _game){
        game = _game;
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        level = 1;

        viewport = new FitViewport(Settings.PHYSICALWIDTH, Settings.PHYSICALHEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.batch);

        Table table = new Table();
        table.top();
        //table.center();
        table.setFillParent(true);

        countdownLabel = new Label(strB1.append(worldTimer), Settings.skin);//String.format("%03d", worldTimer), Settings.skin);
        scoreLabel = new Label(strB2.append(score), Settings.skin);
        timeLabel = new Label("TIME", Settings.skin);
        levelLabel = new Label(strB3.append(level) + "-" + "X", Settings.skin);
        worldLabel = new Label("WORLD", Settings.skin);

        table.add().expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().pad(10);
        table.row();
        table.add().expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    public void update(float dt){
        timeCount += dt;
        worldTimer = (int)timeCount;
        strB1.delete(0, strB1.capacity());
        countdownLabel.setText(strB1.append(worldTimer));

        stage.draw();
    }

    public void addScore(int value){
        score += value;
        //scoreLabel.setText(stringBuilder.append(score));
    }

    public void dispose() {
        stage.dispose();
    }

//    public boolean isTimeUp() { return timeUp; }
}
