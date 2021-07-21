package com.example.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Splash  extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash);

        new android.os.Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent i ;
                i = new Intent(Splash.this, MainActivity.class);
                startActivity(i);
                finish();


            }
        },2000);
//        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.anim);
//        logo.startAnimation(myanim);
    }
    }
