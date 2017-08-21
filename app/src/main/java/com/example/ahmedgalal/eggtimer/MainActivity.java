package com.example.ahmedgalal.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerText;
    Button controllerButton;
    CountDownTimer countDownTimet;
    Boolean counterActive = false;

    public void updateTimer (int secondsLeft) {
        //علشان أخلي قيمة الProgress إللي بالثواني تتحول لدقايق وثواني في الTextView
        int minutes = (int) secondsLeft/60;
        int seconds = secondsLeft - minutes * 60;

        String secString = Integer.toString(seconds);
        if (seconds <= 9) {
            secString = "0" + secString;
        }

        timerText.setText(Integer.toString(minutes) + ":" + secString);
    }

    public  void resetTimer () {
        timerText.setText("0:30");
        timerSeekBar.setProgress(30);
        countDownTimet.cancel();
        controllerButton.setText("Go");
        timerSeekBar.setEnabled(true);
        counterActive = false;
    }

    public void onControl (View view) {
        if (counterActive == false) {
            counterActive = true;
            timerSeekBar.setEnabled(false);
            controllerButton.setText("Stop");

            //التايمر شغال بالملي ثانية، فأنا بجيب قيمة الProgress إللي هبدأ بيها التايمر واضربها في 1000
            countDownTimet = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mPlayer.start();
                }
            }.start();
       } else {
            resetTimer();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = (TextView) findViewById(R.id.timerTextView);
        controllerButton = (Button)findViewById(R.id.controllerButton);
        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);
        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
