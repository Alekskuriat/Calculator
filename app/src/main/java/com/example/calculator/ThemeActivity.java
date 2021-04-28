package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

public class ThemeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (CalculatorActivity.themeNumber == 1)
            setTheme(R.style.Theme_Calculator);
        else if (CalculatorActivity.themeNumber == 2)
            setTheme(R.style.Theme_Calculator2);
        else
            setTheme(R.style.Theme_Calculator3);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);

        //getSupportActionBar().hide();
        inicializationView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("theme", CalculatorActivity.themeNumber);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        CalculatorActivity.themeNumber = savedInstanceState.getInt("theme");
    }

    private void inicializationView() {
        Button btnTheme1 = findViewById(R.id.btnTheme1);
        Button btnTheme2 = findViewById(R.id.btnTheme2);
        Button btnTheme3 = findViewById(R.id.btnTheme3);
        Button btnBack = findViewById(R.id.btnBack);

        btnTheme1.setOnClickListener(onClickListener);
        btnTheme2.setOnClickListener(onClickListener);
        btnTheme3.setOnClickListener(onClickListener);
        btnBack.setOnClickListener(onClickListener);
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnTheme1:
                    CalculatorActivity.themeNumber = 1;
                    break;
                case R.id.btnTheme2:
                    CalculatorActivity.themeNumber = 2;
                    break;
                case R.id.btnTheme3:
                    CalculatorActivity.themeNumber = 3;
                    break;
                case R.id.btnBack:
                    Intent intent = new Intent(ThemeActivity.this, CalculatorActivity.class);
                    startActivity(intent);

            }
            recreate();

        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );
    }
}