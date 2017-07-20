package de.marekparucha.newtowerdefense.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.marekparucha.newtowerdefense.NewTowerDefense;
import de.marekparucha.newtowerdefense.actor.hud.PlayerHUD;
import de.marekparucha.newtowerdefense.actor.tiles.BuildingTile;
import de.marekparucha.newtowerdefense.actor.tiles.listeners.BuildingTileListener;
import de.marekparucha.newtowerdefense.gameplay.controller.GameController;

/**
 * Created by Marek on 18.07.2017.
 */

public class Level implements InputProcessor, Screen, GestureDetector.GestureListener {

    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    private Viewport viewportInGame;
    private Viewport viewportHUD;
    private NewTowerDefense ntd;
    private float currentZoom;
    private Stage stageBuilding;
    private GameController gameController;
    private PlayerHUD playerHUD;



    public Level(NewTowerDefense ntd, int level, PlayerHUD playerHUD)
    {
        this.ntd = ntd;
        this.playerHUD = playerHUD;
    }
    @Override
    public void show() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();


        camera = new OrthographicCamera(w,h);
       /* viewportHUD = new FillViewport(1080,1920);
        playerHUD.setViewport(viewportHUD);
        playerHUD.setPositions();*/
        viewportInGame = new FillViewport(1024,2048,camera);
        viewportInGame.apply();
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        Gdx.app.log("camera", camera.position.toString());
        tiledMap = new TmxMapLoader().load("maps/map1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        stageBuilding = new Stage();

        InputProcessor inputProcessorOne = stageBuilding;
        InputProcessor inputProcessorTwo = new GestureDetector(this);
        InputProcessor inputProcessorThree = playerHUD;
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessorOne);
        inputMultiplexer.addProcessor(inputProcessorTwo);
        inputMultiplexer.addProcessor(inputProcessorThree);
        Gdx.input.setInputProcessor(inputMultiplexer);

        currentZoom = camera.zoom;
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("Kachelebene 4");
        createBuildingForLayer(layer);

        TiledMapTileSets set = tiledMap.getTileSets();

        Gdx.app.log("x",set.getTile(255)+"");
        Gdx.app.log("y",set.getTile(255).getTextureRegion().getRegionY()+"");
        for( int i = 0; i < layer.getHeight()/layer.getTileHeight();i++)
        {
            for (int j = 0; j < layer.getWidth()/layer.getTileWidth();j++)
            {
                System.out.println("test");
            }
        }

    }

    @Override
    public void render(float delta) {
        camera.update();
        ntd.batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor((float)172/255,(float)172/255,(float)172/255,1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ntd.batch.begin();
        stageBuilding.setViewport(viewportInGame);
        stageBuilding.act();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        ntd.batch.end();
        playerHUD.draw();
    }

    private void updateBoundaries()
    {
        // These values likely need to be scaled according to your world coordinates.
        // The left boundary of the map (x)
        int mapLeft = 0;
        // The right boundary of the map (x + width)
        float mapRight = 0;
        // The bottom boundary of the map (y)
        int mapBottom = 0;
        // The top boundary of the map (y + height)
        float mapTop = 0;
        // The camera dimensions, halved
        float cameraHalfWidth = 0;
        float cameraHalfHeight = 0;

// Move camera after player as normal

        float cameraLeft = 0;
        float cameraRight = 0;
        float cameraBottom = 0;
        float cameraTop = 0;
// The right boundary of the map (x + width)
        mapRight = 1024;
// The top boundary of the map (y + height)
        mapTop = 2048 +(143*camera.zoom);
// The camera dimensions, halved
        cameraHalfWidth = camera.viewportWidth * .5f*camera.zoom;
        cameraHalfHeight = (camera.viewportHeight-228) * .5f*camera.zoom;

// Move camera after player as normal

        cameraLeft = camera.position.x - cameraHalfWidth;
        cameraRight = camera.position.x + cameraHalfWidth;
        cameraBottom = camera.position.y - cameraHalfHeight;
        cameraTop = camera.position.y + cameraHalfHeight;

        // Horizontal axis
        if( 1024 < camera.viewportWidth)
        {
            camera.position.x = mapRight / 2;
        }
        else if(cameraLeft <= mapLeft)
        {
            camera.position.x = mapLeft + cameraHalfWidth;
        }
        else if(cameraRight >= mapRight)
        {
            camera.position.x = mapRight - cameraHalfWidth;
        }

// Vertical axis
        if(2048+(143*camera.zoom) < camera.viewportHeight)
        {
            camera.position.y = mapTop / 2;
        }
        else if(cameraBottom <= mapBottom)
        {
            camera.position.y = mapBottom + cameraHalfHeight;
        }
        else if(cameraTop >= mapTop)
        {
            camera.position.y = mapTop - cameraHalfHeight;
        }

    }

    private void createBuildingForLayer(TiledMapTileLayer tiledLayer) {
        boolean free;
        for (int x = 0; x < tiledLayer.getWidth(); x++) {
            for (int y = 0; y < tiledLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                if(cell!= null) {
                    if (cell.getTile().getId() == 219) free = false;
                    else free = true;
                    BuildingTile actor = new BuildingTile(tiledMap, tiledLayer, cell, free);
                    actor.setBounds(x * tiledLayer.getTileWidth(), y * tiledLayer.getTileHeight(), tiledLayer.getTileWidth(),
                            tiledLayer.getTileHeight());
                    actor.addListener(new BuildingTileListener(actor));
                    stageBuilding.addActor(actor);
                }
            }
        }
    }
    @Override
    public void resize(int width, int height) {
        viewportInGame.update(width,height);
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        camera.translate(-deltaX * currentZoom, deltaY * currentZoom);
        camera.update();
        updateBoundaries();
        return false;
    }
    @Override
    public boolean zoom(float initialDistance, float distance) {
        if((initialDistance / distance) * currentZoom < 1) {
            camera.zoom = MathUtils.clamp((initialDistance / distance) * currentZoom,0.5f,1f);
            //camera.zoom = (initialDistance / distance) * currentZoom;
            Gdx.app.log("INFO", camera.viewportWidth + " " + camera.viewportHeight+" "+ camera.zoom);
        }
        else
        {
            camera.zoom = 1;
        }
        camera.update();
        updateBoundaries();

        return true;
    }
    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        Gdx.app.log("INFO", "panStop");
        currentZoom = camera.zoom;

        return false;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }



    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        ntd.batch.dispose();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }



    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }


}
