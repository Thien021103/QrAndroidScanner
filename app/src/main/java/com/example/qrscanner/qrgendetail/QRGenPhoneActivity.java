package com.example.qrscanner.qrgendetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qrscanner.MainActivity;
import com.example.qrscanner.QRDetailActivity;
import com.example.qrscanner.R;

public class QRGenPhoneActivity extends AppCompatActivity {
    private String raw, result;
    private EditText qrPhone;
    private Spinner areaSpinner;
    private ImageButton homeBtn;
    private Button genBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qrgen_phone);

        qrPhone = findViewById(R.id.qrPhone);
        homeBtn = findViewById(R.id.genPhoneHomeBtn);
        genBtn = findViewById(R.id.qrPhoneGenBtn);

        genBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qrPhone.getText() == null) {
                    Toast.makeText(
                        getApplicationContext(), "Please enter valid phone number", Toast.LENGTH_LONG
                    ).show();
                }
                else {
                    raw = "tel:" + areaSpinner + String.valueOf(qrPhone.getText());
                    result = areaSpinner + String.valueOf(qrPhone.getText());
                    Intent intent = new Intent(getApplicationContext(), QRDetailActivity.class);
                    intent.putExtra("RAW", raw);
                    intent.putExtra("RESULT", result);
                    intent.putExtra("TYPE", 1);
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