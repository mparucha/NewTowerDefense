package de.marekparucha.newtowerdefense.screens;

import com.badlogic.gdx.Screen;

import de.marekparucha.newtowerdefense.NewTowerDefense;
import de.marekparucha.newtowerdefense.gameplay.Level;

/**
 * Created by Marek on 18.07.2017.
 */

public class InGameScreen implements Screen {

    private NewTowerDefense ntd;

    public InGameScreen(NewTowerDefense ntd)
    {
        this.ntd = ntd;
    }

    @Override
    public void show() {

        ntd.setScreen(new Level(ntd));
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        ntd.batch.dispose();
    }
}
