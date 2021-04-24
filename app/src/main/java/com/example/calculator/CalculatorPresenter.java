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
    char lastSymbol;
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
        long a = System.nanoTime();
        if (!expression.toString().equals(""))
            if (lastSymbol != ZERO &&
                lastSymbol != SYMBOL_LEFT_PARENT &&
                lastSymbol != SYMBOL_RIGHT_PARENT) {
            display("0");
        }
        a = System.nanoTime() - a;
    }

    public void btnDoubleZeroClicked() {
        if (!(expression.toString().equals("0"))) display("00");
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
        if (c.equals("-") && view.getFirstEntry()) display(c);
        if (!(characterCheck() || view.getFirstEntry())) display(c);
    }

    public void btnBackSpace() {
        if (expression.length() > 1) {
            if (lastSymbol == SYMBOL_LEFT_PARENT) leftParent--;
            else if (lastSymbol == SYMBOL_RIGHT_PARENT) rightParent--;
            else count--;
            view.setResultText(expression.deleteCharAt(expression.length() - 1).toString());
        } else {
            view.setResultText("0");
            view.setFirstEntry();
            expression.delete(0, expression.length());
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
        for (int i = expression.length() - 1; i > 0; i--) {
            if (expression.charAt(i) != SYMBOL_LEFT_PARENT) {
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
        if (characterFind() || view.getFirstEntry()) {
            leftParent++;
            display("(");
        }
    }


    public void btnResult() {
        if (!(expression.toString().equals("")) && rightParent == leftParent && !characterCheck()) {
            exp = Calculator.calculate(expression.substring(0));
            if (exp.equals("Infinity")) {
                btnReset();
            } else {
                roundExp();
                view.setResultText(String.valueOf(expression) + '=' + exp);
                expression.delete(0, expression.length());
                expression.append(exp);
            }
        }
    }

    private void display(String s) {
        view.showResult(s);
        expression.append(s);
        lastSymbol = s.charAt(0);
    }


    private void roundExp() {
        int i;
        int counterZero = 0;
        for (i = 0; i < exp.length(); i++) {
            if (exp.charAt(i) == '.') break;
        }
        for (int j = i; j < exp.length(); j++) {
            if (exp.charAt(j) == '0') counterZero++;
            else counterZero = 0;
            if (counterZero > 3) exp = exp.substring(0, j - 3);
        }
    }

    private boolean characterFind() {
        return lastSymbol == SYMBOL_ADD ||
                lastSymbol == SYMBOL_SBT ||
                lastSymbol == SYMBOL_MPT ||
                lastSymbol == SYMBOL_DVN;
    }

    private boolean characterCheck() {
        return lastSymbol == SYMBOL_ADD ||
                lastSymbol == SYMBOL_SBT ||
                lastSymbol == SYMBOL_DVN ||
                lastSymbol == SYMBOL_MPT ||
                lastSymbol == SYMBOL_DOT;
    }

    private boolean findCharacter() {
        for (int i = expression.length() - 1; i > 0; i--) {
            if (expression.charAt(i) == SYMBOL_DOT) return false;
            if (expression.charAt(i) == SYMBOL_ADD ||
                    expression.charAt(i) == SYMBOL_MPT ||
                    expression.charAt(i) == SYMBOL_SBT ||
                    expression.charAt(i) == SYMBOL_DVN) return true;
        }
        return true;
    }
}
