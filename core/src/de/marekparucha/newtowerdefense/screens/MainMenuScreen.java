package de.marekparucha.newtowerdefense.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.marekparucha.newtowerdefense.NewTowerDefense;

/**
 * Created by Marek on 18.07.2017.
 */

public class MainMenuScreen implements Screen {

    private Skin skinGreen;
    private Skin skinRed;
    private Stage stage;
    private NewTowerDefense ntd;

    public MainMenuScreen(NewTowerDefense ntd)
    {
     this.ntd = ntd;
    }

    @Override
    public void show() {
        ntd.batch = new SpriteBatch();
        skinGreen = new Skin();
        skinRed = new Skin();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/kenvector_future_thin.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        skinGreen.add("default-font",font12,BitmapFont.class);
        skinGreen.addRegions(new TextureAtlas(Gdx.files.internal("ui/green.atlas")));
        skinGreen.load(Gdx.files.internal("ui/green.json"));
        skinRed.add("default-font",font12,BitmapFont.class);
        skinRed.addRegions(new TextureAtlas(Gdx.files.internal("ui/red.atlas")));
        skinRed.load(Gdx.files.internal("ui/red.json"));
        stage = new Stage();

        final TextButton startButton = new TextButton("Start", skinGreen, "default");
        final TextButton exitButton = new TextButton("Exit", skinRed, "default");

        startButton.setWidth(1000f);
        startButton.setHeight(200f);
        startButton.setPosition(Gdx.graphics.getWidth() /2 - 500f, Gdx.graphics.getHeight()/2);
        exitButton.setWidth(1000f);
        exitButton.setHeight(200f);
        exitButton.setPosition(Gdx.graphics.getWidth() /2 - 500f, Gdx.graphics.getHeight()/2 - 300f);
        startButton.getLabel().getName();
        Gdx.app.log("font", skinGreen.getFont("default-font").getCapHeight()+" "+startButton.getLabel().getStyle().font);

        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                ntd.setScreen(new InGameScreen(ntd));

            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });

        stage.addActor(startButton);
        stage.addActor(exitButton);

        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ntd.batch.begin();
        stage.draw();
        ntd.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        ntd.batch.dispose();
    }
}
