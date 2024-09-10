package com.example.calculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // Buttons for digits and operations
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0;
    Button clear, cross, add, sub, multiply, divide, square_root, bracket, ans, dot,history,answer ;
    boolean check;
    String expression;
  ArrayList<String> list = new ArrayList<>();
    DataHelper dataHelper;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize display and output text views
        TextView display = findViewById(R.id.display);
        TextView output = findViewById(R.id.textView3);
        final String[] text = {display.getText().toString()};
        // Initialize buttons
        history=findViewById(R.id.history);
        btn1 = findViewById(R.id.one_1);
        btn2 = findViewById(R.id.Two_2);
        btn3 = findViewById(R.id.three_3);
        btn4 = findViewById(R.id.four_4);
        btn5 = findViewById(R.id.five_5);
        btn6 = findViewById(R.id.six_6);
        btn7 = findViewById(R.id.seven_7);
        btn8 = findViewById(R.id.eigth_8);
        btn9 = findViewById(R.id.nine_9);
        btn0 = findViewById(R.id.zero_0);
        clear = findViewById(R.id.clear_text);
        cross = findViewById(R.id.clear);
        add = findViewById(R.id.add);
        sub = findViewById(R.id.sub);
        multiply = findViewById(R.id.multiply);
        divide = findViewById(R.id.divide);
        square_root = findViewById(R.id.square_root);
        bracket = findViewById(R.id.bracket);
        ans = findViewById(R.id.ans);
        dot = findViewById(R.id.dot);

        // Set up button click listeners

        add.setOnClickListener(clickedView -> {
            expression = display.getText().toString();
            if (expression.contains("=")) {
                int index = expression.indexOf("=");
                expression = expression.substring(index + 1);
                display.setText(expression);
            }
            display.setText(display.getText() + "+");
        });
        sub.setOnClickListener(clickedView -> {
            expression = display.getText().toString();
            if (expression.contains("=")) {
                int index = expression.indexOf("=");
                expression = expression.substring(index + 1);
                display.setText(expression);
            }
            display.setText(display.getText() + "-");
        });
        multiply.setOnClickListener(clickedView -> {
            expression = display.getText().toString();
            if (expression.contains("=")) {
                int index = expression.indexOf("=");
                expression = expression.substring(index + 1);
                display.setText(expression);
            }
            display.setText(display.getText() + "*");
        });
        divide.setOnClickListener(clickedView -> {
            expression = display.getText().toString();
            if (expression.contains("=")) {
                int index = expression.indexOf("=");
                expression = expression.substring(index + 1);
                display.setText(expression);
            }
            display.setText(display.getText() + "/");
        });
        dot.setOnClickListener(clickedView -> {
            display.setText(display.getText() + ".");
        });
        cross.setOnClickListener(clickedView -> {
            int length = display.getText().length();
            if (length > 0) {
                display.setText(display.getText().toString().substring(0, length - 1));
            }
        });
        clear.setOnClickListener(clickedView -> {
            display.setText("");
            output.setText("");
        });
        bracket.setOnClickListener(clickedView -> {
            display.setText(display.getText() + "(");
        });
        bracket.setOnClickListener(clickedView -> {
            String currentText = display.getText().toString();
            int openParentheses = 0;
            int closeParentheses = 0;

            // Count existing parentheses in the current expression
            for (char c : currentText.toCharArray()) {
                if (c == '(') openParentheses++;
                if (c == ')') closeParentheses++;
            }

            // Add the appropriate parenthesis based on balance
            if (openParentheses > closeParentheses) {
                display.setText(currentText + ")");
            } else {
                display.setText(currentText + "(");
            }
        });

        square_root.setOnClickListener(clickedView -> {
            expression = display.getText().toString();
            if (expression.contains("=")) {
                int index = expression.indexOf("=");
                expression = expression.substring(index + 1);
                display.setText(expression);
            }
            display.setText(display.getText() + "√");
        });
        //
        //number button
        btn0.setOnClickListener(clickedView -> {
            display.setText(display.getText() + "0");
        });
        btn1.setOnClickListener(clickedView -> {
            display.setText(display.getText() + "1");
        });
        btn2.setOnClickListener(clickedView -> {
            display.setText(display.getText() + "2");
        });
        btn3.setOnClickListener(clickedView -> {
            display.setText(display.getText() + "3");
        });
        btn4.setOnClickListener(clickedView -> {
            display.setText(display.getText() + "4");
        });
        btn5.setOnClickListener(clickedView -> {
            display.setText(display.getText() + "5");
        });
        btn6.setOnClickListener(clickedView -> {
            display.setText(display.getText() + "6");
        });
        btn7.setOnClickListener(clickedView -> {
            display.setText(display.getText() + "7");
        });
        btn8.setOnClickListener(clickedView -> {
            display.setText(display.getText() + "8");
        });
        btn9.setOnClickListener(clickedView -> {
            display.setText(display.getText() + "9");
        });
        //number button

        ans.setOnClickListener(clickedView -> {
            String expression = display.getText().toString();
            expression = expression.replace("=", "");
            if (!expression.isEmpty()) {
                try {
                double  result = evaluateExpression(expression);
                    display.setText(expression + "=" + String.valueOf(result));
                    output.setText(String.valueOf(result));
                  list.add(expression +"="+ String.valueOf(result));
//change
                    dataHelper = DataHelper.getInstance(this);
                    Memo history = new Memo(expression +"="+ String.valueOf(result));
                    new Thread(() -> {
                        dataHelper.noteDao().insert(history);
                        runOnUiThread(() -> {
                            Toast.makeText(MainActivity.this, "Note saved", Toast.LENGTH_SHORT).show();
                        //    loadNotes();  // Refresh the RecyclerView
  //change
                        });
                    }).start();

                } catch (Exception e) {
                    display.setText("Error");
                }
            }


        });

        history.setOnClickListener(clickedView -> {
            Intent intent = new Intent(MainActivity.this, history.class); // Ensure HistoryActivity is the correct name
            intent.putStringArrayListExtra("EXPRESSION_HISTORY", list); // Passing the list
            startActivity(intent);
        });

    }
    private double evaluateExpression(String expression) {
        expression = expression.replace("=", "");
        expression = expression.replaceAll("√(\\d+)", "Math.sqrt($1)");
        Context context = Context.enter();
        context.setOptimizationLevel(-1); // Disable optimizations for simplicity
        Scriptable scope = context.initStandardObjects();
        double result;
        try {
            Object evalResult = context.evaluateString(scope, expression, "expression", 1, null);
            result = Context.toNumber(evalResult);
            result = Math.round(result * 1_000_0.0) / 1_000_0.0;
        } catch (Exception e) {
            e.printStackTrace();
            result = Double.NaN;
        } finally {
            Context.exit();
        }
        return result;
    }
}

