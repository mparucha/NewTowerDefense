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

    private Skin skin;
    private Stage stage;
    private NewTowerDefense ntd;

    public MainMenuScreen(NewTowerDefense ntd)
    {
     this.ntd = ntd;
    }

    @Override
    public void show() {
        ntd.batch = new SpriteBatch();
        skin = new Skin();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/kenvector_future_thin.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        skin.add("default-font",font12,BitmapFont.class);
        skin.addRegions(new TextureAtlas(Gdx.files.internal("ui/blue.atlas")));
        skin.load(Gdx.files.internal("ui/blue.json"));
        stage = new Stage();

        final TextButton button = new TextButton("Click me", skin, "default");

        button.setWidth(1000f);
        button.setHeight(200f);
        button.setPosition(Gdx.graphics.getWidth() /2 - 100f, Gdx.graphics.getHeight()/2 - 100f);
        button.getLabel().getName();
        Gdx.app.log("font",skin.getFont("default-font").getCapHeight()+" "+button.getLabel().getStyle().font);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                button.setText("You clicked the button");
            }
        });

        stage.addActor(button);

        Gdx.input.setInputProcessor(stage);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
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
