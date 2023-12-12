package com.example.penjualan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etNamaPelanggan, etNamaBarang, etJmlBarang, etHarga, etJmlUang;
    private TextView tvNamaPembeli, tvNamaBarangResult, tvJmlBarangResult, tvHargaResult, tvUangBayarResult,
            tvTotal, tvKembalian, tvBonus, tvKeterangan;

    private Button btnProses, btnHapus, btnKeluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNamaPelanggan = findViewById(R.id.etNamaPelanggan);
        etNamaBarang = findViewById(R.id.etNamaBarang);
        etJmlBarang = findViewById(R.id.etJmlBarang);
        etHarga = findViewById(R.id.etHarga);
        etJmlUang = findViewById(R.id.etJmlUang);

        tvNamaPembeli = findViewById(R.id.tvNamaPembeli);
        tvNamaBarangResult = findViewById(R.id.tvNamaBarangResult);
        tvJmlBarangResult = findViewById(R.id.tvJmlBarangResult);
        tvHargaResult = findViewById(R.id.tvHargaResult);
        tvUangBayarResult = findViewById(R.id.tvUangBayarResult);
        tvTotal = findViewById(R.id.tvTotal);
        tvKembalian = findViewById(R.id.tvKembalian);
        tvBonus = findViewById(R.id.tvBonus);
        tvKeterangan = findViewById(R.id.tvKeterangan);

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
        String namaPelanggan = etNamaPelanggan.getText().toString();
        String namaBarang = etNamaBarang.getText().toString();
        String jmlBarangStr = etJmlBarang.getText().toString();
        String hargaStr = etHarga.getText().toString();
        String jmlUangStr = etJmlUang.getText().toString();

        tvNamaPembeli.setText("Nama Pembeli: " + namaPelanggan);
        tvNamaBarangResult.setText("Nama Barang: " + namaBarang);
        tvJmlBarangResult.setText("Jumlah Barang: " + jmlBarangStr);
        tvHargaResult.setText("Harga: Rp " + hargaStr);
        tvUangBayarResult.setText("Uang Bayar: " + jmlUangStr);

        int jmlBarang = Integer.parseInt(jmlBarangStr);
        int harga = Integer.parseInt(hargaStr);
        int total = jmlBarang * harga;
        int jmlUang = Integer.parseInt(jmlUangStr);

        tvTotal.setText("Total Belanja: Rp " + total);
        if (jmlUang > total) {
            int kembalian = jmlUang - total;
            tvKembalian.setText("Kembalian: Rp " + kembalian);
            tvBonus.setText("Bonus: " + "HardDisk 1TB");
            tvKeterangan.setText("Keterangan: " + "Tunggu kembalian");
        }else{
            tvKembalian.setText("Kembalian: Rp " + "Tidak ada");
            tvBonus.setText("Bonus: " + "Tidak ada");
            tvKeterangan.setText("Keterangan: " + "Silakan belanja kembali");
        }
    }

    private void clearData() {
        etNamaPelanggan.getText().clear();
        etNamaBarang.getText().clear();
        etJmlBarang.getText().clear();
        etHarga.getText().clear();
        etJmlUang.getText().clear();

        tvNamaPembeli.setText("");
        tvNamaBarangResult.setText("");
        tvJmlBarangResult.setText("");
        tvHargaResult.setText("");
        tvUangBayarResult.setText("");
        tvTotal.setText("");
        tvKembalian.setText("");
        tvBonus.setText("");
        tvKeterangan.setText("");
    }
}