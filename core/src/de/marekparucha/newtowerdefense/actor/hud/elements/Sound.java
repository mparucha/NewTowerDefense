package de.marekparucha.newtowerdefense.actor.hud.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.marekparucha.newtowerdefense.actor.hud.listeners.SoundListener;
import de.marekparucha.newtowerdefense.gameplay.controller.GameController;

/**
 * Created by Marek on 19.07.2017.
 */

public class Sound extends Actor {
    Texture textureOn = new Texture(Gdx.files.internal("hud/audioOn.png"));
    Texture textureOff = new Texture(Gdx.files.internal("hud/audioOff.png"));
    boolean soundOn = true;
    private GameController gameController;

    public Sound(GameController gameController){
        addListener(new SoundListener(this));
        this.gameController = gameController;
    }

    @Override
    public void draw(Batch batch, float alpha){
       if(soundOn) batch.draw(textureOff,this.getX(),getY());
        else batch.draw(textureOn,this.getX(),getY());
    }
    public void buttonPressed()
    {
        if(soundOn) soundOn = false;
        else soundOn = true;
    }

}

