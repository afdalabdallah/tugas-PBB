package com.example.crud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextName;
    private String editTextNrp;
    private Button buttonAdd, buttonShowUsers,buttonDelete,buttonUpdate,buttonShowAll;
    private final DBHelper DB= new DBHelper(this);

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = (EditText) findViewById(R.id.editTextName);

        buttonAdd = (Button) findViewById(R.id.btnSimpan);
        buttonAdd.setOnClickListener(this);

        buttonShowUsers = (Button) findViewById(R.id.btnAmbil);
        buttonShowUsers.setOnClickListener(this);

        buttonDelete = (Button) findViewById(R.id.btnDelete);
        buttonDelete.setOnClickListener(this);

        buttonUpdate = (Button) findViewById(R.id.btnUpdate);
        buttonUpdate.setOnClickListener(this);

        buttonShowAll = (Button) findViewById(R.id.btnShowAll);
        buttonShowAll.setOnClickListener(this);
        listView = findViewById(R.id.listView);

        Button btnInputNRP = findViewById(R.id.btnInputNRP);
        btnInputNRP.setOnClickListener(v -> showInputDialog());
    }

    public void insert() {
        Boolean insert = this.DB.insertData(editTextName.getText().toString(),this.editTextNrp);
        try     {this.checkResult(insert);}
        catch (Exception e) {Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();}
    }

    @SuppressLint("Range")
    public void getData(){
        String nama = editTextName.getText().toString();
        String nrp = this.editTextNrp;

        try {
            if (nama != null || (nama != null && nrp !=null)){
                String[] result = this.DB.checkNama(nama);
                Toast.makeText(this, result[0] + " " + result[1], Toast.LENGTH_LONG).show();
            }
            else if (nrp!=null) {
                String[] result = this.DB.checkNrp(nrp);
                Toast.makeText(this, result[0] + " " + result[1], Toast.LENGTH_LONG).show();
            }
            else { Toast.makeText(this, "Data NULL", Toast.LENGTH_LONG).show();}
        }
        catch(Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void update() {
        Boolean update = this.DB.updateNama(editTextName.getText().toString(), this.editTextNrp);
        try     {this.checkResult(update);}
        catch(Exception e)  {Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();}
    }

    public void delete() {
        Boolean delete = this.DB.deleteNrpByNama(editTextName.getText().toString());
        try     { this.checkResult(delete);}
        catch(Exception e)  { Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();}
    }

    public void showAll(){
        String[] data = this.DB.showAll();
        if (data != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_user, R.id.textViewName, data);
            listView.setAdapter(adapter);
        }
    }

    public void checkResult(Boolean result){
        String[] output;
        if(result) {
            output = this.DB.checkNama(editTextName.getText().toString());
            Toast.makeText(this, output[0] + " " + output[1], Toast.LENGTH_LONG).show();
        }
        else { Toast.makeText(this, "Action Failed", Toast.LENGTH_LONG).show();}
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.input_dialog, null);
        builder.setView(dialogView);
        final EditText inputEditText = dialogView.findViewById(R.id.inputEditText);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String nrp = inputEditText.getText().toString();
            editTextNrp=nrp;
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSimpan) {
            this.insert();
        }
        else if (view.getId() == R.id.btnAmbil) {
            this.getData();
        }
        else if (view.getId() == R.id.btnDelete){
            this.delete();
        }
        else if (view.getId() == R.id.btnUpdate){
            this.update();
        }
        else if (view.getId() == R.id.btnShowAll){
            this.showAll();
        }
    }

}