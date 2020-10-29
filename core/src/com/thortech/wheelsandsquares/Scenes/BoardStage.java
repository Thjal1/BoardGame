package com.thortech.wheelsandsquares.Scenes;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.thortech.wheelsandsquares.Actors.AbstractActor;
import com.thortech.wheelsandsquares.Actors.Tile;
import com.thortech.wheelsandsquares.Actors.Wheel;
import com.thortech.wheelsandsquares.Helpers.LogHelper;
import com.thortech.wheelsandsquares.Settings;
import com.thortech.wheelsandsquares.WheelsAndSquares;

/**
 * Created by ToHj on 28-02-2016.
 */
public class BoardStage extends AbstractActor {
    private static final String TAG = BoardStage.class.getName();

    public static final int differentKindsOfColours = Tile.differentKindsOfColours;

    boolean debugCalledFirstTime = false;

    private OrthographicCamera camera;
    private Viewport viewport;
    public Stage stage;

    private float physicalHeight;
    private float physicalWidth;
    private Vector3 centerV3;
    private Texture boardImage;
    private Sprite boardSprite;


    private Array<Array<Tile>> tiles = new Array<Array<Tile>>();
    private Array<Wheel> wheels = new Array<Wheel>();

    private String state = new String();

    int numbersOfTilesX, numbersOfTilesY = 0;



    public BoardStage(WheelsAndSquares game) {
        super(game);
        create();
    }

    private void create() {

        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        stage = new Stage(viewport, this.game.batch);
        stage.setDebugAll(true);
        Gdx.input.setInputProcessor(stage);

        physicalHeight = Settings.PHYSICALHEIGHT;
        physicalWidth = Settings.PHYSICALWIDTH;

        centerV3 = new Vector3(0, 0, 0);

        String file = "badlogic.jpg";   //TODO: New image!
        boardImage = new Texture(Gdx.files.internal(file));
        boardSprite = new Sprite(boardImage);

        boardSprite.setPosition(centerV3.x, centerV3.y);
        boardSprite.setSize(physicalWidth, physicalHeight);

        setState("Still");
    }


    @Override
    public void render(float dt) {
        game.batch.draw(boardSprite, centerV3.x, centerV3.y, physicalWidth, physicalHeight);

        //Draw the board
        for (Array<Tile> arrayTiles : tiles) {
            for (Tile tile : arrayTiles)
            {
                tile.render(dt);
            }
        }

        //Animate and draw the wheels
        boolean gameBatchIsDrawing = false;
        if (game.batch.isDrawing()) {
            gameBatchIsDrawing = true;
            game.batch.end();
        }

        if(!debugCalledFirstTime) {
            if (stage.getActors().notEmpty()) {
                LogHelper.Log(TAG, stage.getActors().first().getName(), Application.LOG_INFO);
                debugCalledFirstTime = true;
            }
        }
        if(stage.getActors().notEmpty())
            stage.setKeyboardFocus(stage.getActors().first());
        stage.act(dt);
        stage.draw();

        if (gameBatchIsDrawing) {
            game.batch.begin();
        }
    }

    public void changePos(Vector3 v3, boolean center) {
        if (center == false) {
            this.centerV3 = v3;
        } else {
            this.centerV3.y = v3.y - physicalWidth / 2;
            this.centerV3.x = v3.x - physicalHeight / 2;
        }
    }

    public void setState(String state) {
        this.state = state;
    }


    /**
     * @param numbersOfTilesX
     * @param numbersOfTilesY
     */

    public void setEmptyBoard(int numbersOfTilesX, int numbersOfTilesY)
    {
        this.numbersOfTilesX = numbersOfTilesX;
        this.numbersOfTilesY = numbersOfTilesY;

        float positionX, positionY = 0;
        float boardCornerPositionX = ((Settings.PHYSICALWIDTH / Tile.getPhysicalWidth() - numbersOfTilesX) /2) * Tile.getPhysicalWidth();
        float boardCornerPositionY = ((Settings.PHYSICALHEIGHT / Tile.getPhysicalHeight() - numbersOfTilesY) /2) * Tile.getPhysicalHeight();

        //Create the tiles for the board
        for (int x = 0; x < numbersOfTilesX; x++) {
            positionX = x*Tile.getPhysicalWidth() + boardCornerPositionX;
            tiles.add(new Array<Tile>());
            for (int y = 0; y < numbersOfTilesY; y++) {
                positionY = y*Tile.getPhysicalHeight() + boardCornerPositionY;
                tiles.get(x).add(new Tile(game, new Vector3(positionX, positionY,0)));
            }
        }
    }

    public void dispose() {

        stage.dispose();
        boardSprite.getTexture().dispose();
    }


    //Todo:
    public int getTileColourValue(int x, int y) {
        return 0; //tableOfTiles getColourValue();
    }

    //Todo:
    public boolean placeNewWheel(Vector2 boardCoordinates, int typeNumber) {
        //Are the coordinates within the bounds of the board?
        if (boardCoordinates.x > numbersOfTilesX || boardCoordinates.y > numbersOfTilesY || boardCoordinates.x < 0 || boardCoordinates.y < 0) {
            LogHelper.Log(TAG, "Wheel coordinates are set outside boundaries of the board", Application.LOG_INFO);
            return false;
            //throw new Exception("Wheel coordinates are set outside boundaries of the board");
        }

        float wheelCornerPositionX = ((Settings.PHYSICALWIDTH / Tile.getPhysicalWidth() - numbersOfTilesX) / 2) * Tile.getPhysicalWidth();
        float wheelCornerPositionY = ((Settings.PHYSICALHEIGHT / Tile.getPhysicalHeight() - numbersOfTilesY) / 2) * Tile.getPhysicalHeight();
        Vector3 physicalVector = new Vector3(wheelCornerPositionX + boardCoordinates.x * Tile.getPhysicalWidth(), wheelCornerPositionY + boardCoordinates.y * Tile.getPhysicalHeight(), 0);

        //Is there all ready a wheel on this spot?
        if (wheels.notEmpty()) {
            boolean wheelIsFound = false;
            for (Wheel wheel : wheels) {
                if (wheel.getFromCoordinates() == boardCoordinates)
                    wheelIsFound = true;
            }
            //Wheel not found on the spot - create it.
            if (!wheelIsFound) {
                //wheels.add(new Wheel(game, physicalVector, Wheel.WheelTypes.getEnum(typeNumber)));
                Wheel wheel = new Wheel(game, physicalVector, Wheel.WheelTypes.getEnum(typeNumber));
                stage.addActor(wheel);
                stage.setKeyboardFocus(wheel);
                return true;
            } else {
                LogHelper.Log(TAG, "Wheel coordinates are same as a all ready placed wheel", Application.LOG_INFO);
                return false;
            }
        }
        //wheels is empty: create the new wheel
        else {
            Wheel wheel = new Wheel(game, physicalVector, Wheel.WheelTypes.getEnum(typeNumber));
            stage.addActor(wheel);
            stage.setKeyboardFocus(wheel);
            return true;
        }
    }

    //Todo:
    public void setTileColour(int x, int y, int colourNumber) {
    Tile.TileColours colour = Tile.TileColours.getEnum(colourNumber);
    tiles.get(x).get(y).setColour(colour);
    }

    public BoardStage getBoard()
    {
        return this;
    }

    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, true);
    }
}