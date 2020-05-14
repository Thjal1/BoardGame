package com.thortech.wheelsandsquares.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.thortech.wheelsandsquares.Settings;

/**
 * Created by ToHj on 20-03-2016.
 */
public class DebugHud {

    public Stage stage;
    private Viewport viewport;

    private StringBuilder strB1 = new StringBuilder();
    private StringBuilder strB2 = new StringBuilder();

    private float fps;
    private float cameraX;

    Label cameraXLabel;
    Label fpsLabel;

    public DebugHud(SpriteBatch sb) {
        fps = 0f;
        cameraX = 0f;

        viewport = new FitViewport(Settings.PHYSICALWIDTH, Settings.PHYSICALHEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table();
        table.bottom().right();
        table.setFillParent(true);

        cameraXLabel = new Label("Camera pos. X " + strB1.append(cameraX), Settings.skin);
        fpsLabel = new Label("FPS " + strB2.append(fps), Settings.skin);

        table.add(cameraXLabel).expandX();
        table.add(fpsLabel).expandX();
        stage.addActor(table);

    }

    public void setCameraXPos(float x){
        cameraX = x;
    }

    public void update(float dt){
        fps = Gdx.graphics.getFramesPerSecond();
        strB1.delete(0, strB1.capacity());
        strB2.delete(0, strB2.capacity());
        cameraXLabel.setText("Camera pos. X " + strB1.append(cameraX));
        fpsLabel.setText("FPS: " + strB2.append(fps));

    }
    public void dispose(){
        stage.dispose();
    }
}
