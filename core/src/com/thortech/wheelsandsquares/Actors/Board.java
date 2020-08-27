package com.thortech.wheelsandsquares.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.thortech.wheelsandsquares.Settings;
import com.thortech.wheelsandsquares.WheelsAndSquares;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ToHj on 28-02-2016.
 */
public class Board extends AbstractActor {
    private static final String TAG = com.thortech.wheelsandsquares.Actors.Board.class.getName();

    public static final int differentKindsOfColours = Tile.differentKindsOfColours;

    private float physicalHeight;
    private float physicalWidth;
    private Vector3 centerV3;
    private Texture boardImage;
    private Sprite boardSprite;

    int numbersOfTilesX, numbersOfTilesY = 0;

    private float elapsedTime = 0;

    private Array<Array<Tile>> tiles = new Array<Array<Tile>>();
    private Array<Wheel> wheels = new Array<Wheel>();

    public Board(WheelsAndSquares _game)
    {
        super(_game);
        create();
    }

    private void create()
    {
        physicalHeight = Settings.PHYSICALHEIGHT;
        physicalWidth = Settings.PHYSICALWIDTH;

        centerV3 = new Vector3(0,0,0);

        String file = "badlogic.jpg";   //TODO: New image!
        boardImage = new Texture(Gdx.files.internal(file));
        boardSprite = new Sprite(boardImage);

        boardSprite.setPosition(centerV3.x, centerV3.y);
        boardSprite.setSize(physicalWidth,physicalHeight);
    }

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

    public int getTileColourValue(int x, int y)
    {
        return tiles.get(x).get(y).getColourValue();
    }

    public void setTileColour(int x, int y, int colourNumber)
    {
        Tile.TileColours colour = Tile.TileColours.getEnum(colourNumber);
        tiles.get(x).get(y).setColour(colour);
    }

    public void placeNewWheel(Vector2 boardCoordinates, int typeNumber) throws Exception {
        //Are the coordinates within the bounds of the board?
        if(boardCoordinates.x > numbersOfTilesX || boardCoordinates.y > numbersOfTilesY || boardCoordinates.x < 0 || boardCoordinates.y < 0) {
            throw new Exception("Wheel coordinates are set outside boundaries of the board");
        }

        float wheelCornerPositionX = ((Settings.PHYSICALWIDTH / Tile.getPhysicalWidth() - numbersOfTilesX) /2) * Tile.getPhysicalWidth();
        float wheelCornerPositionY = ((Settings.PHYSICALHEIGHT / Tile.getPhysicalHeight() - numbersOfTilesY) /2) * Tile.getPhysicalHeight();
        Vector3 physicalVector = new Vector3(wheelCornerPositionX + boardCoordinates.x * Tile.getPhysicalWidth() , wheelCornerPositionY + boardCoordinates.y * Tile.getPhysicalHeight(),0);

        //Is there all ready a wheel on this spot?
        if(wheels.notEmpty()){
            boolean wheelIsFound = false;
            for (Wheel wheel : wheels) {
                if(wheel.getFromCoordinates() == boardCoordinates)
                    wheelIsFound = true;
            }
            //Wheel not found on the spot - create it.
            if(!wheelIsFound)
                wheels.add(new Wheel(game, physicalVector, Wheel.WheelTypes.getEnum(typeNumber)));
            else
                throw new Exception("Wheel coordinates are same as a all ready placed wheel");
        }
        //wheels is empty: create the new wheel
        else {
           wheels.add(new Wheel(game, physicalVector, Wheel.WheelTypes.getEnum(typeNumber)));
        }
    }

    @Override
    public void render (float dt)
    {
        elapsedTime += dt;
        game.batch.draw(boardSprite, centerV3.x, centerV3.y, physicalWidth, physicalHeight);

        for (Array<Tile> arrayTiles : tiles) {
            for (Tile tile : arrayTiles)
            {
                tile.render(dt);
            }
        }

        for (Wheel wheel : wheels) {
                wheel.render(dt);
        }
    }

    public Vector3 getPos(){
        return centerV3;
    }
    public void changePos(Vector3 v3, boolean center)
    {
        if (center == false){
            this.centerV3 = v3;
        }
        else{
            this.centerV3.y = v3.y - physicalWidth / 2;
            this.centerV3.x = v3.x - physicalHeight / 2;
        }
    }
    public void setState(String _state){

    }

    public void dispose()
    {
        for (Array<Tile> arrayTiles : tiles) {
            for (Tile tile : arrayTiles) {
                tile.dispose();
            }
        }
            for (Wheel wheel : wheels)
            {
                wheel.dispose();
            }
        boardSprite.getTexture().dispose();
    }
}
