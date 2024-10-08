package com.example.qrscanner.qrgendetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class QRGenEmailActivity extends AppCompatActivity {
    private EditText qrBody, qrSubject, qrMailto;
    private ImageButton homeBtn;
    private TextView genBtn;
    private String raw, subject, body, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qrgen_email);

        qrMailto = findViewById(R.id.qrMailto);
        qrSubject = findViewById(R.id.qrSubject);
        qrBody = findViewById(R.id.qrBody);
        homeBtn = findViewById(R.id.genEmailHomeBtn);
        genBtn = findViewById(R.id.qrEmailGenBtn);
        genBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qrMailto.getText() == null) {
                    Toast.makeText(
                        getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG
                    ).show();
                }
                else if(qrSubject.getText() == null) {
                    Toast.makeText(
                        getApplicationContext(), "Please enter your subject", Toast.LENGTH_LONG
                    ).show();
                }
                if(qrBody.getText() == null) {
                    Toast.makeText(
                            getApplicationContext(), "Please enter your body", Toast.LENGTH_LONG
                    ).show();
                }
                else {
                    email = qrMailto.getText().toString();
                    subject = qrSubject.getText().toString();
                    body = qrBody.getText().toString();
                    raw = "mailto:" + email
                        + "?subject=" + subject
                        + "&body=" + body;
                    Intent intent = new Intent(getApplicationContext(), QRDetailActivity.class);
                    intent.putExtra("RAW", raw);
                    intent.putExtra("RESULT", raw);
                    intent.putExtra("EMAIL", email);
                    intent.putExtra("SUBJECT", subject);
                    intent.putExtra("BODY", body);
                    intent.putExtra("TYPE", 2);
                    startActivity(intent);
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