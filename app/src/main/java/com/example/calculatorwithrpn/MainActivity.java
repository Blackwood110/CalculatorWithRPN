package com.example.calculatorwithrpn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText inputText;
    private TextView resultText;
    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;
    private Button btnV;
    private Button btnDiv;
    private Button btnC;
    private Button btnBra;
    private Button btnEqul;
    private Button btnSing;
    private ImageButton ibAdd;
    private ImageButton ibMin;
    private ImageButton ibMult;
    private ImageButton btnDelete;
    private boolean stateError;
    private boolean isNumber;
    private boolean lastDot;
    private Calculator calculator;
    private int countOpenBraket;
    private int countCloseBraket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculator = new Calculator();
        initializeButtons();
        setOnClick();
        inputText.setRawInputType(InputType.TYPE_NULL);
        inputText.addTextChangedListener(textWatcher);
    }

    private void initializeButtons() {
        this.inputText = findViewById(R.id.etInput);
        this.resultText = findViewById(R.id.tvResult);
        this.btn0 = findViewById(R.id.btn0);
        this.btn1 = findViewById(R.id.btn1);
        this.btn2 = findViewById(R.id.btn2);
        this.btn3 = findViewById(R.id.btn3);
        this.btn4 = findViewById(R.id.btn4);
        this.btn5 = findViewById(R.id.btn5);
        this.btn6 = findViewById(R.id.btn6);
        this.btn7 = findViewById(R.id.btn7);
        this.btn8 = findViewById(R.id.btn8);
        this.btn9 = findViewById(R.id.btn9);
        this.ibAdd = findViewById(R.id.btnPlus);
        this.ibMin = findViewById(R.id.btnMin);
        this.ibMult = findViewById(R.id.btnMul);
        this.btnDiv = findViewById(R.id.btnDivi);
        this.btnDelete = findViewById(R.id.ibDelete);
        this.btnC = findViewById(R.id.btnC);
        this.btnBra = findViewById(R.id.btnBra);
        this.btnEqul = findViewById(R.id.btnEqual);
        this.btnSing = findViewById(R.id.btnSin);
        this.btnV = findViewById(R.id.btnV);
    }

    private void setOnClick() {
        this.btn0.setOnClickListener(this);
        this.btn1.setOnClickListener(this);
        this.btn2.setOnClickListener(this);
        this.btn3.setOnClickListener(this);
        this.btn4.setOnClickListener(this);
        this.btn5.setOnClickListener(this);
        this.btn6.setOnClickListener(this);
        this.btn7.setOnClickListener(this);
        this.btn8.setOnClickListener(this);
        this.btn9.setOnClickListener(this);
        this.ibAdd.setOnClickListener(this);
        this.ibMult.setOnClickListener(this);
        this.ibMin.setOnClickListener(this);
        this.btnDelete.setOnClickListener(this);
        this.btnDiv.setOnClickListener(this);
        this.btnV.setOnClickListener(this);
        this.btnEqul.setOnClickListener(this);
        this.btnC.setOnClickListener(this);
        this.btnSing.setOnClickListener(this);
        this.btnBra.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn0:
                append("0");
                isNumber = true;
                break;
            case R.id.btn1:
                append("1");
                isNumber = true;
                break;
            case R.id.btn2:
                append("2");
                isNumber = true;
                break;
            case R.id.btn3:
                append("3");
                isNumber = true;
                break;
            case R.id.btn4:
                append("4");
                isNumber = true;
                break;
            case R.id.btn5:
                append("5");
                isNumber = true;
                break;
            case R.id.btn6:
                append("6");
                isNumber = true;
                break;
            case R.id.btn7:
                append("7");
                isNumber = true;
                break;
            case R.id.btn8:
                append("8");
                isNumber = true;
                break;
            case R.id.btn9:
                append("9");
                isNumber = true;
                break;
            case R.id.btnPlus:
                if (!isEmpty())
                    if (endsWithOperator())
                        replace("+");
                    else
                        append("+");
                isNumber = false;
                lastDot = false;
                break;
            case R.id.btnMin:
                if (!isEmpty())
                    if (endsWithOperator())
                        replace("-");
                    else
                        append("-");
                isNumber = false;
                lastDot = false;
                break;
            case R.id.btnMul:
                if (!isEmpty())
                    if (endsWithOperator())
                        replace("x");
                    else
                        append("x");
                isNumber = false;
                lastDot = false;
                break;
            case R.id.btnDivi:
                if (!isEmpty() && isNumber)
                    if (endsWithOperator())
                        replace("/");
                    else
                        append("/");
                isNumber = false;
                lastDot = false;
                break;
            case R.id.ibDelete:
                delete();
                break;
            case R.id.btnV:
                if (isNumber && !lastDot && !endWithBra() && !isClosed()) {
                    append(".");
                    isNumber = false;
                    lastDot = true;
                } else if (isEmpty()) {
                    append("0.");
                    lastDot = true;
                    isNumber = false;
                }
                break;
            case R.id.btnC:
                clear();
                break;
            case R.id.btnBra:
                bracket();
                isNumber = true;
                break;
            case R.id.btnEqual:
                calculate(true);
                break;
            case R.id.btnSin:
                setSing();
                break;
            default:
                break;
        }

    }

    private void setSing() {
        if (isEmpty()) {
            append("(-");
        } else if (isNumber && !endsWithOperator()) {
            int index1;
            int index2;
            int index3;
            int index4;
            String input = getInput();
            index1 = input.lastIndexOf("+") + 1;
            index2 = input.lastIndexOf("/") + 1;
            index3 = input.lastIndexOf("x") + 1;
            index4 = input.lastIndexOf("-") + 1;
            int lastIndex = 0;
            if (index1 > index2 && index1 > index3 && index1 > index4) {
                lastIndex = index1;
            } else if (index2 > index1 && index2 > index3 && index3 > index4) {
                lastIndex = index2;
            } else if (index3 > index2 && index3 > index1 && index3 > index4) {
                lastIndex = index3;
            } else if (index4 > index2 && index4 > index3 && index4 > index1) {
                lastIndex = index4;
            }
            char ch = input.charAt(lastIndex);
            appendSing("(-" + String.valueOf(ch), lastIndex);
            countOpenBraket++;
        }
    }

    private void appendSing(String str, int index) {
        inputText.getText().replace(index, index + 1, str);
    }

    private void bracket() {
        if (((!stateError && !isEmpty() && !endWithBra() && isNumber) || isClosed()) && countOpenBraket > countCloseBraket) {
            countCloseBraket++;
            append(")");
        } else if ((!stateError && !isEmpty() && !endWithBra() && isNumber) || isClosed()) {
            append("x(");
            countOpenBraket++;
        } else if (isEmpty() || endsWithOperator() || endWithBra()) {
            append("(");
            countOpenBraket++;
        } else if (!isEmpty() && !endWithBra()) {
            append(")");
            countCloseBraket++;
        }
    }

    private boolean endWithBra() {
        return getInput().endsWith("(");
    }

    private boolean isClosed() {
        return getInput().endsWith(")");
    }

    private boolean endsWithOperator() {
        return getInput().endsWith("+") || getInput().endsWith("-") || getInput().endsWith("/")
                || getInput().endsWith("x");
    }

    private void replace(String str) {
        inputText.getText().replace(getInput().length() - 1, getInput().length(), str);
    }

    private void clear() {
        lastDot = false;
        isNumber = false;
        stateError = false;
        inputText.getText().clear();
    }

    private void append(String str) {
        this.inputText.getText().append(str);
    }

    private void delete() {
        if (!isEmpty()) {
            this.inputText.getText().delete(getInput().length() - 1, getInput().length());
        } else {
            clear();
            ;
        }
    }

    private String getInput() {
        return this.inputText.getText().toString();
    }

    private boolean isEmpty() {
        return getInput().isEmpty();
    }

    private void calculate(boolean isEqulClick) {
        String input = getInput();
        try {
            if (!isEmpty() && !endsWithOperator()) {
                if (input.contains("x")) {
                    input = input.replaceAll("x", "*");
                }
                double result = calculator.eval(input);
                if (isEqulClick) {
                    inputText.setText(String.valueOf(result));
                    resultText.setText("");
                } else {
                    resultText.setText(String.valueOf(result));
                }
            } else resultText.setText("");
        } catch (Exception e) {
            stateError = true;
            isNumber = false;
            resultText.setText("Некорректное выражение");
        }

    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            calculate(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
