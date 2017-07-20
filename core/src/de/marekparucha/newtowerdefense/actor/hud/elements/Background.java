package de.marekparucha.newtowerdefense.actor.hud.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Marek on 20.07.2017.
 */

public class Background extends Actor {

    private Texture texture = new Texture(Gdx.files.internal("hud/blue_button13.png"));
    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,getX(),getY(),1080,150);
    }
}
