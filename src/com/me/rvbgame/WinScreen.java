package com.me.rvbgame;

import android.widget.Button;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class WinScreen extends GameScreen{

    Label congratLabel;
    TextButton toMainMenuButton;
    TextButton toSPSettingsButton;

    public WinScreen(RvbGdxGame game, boolean bWin) {
        super(game);

        if (bWin){
            congratLabel = new Label("You win! ^_^",this.getSkin());
        }
        else
        {
            congratLabel = new Label("You loose! ==\"",this.getSkin());
        }
        congratLabel.setAlignment(Align.center, Align.center);

        toMainMenuButton = new TextButton("Return to Main Menu",this.getSkin());
        toMainMenuButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mGame.setScreen(new MainMenuScreen(mGame));
            };
        });

        toSPSettingsButton = new TextButton("Play Again", this.getSkin());
        toSPSettingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mGame.setScreen(new SPSettingsScreen(mGame));
            };
        });

        stage.addActor(congratLabel);
        stage.addActor(toMainMenuButton);
        stage.addActor(toSPSettingsButton);

        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        congratLabel.setBounds(0,height - 25,width,10);
        toMainMenuButton.setBounds(width/2-100,height / 2,200,25);
        toSPSettingsButton.setBounds(width/2-100,height / 2 - 30,200,25);


    }
}
