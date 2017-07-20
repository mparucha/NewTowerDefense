package de.marekparucha.newtowerdefense.actor.hud.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Marek on 19.07.2017.
 */

public class Health extends Actor {
    Texture texture = new Texture(Gdx.files.internal("hud/hearts.png"));

    int health = 0;
    BitmapFont font;

    public Health()
    {
        final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/kenvector_future_thin.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 5;
        parameter.shadowOffsetY = 5;
        parameter.size = 48;
        font = generator.generateFont(parameter);
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,this.getX(),getY());
        font.draw(batch,health+"",getX()+100,getY()+50);
    }

    public void update(int health)
    {
        this.health = health;
    }

}
