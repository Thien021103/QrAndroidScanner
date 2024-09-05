package com.example.qrscanner;

import android.Manifest;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class QRDetailActivity extends AppCompatActivity {
    private ImageView qrCode;
    private Button saveQrBtn, shareQrBtn, interactBtn, homeBtn;
    private TextView content;
    private String raw, result;
    private int type;
    private Bitmap bitmap;
    private static final int EXTERNAL_STORAGE_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_qrdetail);

        result = this.getIntent().getStringExtra("RESULT");
        raw = this.getIntent().getStringExtra("RAW");
        type = this.getIntent().getIntExtra("TYPE", 0);

        bitmap = QRGenActivity.generateQRCode(raw);

        qrCode = findViewById(R.id.qrImg);
        saveQrBtn = findViewById(R.id.saveBtn);
        shareQrBtn = findViewById(R.id.shareBtn);
        interactBtn = findViewById(R.id.interactBtn);
        homeBtn = findViewById(R.id.detailHomeBtn);
        content = findViewById(R.id.qrContent);

        if(bitmap == null) {
            Toast.makeText(this, "Could not resolve QR", Toast.LENGTH_SHORT).show();
        }
        else {
            qrCode.setImageBitmap(bitmap);
        }
        switch (type) {
            case 0: // Text
                interactBtn.setVisibility(View.GONE);
                interactBtn.setClickable(false);
                content.setText(
                    "___RESULT___\n" + result
                );
                break;
            case 1: // Phone
                interactBtn.setVisibility(View.VISIBLE);
                interactBtn.setClickable(true);
                interactBtn.setText("Dial");
                interactBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(raw));
                        startActivity(intent);
                    }
                });
                content.setText(
                    "___PHONE___\n" + result
                );
                break;
            case 2: // Email
                interactBtn.setVisibility(View.VISIBLE);
                interactBtn.setClickable(true);
                interactBtn.setText("Send email");
                interactBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse(raw));
                        startActivity(intent);
                    }
                });
                content.setText(
                    "___EMAIL___\n" + result
                );
                break;
            case 3: // Link
                interactBtn.setVisibility(View.VISIBLE);
                interactBtn.setClickable(true);
                interactBtn.setText("Go to link");
                interactBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(raw));
                        startActivity(intent);
                    }
                });
                content.setText(
                        "___LINK___\n" + result
                );
                break;
            case 4: // SMS
                interactBtn.setVisibility(View.VISIBLE);
                interactBtn.setClickable(true);
                interactBtn.setText("Send SMS");
                interactBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(raw));
                        startActivity(intent);
                    }
                });
                content.setText(
                        "____SMS____\n" + result
                );
                break;
            case 5: // Location
                interactBtn.setVisibility(View.VISIBLE);
                interactBtn.setClickable(true);
                interactBtn.setText("See on map");
                interactBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(raw));
                        startActivity(intent);
                    }
                });
                content.setText(
                        "__LOCATION__\n" + result
                );
                break;
        }
        saveQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissionsToSave();
            }
        });
        shareQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage(getApplicationContext(), bitmap);
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
    private void saveQrCodeToGallery() {

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
    public void shareImage(Context context, Bitmap bitmap) {
        // Save the bitmap to a file
        File imageFile = saveBitmapToFile(context, bitmap, "shared_image.png");

        // Get the content URI for the saved image file
        Uri contentUri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", imageFile);

        if (contentUri != null) {
            // Create a share intent
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // Grant temporary read permission to the URI
            shareIntent.setDataAndType(contentUri, context.getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            shareIntent.setType("image/png");

            // Start the activity to share the image
            context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
        }
    }
    public File saveBitmapToFile(Context context, Bitmap bitmap, String fileName) {
        // Save the bitmap to a file in the cache directory
        File cachePath = new File(context.getCacheDir(), "images");
        cachePath.mkdirs(); // Create the images directory if it doesn't exist
        File imageFile = new File(cachePath, fileName);

        try (FileOutputStream stream = new FileOutputStream(imageFile)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // Use PNG format
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageFile;
    }
    private void requestPermissionsToSave() {
        // List of permissions to request
        String[] permissions = {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };

        // Check if permissions are already granted
        if (
               ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permissions
            ActivityCompat.requestPermissions(this, permissions, EXTERNAL_STORAGE_PERMISSION_CODE);
        } else {
            // Permissions are already granted, proceed
            saveQrCodeToGallery();
        }
    }

    // This method is called when the user responds to the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CODE) {
            boolean allPermissionsGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false;
                    break;
                }
            }

            if (allPermissionsGranted) {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show();
                saveQrCodeToGallery();
            } else {
                // Some permissions were denied, show a message to the user
                Toast.makeText(this, "All permissions are required to proceed.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
