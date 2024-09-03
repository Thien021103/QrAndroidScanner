package com.example.qrscanner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;


public class QRScanActivity extends CaptureActivity {
    private DecoratedBarcodeView barcodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        barcodeView = findViewById(R.id.zxing_barcode_scanner);

        // Set a custom DecoderFactory if you want to scan specific barcode formats
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
        barcodeView.setDecoderFactory(new DefaultDecoderFactory(formats));


        // Set up continuous decoding
        barcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                handleResult(result);
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
                // Handle possible result points if needed
            }
        });
    }

    private void handleResult(BarcodeResult result) {
        // Handle the scanned result
        Intent resultIntent = new Intent(QRScanActivity.this, QRDetailActivity.class);
        resultIntent.putExtra("RESULT", result.getText());
        Log.e("---SUPER_TAG_FOR_RESULT---", result.getText());

        finish(); // Close the activity after the scan
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