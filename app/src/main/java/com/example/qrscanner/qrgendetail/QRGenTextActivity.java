package com.example.qrscanner.qrgendetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qrscanner.MainActivity;
import com.example.qrscanner.QRDetailActivity;
import com.example.qrscanner.R;

public class QRGenTextActivity extends AppCompatActivity {
    private String raw;
    private EditText qrText;
    private TextView qrTextGenBtn;
    private ImageButton homeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qrgen_text);

        qrText = findViewById(R.id.qrText);
        qrTextGenBtn = findViewById(R.id.qrTextGenBtn);
        homeBtn = findViewById(R.id.genTextHomeBtn);
        qrTextGenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qrText.getText() == null) {
                    Toast.makeText(
                        getApplicationContext(), "Please enter something", Toast.LENGTH_LONG
                    ).show();
                }
                else {
                    raw = qrText.getText().toString();

                    Intent intent = new Intent(getApplicationContext(), QRDetailActivity.class);
                    intent.putExtra("RAW", raw);
                    intent.putExtra("RESULT", raw);
                    intent.putExtra("TYPE", 0);
                }
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
            }
        });
    }
}