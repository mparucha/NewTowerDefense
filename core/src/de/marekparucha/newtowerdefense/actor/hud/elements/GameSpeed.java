package de.marekparucha.newtowerdefense.actor.hud.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.marekparucha.newtowerdefense.actor.hud.listeners.GameSpeedListener;
import de.marekparucha.newtowerdefense.gameplay.controller.GameController;

/**
 * Created by Marek on 19.07.2017.
 */

public class GameSpeed extends Actor {

    Texture textureRewind = new Texture(Gdx.files.internal("hud/rewind.png"));
    Texture texturePause = new Texture(Gdx.files.internal("hud/pause.png"));
    Texture texturePlay = new Texture(Gdx.files.internal("hud/right.png"));
    Texture textureFoward = new Texture(Gdx.files.internal("hud/fastForward.png"));
    private int mode = 0;
    private boolean paused = false;
    private GameController gameController;
    private BitmapFont font;
    private static double modifier = 1;

    public GameSpeed(int mode, GameController gameController){
        this.mode = mode;
        addListener(new GameSpeedListener(this));
        this.gameController = gameController;
        final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/kenvector_future_thin.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color = Color.BLACK;
        //parameter.shadowColor = Color.BLACK;
        parameter.size = 64;
        font = generator.generateFont(parameter);
    }

    @Override
    public void draw(Batch batch, float alpha){
        if(mode == -1)
        {
            batch.draw(textureRewind,this.getX(),getY());
        }
        else if(mode == 1)
        {
            batch.draw(textureFoward,this.getX(),getY());
        }
        else
        {
            if(paused)
            {
                batch.draw(texturePlay,this.getX(),getY());
                font.draw(batch,"x0.0",this.getX()-375,getY()+70);
            }
            else
            {
                batch.draw(texturePause,this.getX(),getY());
                font.draw(batch,"x"+modifier,this.getX()-375,getY()+70);
            }

        }

    }

    public void buttonPressed()
    {
        if(mode == 1 && modifier<2) modifier= modifier*2;
        else if(mode == -1 && modifier>0.5) modifier= modifier/2;
        else if(mode == 0 && paused) paused = false;
        else if(mode == 0&& !paused) paused = true;
    }
}
