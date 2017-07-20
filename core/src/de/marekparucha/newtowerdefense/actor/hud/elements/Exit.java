package de.marekparucha.newtowerdefense.actor.hud.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.marekparucha.newtowerdefense.actor.hud.listeners.ExitListener;
import de.marekparucha.newtowerdefense.gameplay.controller.GameController;

/**
 * Created by Marek on 19.07.2017.
 */

public class Exit extends Actor {

    Texture texture = new Texture(Gdx.files.internal("hud/exit.png"));
    GameController gameController;
    public Exit(GameController gameController)
    {
        addListener(new ExitListener(this));
        this.gameController = gameController;
    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,this.getX(),getY(),100,100);
    }

    public void buttonPressed()
    {
        Gdx.app.log("exit", "pressed");

    }
}
