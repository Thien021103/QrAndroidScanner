package com.example.qrscanner.qrgendetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qrscanner.MainActivity;
import com.example.qrscanner.QRDetailActivity;
import com.example.qrscanner.R;

public class QRGenSMSActivity extends AppCompatActivity {
    private String raw;
    private EditText qrSMS;
    private Button qrSMSGenBtn;
    private ImageButton homeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qrgen_link);

        qrSMS = findViewById(R.id.qrSMS);
        qrSMSGenBtn = findViewById(R.id.qrSMSGenBtn);
        homeBtn = findViewById(R.id.genSMSHomeBtn);
        qrSMSGenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                raw = qrSMS.getText().toString();

                Intent intent = new Intent(getApplicationContext(), QRDetailActivity.class);
                intent.putExtra("RAW", raw);
                intent.putExtra("RESULT", raw);
                intent.putExtra("TYPE", 3);
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