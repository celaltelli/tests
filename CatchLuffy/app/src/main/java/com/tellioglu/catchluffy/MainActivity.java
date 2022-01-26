package com.tellioglu.catchluffy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public Button startButton;
    public TextView countDownTextView;
    public TextView scoreTextView;
    public GridView gridView ;
    Handler handler;
    Runnable imageViewRunnable;
    ChannelImageAdapter imageAdapter;
    public int count = 10;
    public int score = 0;
    public int currentIndex = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.startButton);
        countDownTextView = findViewById(R.id.countDownTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        gridView = findViewById(R.id.luffyGridView);
        imageAdapter = new ChannelImageAdapter(this);
        gridView.setAdapter(imageAdapter);
        gridView.setNumColumns(3);
        count = 10;
        score = 0;

    }
    public void start(View view)
    {
        startButton.setEnabled(false);
        count = 10;
        score = 0;
        scoreTextView.setText("Score : " + score);
        handler = new Handler();
        imageViewRunnable = new Runnable() {
            @Override
            public void run() {
                int randomNumber;
                do {
                     randomNumber = new Random().nextInt(8);
                }
                while(randomNumber == currentIndex);
                ImageView imageView = (ImageView) imageAdapter.getItem(randomNumber);
                if(currentIndex>=0) {
                    ImageView currentImageView = (ImageView) imageAdapter.getItem(currentIndex);
                    currentImageView.setVisibility(View.INVISIBLE);
                }
                imageView.setVisibility(View.VISIBLE);
                currentIndex = randomNumber;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        scoreTextView.setText("Score : " + ++score);

                    }
                });
                handler.postDelayed(this,400);

            }
        };
        handler.post(imageViewRunnable);
        new CountDownTimer(10000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                countDownTextView.setText("Count : " + count--);
            }

            @Override
            public void onFinish() {
                countDownTextView.setText("Finished!" );
                startButton.setEnabled(true);
                handler.removeCallbacks(imageViewRunnable);
                ImageView currentImageView = (ImageView) imageAdapter.getItem(currentIndex);
                currentImageView.setVisibility(View.INVISIBLE);

            }
        }.start();

    }
}

 class ChannelImageAdapter extends BaseAdapter {

    int mGalleryItemBackground;
    private Context mContext;
    public List<ImageView> images = new ArrayList<ImageView>();
    public ChannelImageAdapter(Context c) {
        mContext = c;

        for(int i = 0;i<9;i++){
            ImageView imageView = new ImageView(c.getApplicationContext());
            imageView.setBackgroundResource(R.drawable.luffy);
            imageView.setId(i);
            images.add(imageView);

        }

    }
    public int getCount() {
        return images.size();
    }
    public Object getItem(int position) {
        return images.get(position);
    }
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

         ImageView imageView;
        //Bitmap bm = BitmapFactory
        //      .decodeFile(images[position].getAbsolutePath());
        if (convertView == null) {
            imageView = images.get(position);
            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(5, 10, 5, 10);
            imageView.setVisibility(View.INVISIBLE);
        } else {
            imageView = (ImageView) convertView;
        }

        return imageView;

    }


}