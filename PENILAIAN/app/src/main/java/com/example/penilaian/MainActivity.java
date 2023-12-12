package com.example.penilaian;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNamaMahasiswa, etNilaiTugas, etNilaiETS, etNilaiEAS, etNilaiFP;
    private TextView tvNamaMahasiswa, tvNilaiAngka, tvNilaiHuruf;

    private Button btnProses, btnHapus, btnKeluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNamaMahasiswa = findViewById(R.id.etNamaMahasiswa);
        etNilaiTugas = findViewById(R.id.etNilaiTugas);
        etNilaiETS = findViewById(R.id.etNilaiETS);
        etNilaiEAS = findViewById(R.id.etNilaiEAS);
        etNilaiFP = findViewById(R.id.etNilaiFP);

        tvNamaMahasiswa = findViewById(R.id.tvNamaMahasiswa);
        tvNilaiAngka = findViewById(R.id.tvNilaiAngka);
        tvNilaiHuruf = findViewById(R.id.tvNilaiHuruf);

        btnProses = findViewById(R.id.btnProses);
        btnHapus = findViewById(R.id.btnHapus);
        btnKeluar = findViewById(R.id.btnKeluar);

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processInput();
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearData();
            }
        });

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void processInput() {
        String namaMahasiswa = etNamaMahasiswa.getText().toString();
        String hurufAkhir = "";
        tvNamaMahasiswa.setText("Nama Mahasiswa: " + namaMahasiswa);

        int nilaiTugas = Integer.parseInt(etNilaiTugas.getText().toString());
        int nilaiETS = Integer.parseInt(etNilaiETS.getText().toString());
        int nilaiEAS = Integer.parseInt(etNilaiEAS.getText().toString());
        int nilaiFP = Integer.parseInt(etNilaiFP.getText().toString());

        double nilaiAkhir = nilaiTugas * 0.1 + nilaiETS * 0.3 + nilaiEAS * 0.3 + nilaiFP * 0.3;
        if (nilaiAkhir <= 100 && nilaiAkhir > 85){
            hurufAkhir="A";
        }else if (nilaiAkhir <= 85 && nilaiAkhir > 70){
            hurufAkhir="B";
        }
        else if (nilaiAkhir <= 70 && nilaiAkhir > 60){
            hurufAkhir="C";
        }else if (nilaiAkhir <= 60 && nilaiAkhir > 50){
            hurufAkhir="D";
        }

        tvNamaMahasiswa.setText("Nama Mahasiswa: " + namaMahasiswa);
        tvNilaiAngka.setText("Nilai Angka Akhir: " + nilaiAkhir);
        tvNilaiHuruf.setText("Nilai Huruf Akhir: " + hurufAkhir);
    }

    private void clearData() {
        etNamaMahasiswa.getText().clear();
        etNilaiTugas.getText().clear();
        etNilaiETS.getText().clear();
        etNilaiEAS.getText().clear();
        etNilaiFP.getText().clear();

        tvNamaMahasiswa.setText("");
        tvNilaiAngka.setText("");
        tvNilaiHuruf.setText("");
    }
}