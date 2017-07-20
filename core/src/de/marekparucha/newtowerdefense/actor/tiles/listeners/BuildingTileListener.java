package de.marekparucha.newtowerdefense.actor.tiles.listeners;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.marekparucha.newtowerdefense.actor.tiles.BuildingTile;

/**
 * Created by Marek on 19.07.2017.
 */

public class BuildingTileListener extends ClickListener {

    private BuildingTile actor;

    public BuildingTileListener(BuildingTile actor) {
        this.actor = actor;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        System.out.println(actor.isFree() + " : boolean if free");
    }
}
