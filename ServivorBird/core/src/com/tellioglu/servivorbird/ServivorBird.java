package com.tellioglu.servivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class ServivorBird extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;
    Texture bird;
    Texture bee1;
    Texture bee2;
    Texture bee3;
    float birdX = 0;
    float birdY = 0;
    int gameState = 0;
    float velocity = 1;
    float enemyVelocity = 4;
    float gravity = 0.8f;
    Random random;
    int score = 0;
    int scoredEnemy = 0;

    Circle birdCircle;

    int numberOfBeeSet = 4;
    float[] enemyX = new float[numberOfBeeSet];
    float[] enemyOffset = new float[numberOfBeeSet];
    float[] enemyOffset2 = new float[numberOfBeeSet];
    float[] enemyOffset3 = new float[numberOfBeeSet];
    float distance = 0;

    Circle[] eneyCircle1;
    Circle[] eneyCircle2;
    Circle[] eneyCircle3;

    BitmapFont font;
    BitmapFont font2;


    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("Full-Background.png");
        bird = new Texture("frame-1.png");
        bee1 = new Texture("sprite1.png");
        bee2 = new Texture("sprite1.png");
        bee3 = new Texture("sprite1.png");
        birdX = Gdx.graphics.getWidth() / 4;
        birdY = Gdx.graphics.getHeight() / 2;
        birdCircle = new Circle();

        distance = Gdx.graphics.getWidth() / 2;
        random = new Random();

        eneyCircle1 = new Circle[numberOfBeeSet];
        eneyCircle2 = new Circle[numberOfBeeSet];
        eneyCircle3 = new Circle[numberOfBeeSet];

        for (int i = 0; i < numberOfBeeSet; i++) {

            enemyOffset[i] = (random.nextFloat() * Gdx.graphics.getHeight());
            enemyOffset2[i] = (random.nextFloat() * Gdx.graphics.getHeight());
            enemyOffset3[i] = (random.nextFloat() * Gdx.graphics.getHeight());

            enemyX[i] = Gdx.graphics.getWidth() - bee1.getWidth() / 2 + i * distance;

            eneyCircle1[i] = new Circle();
            eneyCircle2[i] = new Circle();
            eneyCircle3[i] = new Circle();
        }

        font = new BitmapFont();
        font.setColor(Color.RED);
        font.getData().setScale(6);
        font2 = new BitmapFont();
        font2.setColor(Color.ORANGE);
        font2.getData().setScale(8);


    }

    public float getRandom(float offset,float offset2) {
        float rand = 0;
        Random random = new Random();
        while (rand + Gdx.graphics.getHeight() / 10 >= Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10 || rand <= 0
                || offset != 0 && !(rand >offset +( Gdx.graphics.getHeight() / 10)  || rand  < offset -( Gdx.graphics.getHeight() / 10) )
                || offset2 != 0 && !(rand >offset2 +( Gdx.graphics.getHeight() / 10)  || rand  < offset2 -( Gdx.graphics.getHeight() / 10) ))
        {
            rand = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() - 200;
        }
        return rand;

    }

    ;

    @Override
    public void render() {
        ScreenUtils.clear(1, 0, 0, 1);

        batch.begin();
        batch.draw(background, 0, 0);
        if (gameState == 1) {


            if (enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 4) {
                score++;

                if (scoredEnemy < numberOfBeeSet - 1) {
                    scoredEnemy++;
                } else {
                    scoredEnemy = 0;
                }
            }

            if (Gdx.input.justTouched()) {
                float neVelocity =  (float) -(Gdx.graphics.getHeight() * 0.015);
                if(birdY - neVelocity+gravity < Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10)
                velocity = (float) -(Gdx.graphics.getHeight() * 0.015);
            }
            for (int i = 0; i < numberOfBeeSet; i++) {

                if (enemyX[i] < -Gdx.graphics.getWidth() / 15) {
                    enemyX[i] += numberOfBeeSet * distance;

                    enemyOffset[i] = getRandom(0,0);
                    enemyOffset2[i] = getRandom(enemyOffset[i] ,0);
                    enemyOffset3[i] = getRandom(enemyOffset2[i],enemyOffset3[i]);
                } else
                    enemyX[i] -= enemyVelocity;
                batch.draw(bee1, enemyX[i], enemyOffset[i], Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 10);
                batch.draw(bee2, enemyX[i], enemyOffset2[i], Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 10);
                batch.draw(bee3, enemyX[i], enemyOffset3[i], Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 10);

                eneyCircle1[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 40, enemyOffset[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 40);
                eneyCircle2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 40, enemyOffset2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 40);
                eneyCircle3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 40, enemyOffset3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 40);

                if (Intersector.overlaps(birdCircle, eneyCircle1[i]) || Intersector.overlaps(birdCircle, eneyCircle2[i]) || Intersector.overlaps(birdCircle, eneyCircle3[i])) {
                    gameState = 2;
                }
            }


            if (birdY > 0) {
                if (birdY < Gdx.graphics.getWidth()) {
                    velocity += gravity;
                    birdY = birdY - velocity;


                    if (birdY > Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10)
                        birdY = Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 10;

                }

            } else
                gameState = 2;

        } else if (gameState == 0) {
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }

        } else if (gameState == 2) {

            int currentScore = score;
            font2.draw(batch, "Game Over! Tab to play again!", 200, Gdx.graphics.getHeight() / 2);
            if(currentScore!= 0)
            font.draw(batch, "Score: "+ currentScore, Gdx.graphics.getWidth()/2-300, Gdx.graphics.getHeight() / 2 -100 );
            if (Gdx.input.justTouched()) {
                score = 0;
                gameState = 1;
            }

            birdY = Gdx.graphics.getHeight() / 2;

            for (int i = 0; i < numberOfBeeSet; i++) {

                enemyOffset[i] = (random.nextFloat() * Gdx.graphics.getHeight());
                enemyOffset2[i] = (random.nextFloat() * Gdx.graphics.getHeight());
                enemyOffset3[i] = (random.nextFloat() * Gdx.graphics.getHeight());

                enemyX[i] = (Gdx.graphics.getWidth() - (bee1.getWidth() / 2)) + (i * distance);

                eneyCircle1[i] = new Circle();
                eneyCircle2[i] = new Circle();
                eneyCircle3[i] = new Circle();
            }

            velocity = 0;
            scoredEnemy = 0;
           // score = 0;

        }

        batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 20, Gdx.graphics.getHeight() / 10);

        font.draw(batch, String.valueOf(score), 100, 200);
        batch.end();
        birdCircle.set(birdX + Gdx.graphics.getWidth() / 40, birdY + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 40);

    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        gameState = 2;
    }
}
