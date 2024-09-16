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

import com.example.qrscanner.MainActivity;
import com.example.qrscanner.QRDetailActivity;
import com.example.qrscanner.R;

public class QRGenLinkActivity extends AppCompatActivity {
    private String raw;
    private EditText qrLink;
    private TextView qrLinkGenBtn;
    private ImageButton homeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qrgen_link);

        qrLink = findViewById(R.id.qrLink);
        qrLinkGenBtn = findViewById(R.id.qrLinkGenBtn);
        homeBtn = findViewById(R.id.genLinkHomeBtn);
        qrLinkGenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qrLink.getText() == null) {
                    Toast.makeText(
                        getApplicationContext(), "Please enter valid link", Toast.LENGTH_LONG
                    ).show();
                }
                else {
                    raw = qrLink.getText().toString();

                    Intent intent = new Intent(getApplicationContext(), QRDetailActivity.class);
                    intent.putExtra("RAW", raw);
                    intent.putExtra("RESULT", raw);
                    intent.putExtra("TYPE", 3);
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