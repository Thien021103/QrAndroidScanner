package com.example.qrscanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.android.Intents;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.integration.android.IntentIntegrator;

import java.io.IOException;
import java.net.URLDecoder;

public class MainActivity extends Activity {

    public static final int QR_SCAN_REQUEST = 0;
    public static final int IMG_CHOOSE_REQUEST = 1;

    private TextView chooseImgBtn, QRGenBtn, QRScanBtn;
    private String[] info;
    private String raw, result;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QRScanBtn = findViewById(R.id.qr_scan_btn_id);
        QRGenBtn = findViewById(R.id.qr_gen_btn_id);
        chooseImgBtn = findViewById(R.id.img_choose_btn_id);

        chooseImgBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });

        QRScanBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openQRScanner();
            }
        });

        QRGenBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openQRGenerator();
            }
        });
    }

    private void openImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        this.startActivityForResult(Intent.createChooser(intent, "Select Image for scanning"), IMG_CHOOSE_REQUEST);
    }
    private void openQRScanner(){
        Intent intentScan = new Intent(this, QRScanActivity.class);
        this.startActivity(intentScan);
    }
    private void openQRGenerator(){
        Intent intent = new Intent(MainActivity.this, QRGenActivity.class);
        startActivity(intent);
    }

    @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_CHOOSE_REQUEST && resultCode == RESULT_OK
            && data != null && data.getData() != null)
        {
            Uri imageUri = data.getData();
            try {
                // Convert the image URI to a Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                // Decode the QR code from the Bitmap
                raw = QRGenActivity.decodeQRCode(bitmap);
                if(raw == null) {
                    Toast.makeText(this, "Could not rosolve QR", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    info = raw.split(":", 2);
                    Intent detailIntent = new Intent(MainActivity.this, QRDetailActivity.class);
                    switch (info[0]) {
                        case "tel":
                            result = info[1];
                            type = 1;
                            break;
                        case "mailto":
                            result = info[1];
                            String[] mailParts = result.split("\\?", 2);
                            String email = mailParts[0];
                            String[] pairs = mailParts[1].split("&", 2);
                            // Decode each key-value pair
                            String subject = URLDecoder.decode(pairs[0].substring(8), "UTF-8");
                            String mailBody = URLDecoder.decode(pairs[1].substring(5), "UTF-8");
                            detailIntent.putExtra("EMAIL", email);
                            detailIntent.putExtra("SUBJECT", subject);
                            detailIntent.putExtra("BODY", mailBody);
                            type = 2;
                            break;
                        case "https":
                        case "http":
                            result = info[1];
                            type = 3;
                            break;
                        case "sms":
                            String[] smsParts = info[1].split("\\?", 2);
                            result = smsParts[0];
                            String smsBody = URLDecoder.decode(smsParts[1].substring(5), "UTF-8");
                            detailIntent.putExtra("BODY", smsBody);
                            type = 4;
                            break;
                        case "geo":
                            result = info[1];
                            type = 5;
                            break;
                        default:
                            result = info[0];
                            type = 0;
                            break;
                    }
                    detailIntent.putExtra("RAW", raw);
                    detailIntent.putExtra("TYPE", type);
                    detailIntent.putExtra("RESULT", result);

                    startActivity(detailIntent);
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to resolve", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
        }
    }
}