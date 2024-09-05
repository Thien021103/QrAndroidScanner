package com.example.qrscanner.qrgendetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qrscanner.QRDetailActivity;
import com.example.qrscanner.R;

public class QRGenTextActivity extends AppCompatActivity {
    private String raw;
    private TextView qrText;
    private Button qrTextGenBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qrgen_text);

        qrText = findViewById(R.id.qrText);
        qrTextGenBtn = findViewById(R.id.qrTextGenBtn);

        qrTextGenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                raw = qrText.getText().toString();

                Intent intent = new Intent(getApplicationContext(), QRDetailActivity.class);
                intent.putExtra("RAW", raw);
                intent.putExtra("RESULT", raw);
                intent.putExtra("TYPE", 0);
            }
        });
    }
}