package de.marekparucha.newtowerdefense.gameplay.controller;

import com.badlogic.gdx.Screen;

import de.marekparucha.newtowerdefense.NewTowerDefense;
import de.marekparucha.newtowerdefense.actor.hud.PlayerHUD;
import de.marekparucha.newtowerdefense.gameplay.Level;

/**
 * Created by Marek on 19.07.2017.
 */

public class GameController {

    PlayerHUD playerHUD;
    Level level;
    NewTowerDefense ntd;

    public GameController(NewTowerDefense ntd)
    {
        this.playerHUD = new PlayerHUD(this,ntd.batch);
        this.ntd = ntd;
    }


    public PlayerHUD getPlayerHUD() {
        return playerHUD;
    }

    public void setLevelScreen(int level)
    {
        ntd.setScreen(new Level(ntd,level,playerHUD));
    }

    public void start()
    {
        ntd.setScreen(new Level(ntd, 1,playerHUD));
    }
}
