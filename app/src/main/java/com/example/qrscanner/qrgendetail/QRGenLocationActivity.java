package com.example.qrscanner.qrgendetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qrscanner.MainActivity;
import com.example.qrscanner.QRDetailActivity;
import com.example.qrscanner.QRGenActivity;
import com.example.qrscanner.R;

public class QRGenLocationActivity extends AppCompatActivity {

    private EditText qrLatitude, qrLongitude;
    private TextView qrLocationGenBtn;
    private ImageButton homeBtn;
    private String raw, result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qrgen_location);

        qrLatitude = findViewById(R.id.qrLatitude);
        qrLongitude = findViewById(R.id.qrLongitude);
        qrLocationGenBtn = findViewById(R.id.qrLocationGenBtn);
        homeBtn = findViewById(R.id.genLocationHomeBtn);

        qrLocationGenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qrLatitude.getText() == null) {
                    Toast.makeText(
                        getApplicationContext(), "Please enter valid latitude", Toast.LENGTH_LONG
                    ).show();
                } else if(qrLongitude.getText() == null) {
                    Toast.makeText(
                        getApplicationContext(), "Please enter valid longitude", Toast.LENGTH_LONG
                    ).show();
                }
                else {
                    String latitude = qrLatitude.getText().toString();
                    String longitude = qrLongitude.getText().toString();
                    raw = "geo:" + latitude + "," + longitude + "?q=" + latitude + "," + longitude;

                    Intent intent = new Intent(getApplicationContext(), QRDetailActivity.class);
                    intent.putExtra("RAW", raw);
                    intent.putExtra("RESULT", latitude + "," + longitude);
                    intent.putExtra("TYPE", 5);
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