package com.thortech.wheelsandsquares.Actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.thortech.wheelsandsquares.Settings;
import com.thortech.wheelsandsquares.WheelsAndSquares;

/**
 * Created by ToHj on 28-02-2016.
 */
public class Board extends AbstractActor {
    private static final String TAG = com.thortech.wheelsandsquares.Actors.Board.class.getName();

    public static final int differentKindsOfColours = Tile.differentKindsOfColours;

    private float regionHeight;
    private float regionWidth;
    private Vector3 centerV3;
    private Texture boardImage;
    private int numberOfTilesX;
    private int NumberOfTilesY;

    private float elapsedTime = 0;

    private Array<Array<Tile>> tiles = new Array<Array<Tile>>();
    private Array<Array<Wheel>> wheels = new Array<Array<Wheel>>();

    public Board(WheelsAndSquares _game)
    {
        super(_game);
        create();
    }

    private void create()
    {
        regionHeight = Settings.PHYSICALSCALE;
        regionWidth = Settings.PHYSICALSCALE;

        centerV3 = new Vector3(0,0,0);
    }

    public void setEmptyBoard(int numbersOfTilesX, int numbersOfTilesY)
    {
        for (int x = 0; x < numbersOfTilesX; x++) {
            tiles.add(new Array<Tile>());
            wheels.add(new Array<Wheel>());
            for (int y = 0; y < numbersOfTilesY; y++) {
                tiles.get(x).add(new Tile(game));
                wheels.get(x).add(new Wheel(game));
            }
        }
    }

    public int getTileColourValue(int x, int y)
    {
        return tiles.get(x).get(y).getColourValue();
    }

    public int getWheelTypeValue(int x, int y)
    {
        return wheels.get(x).get(y).getTypeValue();
    }

    public void setTileColour(int x, int y, int colourNumber)
    {
        Tile.TileColours colour = Tile.TileColours.getEnum(colourNumber);
        tiles.get(x).get(y).setColour(colour);
    }

    public void setWheelType(int x, int y, int typeNumber)
    {
        Wheel.WheelTypes type = Wheel.WheelTypes.getEnum(typeNumber);
        wheels.get(x).get(y).setType(type);
    }

    @Override
    public void render (float dt)
    {

        elapsedTime += dt;

        for (Array<Tile> arrayTiles : tiles) {
            for (Tile tile : arrayTiles)
            {
                tile.render(dt);
            }
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
            this.centerV3.y = v3.y - regionWidth / 2;
            this.centerV3.x = v3.x - regionHeight / 2;
        }
    }
    public void setState(String _state){

    }

    public void dispose() {

    }
}
