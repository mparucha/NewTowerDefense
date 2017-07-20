package de.marekparucha.newtowerdefense.actor.hud.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.marekparucha.newtowerdefense.actor.hud.elements.Sound;

/**
 * Created by Marek on 19.07.2017.
 */

public class SoundListener extends ClickListener{

    private Sound actor;

    public SoundListener(Sound actor) {
        this.actor = actor;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        actor.buttonPressed();
    }
}
