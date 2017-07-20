package de.marekparucha.newtowerdefense.gameplay.controller;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import de.marekparucha.newtowerdefense.NewTowerDefense;
import de.marekparucha.newtowerdefense.actor.enemies.Movement;
import de.marekparucha.newtowerdefense.actor.hud.PlayerHUD;
import de.marekparucha.newtowerdefense.gameplay.Level;

/**
 * Created by Marek on 19.07.2017.
 */

public class GameController {

    PlayerHUD playerHUD;
    Level level;
    NewTowerDefense ntd;
    Movement movement;
    TiledMap tiledMap;

    public GameController(NewTowerDefense ntd)
    {
        this.playerHUD = new PlayerHUD(this,ntd.batch);
        this.ntd = ntd;
        movement = new Movement();
    }


    public PlayerHUD getPlayerHUD() {
        return playerHUD;
    }

    public void setLevelScreen(int level)
    {
        this.tiledMap = new TmxMapLoader().load("maps/map1.tmx");
        this.level = new Level(ntd, level,playerHUD,tiledMap);
        ntd.setScreen(this.level);
    }

    public void start()
    {
        this.tiledMap = new TmxMapLoader().load("maps/map1AbzweigungEnde.tmx");
        movement.setMovements((TiledMapTileLayer)tiledMap.getLayers().get("Kachelebene 2"));
        level = new Level(ntd, 1,playerHUD,tiledMap);
        ntd.setScreen(level);
    }
}
