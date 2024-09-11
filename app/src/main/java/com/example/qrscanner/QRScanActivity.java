package com.example.qrscanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class QRScanActivity extends CaptureActivity {
    private DecoratedBarcodeView barcodeView;
    private ImageButton homeBtn;
    private String raw, result;
    private int type;
    private String[] info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        barcodeView = findViewById(R.id.zxing_barcode_scanner);
        homeBtn = findViewById(R.id.scanHomeBtn);

        // Set a custom DecoderFactory if you want to scan specific barcode formats
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
        barcodeView.setDecoderFactory(new DefaultDecoderFactory(formats));

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homeIntent);
            }
        });
        // Set up continuous decoding
        barcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                try {
                    handleResult(result);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
                // Handle possible result points if needed
            }
        });
    }

    private void handleResult(BarcodeResult barcodeResult) throws UnsupportedEncodingException {
        // Handle the scanned result
        if (barcodeResult.getText() == null) {
            Toast.makeText(this,"Could not resolve QR", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent detailIntent = new Intent(QRScanActivity.this, QRDetailActivity.class);
            raw = barcodeResult.getText();
            info = raw.split(":", 2);
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
            Log.e("---SUPER_TAG_FOR_RESULT---", barcodeResult.getText());
            startActivity(detailIntent);
            finish(); // Close the activity after the scan
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();
    }
}