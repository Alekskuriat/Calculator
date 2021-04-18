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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        presenter = new CalculatorPresenter(this, new Calculator());
        inicializationView();

    }

    private void inicializationView() {
        resultText = findViewById(R.id.viewResult);
        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);
        Button btn5 = findViewById(R.id.btn5);
        Button btn6 = findViewById(R.id.btn6);
        Button btn7 = findViewById(R.id.btn7);
        Button btn8 = findViewById(R.id.btn8);
        Button btn9 = findViewById(R.id.btn9);
        Button btnZero = findViewById(R.id.btnZero);
        Button btnDoubleZero = findViewById(R.id.btnDoubleZero);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSub = findViewById(R.id.btnSbt);
        Button btnMpt = findViewById(R.id.btnMtp);
        Button btnDiv = findViewById(R.id.btnDvn);
        Button btnReset = findViewById(R.id.btnReset);
        Button btnResult = findViewById(R.id.btnResult);
        Button btnBackSpace = findViewById(R.id.btnBackSpace);
        Button btnRoot = findViewById(R.id.btnRoot);
        Button btnPoint = findViewById(R.id.btnPoint);


        btn1.setOnClickListener(onClickListener);
        btn2.setOnClickListener(onClickListener);
        btn3.setOnClickListener(onClickListener);
        btn4.setOnClickListener(onClickListener);
        btn5.setOnClickListener(onClickListener);
        btn6.setOnClickListener(onClickListener);
        btn7.setOnClickListener(onClickListener);
        btn8.setOnClickListener(onClickListener);
        btn9.setOnClickListener(onClickListener);
        btnZero.setOnClickListener(onClickListener);
        btnDoubleZero.setOnClickListener(onClickListener);
        btnAdd.setOnClickListener(onClickListener);
        btnSub.setOnClickListener(onClickListener);
        btnMpt.setOnClickListener(onClickListener);
        btnDiv.setOnClickListener(onClickListener);
        btnReset.setOnClickListener(onClickListener);
        btnResult.setOnClickListener(onClickListener);
        btnBackSpace.setOnClickListener(onClickListener);
        btnRoot.setOnClickListener(onClickListener);
        btnPoint.setOnClickListener(onClickListener);

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

    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn1:
                    presenter.btnNumberClicked("1");
                    break;
                case R.id.btn2:
                    presenter.btnNumberClicked("2");
                    break;
                case R.id.btn3:
                    presenter.btnNumberClicked("3");
                    break;
                case R.id.btn4:
                    presenter.btnNumberClicked("4");
                    break;
                case R.id.btn5:
                    presenter.btnNumberClicked("5");
                    break;
                case R.id.btn6:
                    presenter.btnNumberClicked("6");
                    break;
                case R.id.btn7:
                    presenter.btnNumberClicked("7");
                    break;
                case R.id.btn8:
                    presenter.btnNumberClicked("8");
                    break;
                case R.id.btn9:
                    presenter.btnNumberClicked("9");
                    break;
                case R.id.btnZero:
                    presenter.btnZeroClicked();
                    break;
                case R.id.btnDoubleZero:
                    presenter.btnDoubleZeroClicked();
                    break;
                case R.id.btnReset:
                    presenter.btnReset();
                    break;
                case R.id.btnRoot:
                    presenter.btnRoot();
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
