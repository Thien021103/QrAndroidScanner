package com.example.qrscanner.qrgendetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qrscanner.MainActivity;
import com.example.qrscanner.QRDetailActivity;
import com.example.qrscanner.R;

public class QRGenSMSActivity extends AppCompatActivity {
    private String raw;
    private EditText qrPhone, qrBody;
    private Button qrSMSGenBtn;
    private ImageButton homeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qrgen_sms);

        qrPhone = findViewById(R.id.qrPhoneSMS);
        qrBody = findViewById(R.id.qrBodySMS);
        qrSMSGenBtn = findViewById(R.id.qrSMSGenBtn);
        homeBtn = findViewById(R.id.genSMSHomeBtn);
        qrSMSGenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qrPhone.getText() == null) {
                    Toast.makeText(
                        getApplicationContext(), "Please enter valid phone number", Toast.LENGTH_LONG
                    ).show();
                }
                else if(qrBody.getText() == null) {
                    Toast.makeText(
                            getApplicationContext(), "Please enter body", Toast.LENGTH_LONG
                    ).show();
                }
                else {
                    String phoneNumber = qrPhone.getText().toString();
                    String body = qrBody.getText().toString();
                    raw = "sms:" + phoneNumber + "?body=" + Uri.encode(body);

                    Intent intent = new Intent(getApplicationContext(), QRDetailActivity.class);
                    intent.putExtra("RAW", raw);
                    intent.putExtra("RESULT", phoneNumber);
                    intent.putExtra("BODY", body);
                    intent.putExtra("TYPE", 4);
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