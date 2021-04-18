package com.example.calculator;

public class CalculatorPresenter{
    private final CalculatorActivity view;
    private Calculator calculator;
    private final char SYMBOL_ADD = 0x002B;
    private final char SYMBOL_SBT = 0x002D;
    private final char SYMBOL_DVN = 0x002F;
    private final char SYMBOL_MPT = 0x002A;
    private final char SYMBOL_DOT = 0x002E;
    private final char ZERO = '0';
    StringBuffer expression = new StringBuffer("");
    String exp;

    public CalculatorPresenter(CalculatorActivity view, Calculator calculator) {
        this.view = view;
        this.calculator = calculator;
    }

    public void btnNumberClicked(String number) {
        view.showResult(number);
        expression.append(number);
    }

    public void btnZeroClicked() {
        if (view.getTextView().getText().toString().charAt(view.getTextView().length() - 1) != ZERO) {
                view.showResult("0");
                expression.append("0");
        }
    }


    public void btnDoubleZeroClicked() {
        if (!(view.getTextView().getText().toString().equals("0"))) {
            view.showResult("00");
            expression.append("00");
        }
    }

    public void btnReset() {
        view.setResultText("0");
        view.setFirstEntry();
        expression.delete(0, expression.length() );
    }

    public void btnRoot() {
        if (!(view.getTextView().getText().toString().charAt(0) == 0x221A)) {
            view.setResultText("√" + view.getTextView().getText().toString());
            expression.indexOf("√", 0);
        }
    }

    public void btnCharacter(String c) {
        if (!(characterCheck() || view.getFirstEntry())) {
            view.showResult(c);
            expression.append(c);
        }
    }

    public void btnBackSpace() {
        if (view.getTextView().length() > 1) {
            view.setResultText(view.getTextView().getText().toString().substring(0, view.getTextView().length() - 1));
            expression.deleteCharAt(expression.length() - 1);
        } else {
            view.setResultText("0");
            view.setFirstEntry();
            expression.delete(0, expression.length() - 1);
        }
    }

    public void btnPoint() {
        if (view.getFirstEntry()) {
            view.showResult("0.");
            expression.append("0.");
        } else {
            if (!characterCheck())
                if (findCharacter()) {
                    view.showResult(".");
                    expression.append(".");
                }
        }
    }

    public void btnResult() {
        exp = Calculator.calculate(expression.substring(0));
        view.setResultText(exp);
        expression.delete(0, expression.length());
        expression.append(exp);

    }


    private boolean characterCheck() {
        return view.getTextView().getText().toString().charAt(view.getTextView().length() - 1) == SYMBOL_ADD
                || view.getTextView().getText().toString().charAt(view.getTextView().length() - 1) == SYMBOL_SBT
                || view.getTextView().getText().toString().charAt(view.getTextView().length() - 1) == SYMBOL_MPT
                || view.getTextView().getText().toString().charAt(view.getTextView().length() - 1) == SYMBOL_DVN
                || view.getTextView().getText().toString().charAt(view.getTextView().length() - 1) == SYMBOL_DOT;
    }

    private boolean findCharacter() {
        for (int i = view.getTextView().length() - 1; i > 0; i--) {
            if (view.getTextView().getText().toString().charAt(i) == SYMBOL_DOT) return false;
            if (view.getTextView().getText().toString().charAt(i) == SYMBOL_SBT
                    || view.getTextView().getText().toString().charAt(i) == SYMBOL_MPT
                    || view.getTextView().getText().toString().charAt(i) == SYMBOL_DVN
                    || view.getTextView().getText().toString().charAt(i) == SYMBOL_ADD) {
                return true;
            }
        }
        return true;
    }

}
