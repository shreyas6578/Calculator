package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class history extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TextView textView = findViewById(R.id.textview);
        Button back = findViewById(R.id.back);
        Button clear = findViewById(R.id.clear);

        // Retrieve and display history
        ArrayList<String> expressionHistory = getIntent().getStringArrayListExtra("EXPRESSION_HISTORY");
        if (expressionHistory != null && !expressionHistory.isEmpty()) {
            for (String expr : expressionHistory) {
                textView.append(expr + "\n");
            }
        } else {
            textView.setText("No history available");
        }

        back.setOnClickListener(clickedView -> {
            Intent intent = new Intent(history.this, MainActivity.class);
            startActivity(intent);
        });

        clear.setOnClickListener(view -> textView.setText(""));
    }
    }
