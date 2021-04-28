package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculatorActivity extends AppCompatActivity {

    private TextView resultText;
    private boolean firstEntry = true;
    private CalculatorPresenter presenter;
    private final int[] numberButtonIds = new int[]{R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,
            R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        inicializationView();
        setNumberButtonListeners();
        presenter = new CalculatorPresenter(this, new Calculator());
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        resultText.setText(savedInstanceState.getString("textView"));
        presenter.count = savedInstanceState.getInt("count");
        presenter.rightParent = savedInstanceState.getInt("rightParent");
        presenter.leftParent = savedInstanceState.getInt("leftParent");
        presenter.expression.delete(0, presenter.expression.length());
        presenter.expression.append(savedInstanceState.getString("expression"));
        firstEntry = savedInstanceState.getBoolean("entry");
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("textView", String.valueOf(presenter.expression));
        outState.putInt("count", presenter.count);
        outState.putInt("rightParent", presenter.rightParent);
        outState.putInt("leftParent", presenter.leftParent);
        outState.putString("expression", String.valueOf(presenter.expression));
        outState.putBoolean("entry", firstEntry);
        super.onSaveInstanceState(outState);
    }

    private void inicializationView() {
        resultText = findViewById(R.id.viewResult);
        Button btnZero = findViewById(R.id.btnZero);
        Button btnDoubleZero = findViewById(R.id.btnDoubleZero);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSub = findViewById(R.id.btnSbt);
        Button btnMpt = findViewById(R.id.btnMtp);
        Button btnDiv = findViewById(R.id.btnDvn);
        Button btnReset = findViewById(R.id.btnReset);
        Button btnResult = findViewById(R.id.btnResult);
        Button btnBackSpace = findViewById(R.id.btnBackSpace);
        Button btnPoint = findViewById(R.id.btnPoint);
        Button btnRightParent = findViewById(R.id.btnRightParent);
        Button btnLeftParent = findViewById(R.id.btnLeftParent);


        btnZero.setOnClickListener(onClickListener);
        btnDoubleZero.setOnClickListener(onClickListener);
        btnAdd.setOnClickListener(onClickListener);
        btnSub.setOnClickListener(onClickListener);
        btnMpt.setOnClickListener(onClickListener);
        btnDiv.setOnClickListener(onClickListener);
        btnReset.setOnClickListener(onClickListener);
        btnResult.setOnClickListener(onClickListener);
        btnBackSpace.setOnClickListener(onClickListener);
        btnPoint.setOnClickListener(onClickListener);
        btnRightParent.setOnClickListener(onClickListener);
        btnLeftParent.setOnClickListener(onClickListener);


    }

    public void showResult(String result) {
        valueInput(result);
    }

    public TextView getTextView() {
        return resultText;
    }

    public boolean getFirstEntry() {
        return firstEntry;
    }

    public void setFirstEntry() {
        firstEntry = true;
    }

    public void setResultText(String s) {
        resultText.setText(s);
    }

    private void setNumberButtonListeners() {
        for (int i = 0; i < numberButtonIds.length; i++) {
            int index = i + 1;
            findViewById(numberButtonIds[i]).setOnClickListener(v -> presenter.btnNumberClicked(String.valueOf(index)));
        }
    }

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnZero:
                    presenter.btnZeroClicked();
                    break;
                case R.id.btnDoubleZero:
                    presenter.btnDoubleZeroClicked();
                    break;
                case R.id.btnReset:
                    presenter.btnReset();
                    break;
                case R.id.btnAdd:
                    presenter.btnCharacter("+");
                    break;
                case R.id.btnSbt:
                    presenter.btnCharacter("-");
                    break;
                case R.id.btnMtp:
                    presenter.btnCharacter("*");
                    break;
                case R.id.btnDvn:
                    presenter.btnCharacter("/");
                    break;
                case R.id.btnBackSpace:
                    presenter.btnBackSpace();
                    break;
                case R.id.btnPoint:
                    presenter.btnPoint();
                    break;
                case R.id.btnRightParent:
                    presenter.btnRightParent();
                    break;
                case R.id.btnLeftParent:
                    presenter.btnLeftParent();
                    break;
                case R.id.btnResult:
                    presenter.btnResult();
            }

        }
    };

    private void valueInput(String s) {
        if (firstEntry) {
            resultText.setText(s);
            firstEntry = false;
        } else resultText.append(s);
    }

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

