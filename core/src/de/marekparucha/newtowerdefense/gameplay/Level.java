package de.marekparucha.newtowerdefense.gameplay;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import de.marekparucha.newtowerdefense.NewTowerDefense;

/**
 * Created by Marek on 18.07.2017.
 */

public class Level implements InputProcessor, Screen, GestureDetector.GestureListener {

    Texture img;
    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    SpriteBatch sb;
    Texture texture;
    Sprite sprite;
    private Viewport viewport;
    private NewTowerDefense ntd;
    private float currentZoom;

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

    public Level(NewTowerDefense ntd)
    {
        this.ntd = ntd;
    }
    @Override
    public void show() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();


        camera = new OrthographicCamera(w,h);
        viewport = new FillViewport(1024,2048,camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        Gdx.app.log("camera", camera.position.toString());
        tiledMap = new TmxMapLoader().load("maps/map1.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        Gdx.input.setInputProcessor(new GestureDetector(this));
        currentZoom = camera.zoom;

      /*  sb = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("pik.png"));
        sprite = new Sprite(texture);
        */
    }

    @Override
    public void render(float delta) {
        camera.update();
        ntd.batch.setProjectionMatrix(camera.combined);
        /*updateBoundaries();
        adaptBoundaries();*/
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ntd.batch.begin();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        ntd.batch.end();
       /* sb.begin();
        sprite.draw(sb);
        sb.end();*/
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
    public boolean pan(float x, float y, float deltaX, float deltaY) {
    camera.translate(-deltaX * currentZoom, deltaY * currentZoom);
    camera.update();
        updateBoundaries();
        adaptBoundaries();
    Gdx.app.log("INFO", camera.position.x+" "+camera.position.y+" "+x+" "+y+" "+ currentZoom);
        return false;
    }
    @Override
    public boolean zoom(float initialDistance, float distance) {

        //Gdx.app.log("INFO", "Zoom performed");
        //camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100/camera.viewportWidth);
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
        adaptBoundaries();

        return true;
    }
    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        Gdx.app.log("INFO", "panStop");
        currentZoom = camera.zoom;

        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    private void updateBoundaries()
    {
// The right boundary of the map (x + width)
        mapRight = 960;
// The top boundary of the map (y + height)
        mapTop = 1920;
// The camera dimensions, halved
        cameraHalfWidth = camera.viewportWidth * .5f*camera.zoom;
        cameraHalfHeight = (camera.viewportHeight-210) * .5f*camera.zoom;

// Move camera after player as normal

        cameraLeft = camera.position.x - cameraHalfWidth;
        cameraRight = camera.position.x + cameraHalfWidth;
        cameraBottom = camera.position.y - cameraHalfHeight;
        cameraTop = camera.position.y + cameraHalfHeight;
    }

    private void adaptBoundaries()
    {

        // Horizontal axis
        if( 960 < camera.viewportWidth)
        {
            camera.position.x = mapRight / 2;
            Gdx.app.log("Zoom",(float) (960*camera.zoom)+" "+camera.viewportWidth);
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
        if(1920 < camera.viewportHeight)
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
}
