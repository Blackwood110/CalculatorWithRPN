package com.example.calculatorwithrpn;

import java.math.BigDecimal;
import java.util.LinkedList;

public class Calculator {
    static boolean isDelim(char c) { // тру если пробел
        return c == ' ';
    }

    static boolean isOperator(char c) { // возвращяем тру если один из символов ниже
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }

    static int priority(char op) {
        switch (op) { // при + или - возврат 1, при * / % 2 иначе -1
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
            case '%':
                return 2;
            default:
                return -1;
        }
    }

    static void processOperator(LinkedList<Double> st, char op) {
        double r = st.removeLast(); // выдёргиваем из упорядоченного листа последний элемент
        double l = st.removeLast(); // также
        switch (op) { // выполняем действие между l и r в зависимости от оператора в кейсе и результат валим в st
            case '+':
                st.add(new BigDecimal(l).add(new BigDecimal(r)).doubleValue());
                break;
            case '-':
                st.add(new BigDecimal(l).subtract(new BigDecimal(r)).doubleValue());
                break;
            case '*':
                st.add(new BigDecimal(l).multiply(new BigDecimal(r)).doubleValue());
                break;
            case '/':
                st.add(new BigDecimal(l).divide(new BigDecimal(r)).doubleValue());
                break;
        }
    }

    public double eval(String s) {
        LinkedList<Double> st = new LinkedList<Double>(); // сюда наваливают цифры
        LinkedList<Character> op = new LinkedList<Character>(); // сюда опрераторы и st и op в порядке поступления
        for (int i = 0; i < s.length(); i++) { // парсим строку с выражением и вычисляем
            char c = s.charAt(i);
            if (isDelim(c))
                continue;
            if ((c == '(') && (s.charAt(i + 1) == '-')) {
                String operand = "-";
                i += 2;
                while (i < s.length() && (Character.isDigit(s.charAt(i)) || (s.charAt(i) == '.')))
                    operand += s.charAt(i++);
                --i;
                st.add(Double.parseDouble(operand));
            } else if (c == '(')
                op.add('(');
            else if (c == ')') {
                while (op.getLast() != '(')
                    processOperator(st, op.removeLast());
                op.removeLast();
            } else if (isOperator(c)) {
                while (!op.isEmpty() && priority(op.getLast()) >= priority(c))
                    processOperator(st, op.removeLast());
                op.add(c);
            } else {
                String operand = "";
                while (i < s.length() && (Character.isDigit(s.charAt(i)) || (s.charAt(i) == '.')))
                    operand += s.charAt(i++);
                --i;
                st.add(Double.parseDouble(operand));
            }
        }
        while (!op.isEmpty())
            processOperator(st, op.removeLast());
        return st.get(0);  // возврат результата
    }
}
