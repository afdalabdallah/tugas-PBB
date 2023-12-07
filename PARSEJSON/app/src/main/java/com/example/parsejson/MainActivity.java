package com.example.parsejson;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        String json = loadJSONFromAsset("contacts.json");
        ArrayList<String> contactList = parseJSON(json);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactList);
        listView.setAdapter(adapter);
    }

    private String loadJSONFromAsset(String fileName) {
        String json;
        try {
            AssetManager assetManager = getAssets();
            InputStream is = assetManager.open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    private ArrayList<String> parseJSON(String json) {
        ArrayList<String> contactList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray contactsArray = jsonObject.getJSONArray("contacts");

            for (int i = 0; i < contactsArray.length(); i++) {
                JSONObject contact = contactsArray.getJSONObject(i);

                String id = contact.getString("id");
                String name = contact.getString("name");
                String email = contact.getString("email");
                String address = contact.getString("address");
                String gender = contact.getString("gender");

                JSONObject phone = contact.getJSONObject("phone");
                String mobile = phone.getString("mobile");

                String contactInfo = "ID: " + id + "\nName: " + name + "\nEmail: " + email +
                        "\nAddress: " + address + "\nGender: " + gender + "\nMobile: " + mobile;
                contactList.add(contactInfo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contactList;
    }
}