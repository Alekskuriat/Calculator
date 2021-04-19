package com.example.calculator;


public class CalculatorPresenter {
    public CalculatorActivity view;
    private Calculator calculator;
    private final char SYMBOL_ADD = 0x002B;
    private final char SYMBOL_SBT = 0x002D;
    private final char SYMBOL_DVN = 0x002F;
    private final char SYMBOL_MPT = 0x002A;
    private final char SYMBOL_DOT = 0x002E;
    private final char SYMBOL_RIGHT_PARENT = 0x0029;
    private final char SYMBOL_LEFT_PARENT = 0x0028;
    private final char ZERO = '0';
    int rightParent = 0;
    int leftParent = 0;
    int count = 0;
    StringBuffer expression = new StringBuffer("");
    String exp;

    public CalculatorPresenter(CalculatorActivity view, Calculator calculator) {
        this.view = view;
        this.calculator = calculator;

    }

    public void btnNumberClicked(String number) {
        display(number);
    }

    public void btnZeroClicked() {
        if (view.getTextView()
                .getText()
                .toString()
                .charAt(view.getTextView().length() - 1) != ZERO) display("0");
    }

    public void btnDoubleZeroClicked() {
        if (!(view.getTextView()
                .getText()
                .toString()
                .equals("0"))) {
            display("00");
        }
    }

    public void btnReset() {
        view.setResultText("0");
        view.setFirstEntry();
        expression.delete(0, expression.length());
        rightParent = 0;
        leftParent = 0;
        count = 0;
    }

    public void btnCharacter(String c) {
        if (!(characterCheck() || view.getFirstEntry())) display(c);
    }

    public void btnBackSpace() {
        char viewLastElement = view.getTextView().getText().toString().charAt(view.getTextView().length() - 1);
        if (view.getTextView().length() > 1) {
            if (viewLastElement == SYMBOL_LEFT_PARENT) leftParent--;
            else if (viewLastElement == SYMBOL_RIGHT_PARENT) rightParent--;
            else count--;
            view.setResultText(view.getTextView()
                    .getText()
                    .toString()
                    .substring(0, view.getTextView().length() - 1));
            expression.deleteCharAt(expression.length() - 1);
        } else {
            view.setResultText("0");
            view.setFirstEntry();
            expression.delete(0, expression.length() - 1);
        }
    }

    public void btnPoint() {
        if (view.getFirstEntry()) display("0.");
        else {
            if (!characterCheck())
                if (findCharacter()) display(".");
        }
    }

    public void btnRightParent() {
        count = 0;
        for (int i = view.getTextView().length() - 1; i > 0; i--) {
            if (view.getTextView().getText().charAt(i) != SYMBOL_LEFT_PARENT) {
                count++;
                if (count == 3) break;
            } else break;
        }
        if ((rightParent != leftParent) && count >= 3) {
            rightParent++;
            display(")");
        }
    }

    public void btnLeftParent() {
        if (characterFind()) {
            leftParent++;
            display("(");
        }
    }


    public void btnResult() {
        if (rightParent == leftParent) {
            exp = Calculator.calculate(expression.substring(0));
            roundExp();
            view.setResultText(exp);
            expression.delete(0, expression.length());
            expression.append(exp);
        }


    }

    private void display(String s) {
        view.showResult(s);
        expression.append(s);
    }


    private void roundExp() {
        int i;
        int counterZero = 0;
        for (i = 0; i < exp.length()  ; i++) {
            if (exp.charAt(i) == '.') break;
        }
        for (int j = i; j < exp.length(); j++) {
            if (exp.charAt(j) == '0') counterZero++;
            else counterZero = 0;
            if (counterZero > 3) exp = exp.substring(0, j - 3 );
        }
    }

    private boolean characterFind() {
        return view.getTextView().getText().toString().charAt(view.getTextView().length() - 1) == SYMBOL_ADD
                || view.getTextView().getText().toString().charAt(view.getTextView().length() - 1) == SYMBOL_SBT
                || view.getTextView().getText().toString().charAt(view.getTextView().length() - 1) == SYMBOL_MPT
                || view.getTextView().getText().toString().charAt(view.getTextView().length() - 1) == SYMBOL_DVN;
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
