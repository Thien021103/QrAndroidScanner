package com.example.qrscanner.qrgendetail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.qrscanner.QRGenActivity;
import com.example.qrscanner.R;

public class QRGenLocationActivity extends AppCompatActivity {

    private EditText editLatitude, editLongitude, editLabel;
    private ImageView qrCode;
    private Button qrGenBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qrgen_location);

//        editLabel = findViewById();
//        editLatitude = findViewById();
//        editLongitude = findViewById();
//        qrCode = findViewById();
//        qrGenBtn = findViewById();

        qrGenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //QRGenActivity.generateQRCode();
            }
        });
    }
}