package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.util.Date;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISSION = 123;
    private static final int kodekamera = 222;
    private Button b1;
    private ImageView iv;
    private String nmFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button);
        iv = findViewById(R.id.imageView);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                } else {
                    startCamera();
                }
            }
        });
    }

    private void startCamera() {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File imagesFolder = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "HasilFoto");

        if (!imagesFolder.exists()) {
            if (!imagesFolder.mkdirs()) {
                Toast.makeText(this, "Failed to create directory", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Date d = new Date();
        CharSequence s = DateFormat.format("yyyyMMdd-hh-mm-ss", d.getTime());
        nmFile = imagesFolder + File.separator + s.toString() + ".jpg";

        File image = new File(imagesFolder, s.toString() + ".jpg");
        Uri uriSavedImage = FileProvider.getUriForFile(
                this,
                "app.nbs.camera.fileprovider",
                image
        );

        it.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
        startActivityForResult(it, kodekamera);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case kodekamera:
                    prosesKamera(data);
                    break;
            }
        }
    }

    private void prosesKamera(Intent datanya) {
        Bitmap bm;

        BitmapFactory.Options options;
        options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        bm = BitmapFactory.decodeFile(nmFile, options);
        iv.setImageBitmap(bm);
        Toast.makeText(this, "Data Telah Terload ke ImageView" + nmFile, Toast.LENGTH_SHORT).show();
    }
}
