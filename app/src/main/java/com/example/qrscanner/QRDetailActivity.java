package com.example.qrscanner;

import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.OutputStream;

public class QRDetailActivity extends AppCompatActivity {
    private ImageView qrCode;
    private Button saveQrBtn, shareQrBtn;
    private TextView qrInfo;
    private String raw, result;
    private int type;
    private Bitmap bitmap;
    private static final int WRITE_EXTERNAL_STORAGE_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qrdetail);

        this.getIntent().getStringExtra("RESULT");
        this.getIntent().getStringExtra("RAW");
        this.getIntent().getIntExtra("TYPE", 0);

        switch (type) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }

        bitmap = QRGenActivity.generateQRCode(raw);

        //saveQrBtn = findViewById();
//        saveQrBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                    saveQrCodeToGallery();
//                } else {
//                    if (checkStoragePermission()) {
//                        saveQrCodeToGallery();
//                    } else {
//                        requestStoragePermission();
//                    }
//                }
//            }
//        });
    }
//    private boolean checkStoragePermission() {
//        // Kiểm tra quyền lưu trữ
//        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_GRANTED;
//    }
//    private void requestStoragePermission() {
//        // Yêu cầu quyền lưu trữ nếu chưa được cấp
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                WRITE_EXTERNAL_STORAGE_PERMISSION_CODE);
//    }
    private void saveQrCodeToGallery() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) qrCode.getDrawable();
        if (bitmapDrawable == null) {
            Toast.makeText(this, "No QR code to save!", Toast.LENGTH_SHORT).show();
            return;
        }

        Bitmap bitmap = bitmapDrawable.getBitmap();
        OutputStream fos;

        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "QRCode_" + System.currentTimeMillis() + ".png");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/QRCode");
                uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            }

            fos = getContentResolver().openOutputStream(uri);
            if (fos != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
                Toast.makeText(this, "QR code saved to Gallery!", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save QR code", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveQrCodeToGallery();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}