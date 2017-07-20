package de.marekparucha.newtowerdefense.actor.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

import de.marekparucha.newtowerdefense.actor.hud.elements.Background;
import de.marekparucha.newtowerdefense.actor.hud.elements.Coins;
import de.marekparucha.newtowerdefense.actor.hud.elements.Exit;
import de.marekparucha.newtowerdefense.actor.hud.elements.GameSpeed;
import de.marekparucha.newtowerdefense.actor.hud.elements.Health;
import de.marekparucha.newtowerdefense.actor.hud.elements.Sound;
import de.marekparucha.newtowerdefense.gameplay.controller.GameController;

/**
 * Created by Marek on 19.07.2017.
 */

public class PlayerHUD extends Stage {

    private Sound sound;
    private GameSpeed slower;
    private GameSpeed pause_play;
    private GameSpeed faster;
    private Health health;
    private Exit exit;
    private Coins coins;
    private Background background;

    private GameController gameController;


    public PlayerHUD(GameController gameController, Batch b)
    {
        super(new FillViewport(1080,1920),b);
        this.gameController = gameController;
        background = new Background();
        sound = new Sound(gameController);
        slower = new GameSpeed(-1, gameController);
        pause_play = new GameSpeed(0, gameController);
        faster = new GameSpeed(1, gameController);
        health = new Health();
        exit = new Exit(gameController);
        coins = new Coins();
        addAllHUDElements();
        setPositions();
    }

    private void  addAllHUDElements()
    {
        this.addActor(background);
        this.addActor(sound);
        this.addActor(slower);
        this.addActor(pause_play);
        this.addActor(faster);
        this.addActor(health);
        this.addActor(exit);
        this.addActor(coins);
    }


    public void updateCoins(int coins)
    {
        this.coins.update(coins);
    }

    public void updateHealth(int health)
    {
        this.health.update(health);
    }

    public void setPositions()
    {
        background.setBounds(0,this.getHeight()-243,1080,150);
        exit.setBounds(50,this.getHeight()-210,200,200);
        sound.setBounds(200, this.getHeight()-210,200,200);
        slower.setBounds(600,this.getHeight()-210,200,200);
        pause_play.setBounds(750,this.getHeight()-210,200,200);
        faster.setBounds(900,this.getHeight()-210,200,200);
        health.setBounds(30,this.getHeight()-350,150,150);
        coins.setBounds(30,this.getHeight()-450,150,150);
    }


}
