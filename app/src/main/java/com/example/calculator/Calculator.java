package com.example.calculator;


import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;

public class Calculator {


    @SuppressLint("DefaultLocale")
    public static String calculate(String s) {
        List<Lexeme> lexemes = lexAnalyze(s);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        return String.format("%.3g", expr(lexemeBuffer));
    }

    public enum LexemeType {
        OP_PLUS, OP_MINUS, OP_MUL, OP_DIV,
        LEFT_PARENT, RIGHT_PARENT,
        NUMBER,
        EOF;

    }

    public static class Lexeme {

        LexemeType type;
        String value;

        public Lexeme(LexemeType type, String value) {
            this.type = type;
            this.value = value;
        }

        public Lexeme(LexemeType type, Character value) {
            this.type = type;
            this.value = value.toString();
        }

    }

    public static class LexemeBuffer {

        private int pos;
        public List<Lexeme> lexemes;

        public LexemeBuffer(List<Lexeme> lexemes) {
            this.lexemes = lexemes;
        }

        public Lexeme next() {
            return lexemes.get(pos++);
        }

        public void back() {
            pos--;
        }


    }

    public static List<Lexeme> lexAnalyze(String expText) {
        ArrayList<Lexeme> lexemes = new ArrayList<>();
        int pos = 0;
        while (pos < expText.length()) {
            char c = expText.charAt(pos);
            switch (c) {
                case '(':
                    lexemes.add(new Lexeme(LexemeType.LEFT_PARENT, c));
                    pos++;
                    continue;
                case ')':
                    lexemes.add(new Lexeme(LexemeType.RIGHT_PARENT, c));
                    pos++;
                    continue;
                case '+':
                    lexemes.add(new Lexeme(LexemeType.OP_PLUS, c));
                    pos++;
                    continue;
                case '-':
                    lexemes.add(new Lexeme(LexemeType.OP_MINUS, c));
                    pos++;
                    continue;
                case '*':
                    lexemes.add(new Lexeme(LexemeType.OP_MUL, c));
                    pos++;
                    continue;
                case '/':
                    lexemes.add(new Lexeme(LexemeType.OP_DIV, c));
                    pos++;
                    continue;
                default:
                    if (c <= '9' && c >= '0' || c == '.') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            pos++;
                            if (pos >= expText.length()) {
                                break;
                            }
                            c = expText.charAt(pos);
                        } while (c <= '9' && c >= '0' || c == '.');
                        lexemes.add(new Lexeme(LexemeType.NUMBER, sb.toString()));
                    } else {
                        if (c != ' ') {
                            throw new RuntimeException("Unexpected character: " + c);
                        }
                        pos++;
                    }
            }
        }
        lexemes.add(new Lexeme(LexemeType.EOF, ""));
        return lexemes;
    }

    public static Double expr(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        if (lexeme.type == LexemeType.EOF) {
            return Double.valueOf(0);
        } else {
            lexemes.back();
            return plusminus(lexemes);
        }
    }

    public static Double plusminus(LexemeBuffer lexemes) {
        Double value = multdiv(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_PLUS:
                    value += multdiv(lexemes);
                    break;
                case OP_MINUS:
                    value -= multdiv(lexemes);
                    break;
                case EOF:
                case RIGHT_PARENT:
                    lexemes.back();
                    return value;
            }
        }
    }

    public static Double multdiv(LexemeBuffer lexemes) {
        Double value = factor(lexemes);
        while (true) {
            Lexeme lexeme = lexemes.next();
            switch (lexeme.type) {
                case OP_MUL:
                    value *= factor(lexemes);
                    break;
                case OP_DIV:
                    value /= factor(lexemes);
                    break;
                case EOF:
                case RIGHT_PARENT:
                case OP_PLUS:
                case OP_MINUS:
                    lexemes.back();
                    return value;
            }
        }
    }

    public static Double factor(LexemeBuffer lexemes) {
        Lexeme lexeme = lexemes.next();
        switch (lexeme.type) {
            case NUMBER:
                return Double.parseDouble(lexeme.value);
            case LEFT_PARENT:
                double value = plusminus(lexemes);
                lexeme = lexemes.next();
                return value;

        }
        return null;
    }


}
