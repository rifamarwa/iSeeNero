package com.example.iseenero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    
    ListView listView;
    DatabaseReference db;
    SungaiAdapter sungaiAdapter;
    FirebaseHelper helper;
    EditText namaSungaiEditText,statusWaspadaEditText,
            ketinggianEditText, pHEditText, kecepatanArusEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        //SET UP FIREBASE DB
        db= FirebaseDatabase.getInstance("https://iseenero-b4cf2-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        helper=new FirebaseHelper(db);
        Toast.makeText(getApplicationContext(),"Data " + db, Toast.LENGTH_LONG).show();

        //DISPLAY LISTVIEW
        listView = (ListView) findViewById(R.id.listView);
        sungaiAdapter = new SungaiAdapter(MainActivity.this, helper.retrieve());
        listView.setAdapter(sungaiAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayInputDialog();
            }
        });

        
    }

    //DISPLAY INPUT DIALOG
    private void displayInputDialog()
    {
        Dialog d=new Dialog(this);
        d.setTitle("Save To Firebase");
        d.setContentView(R.layout.input_dialog);

        namaSungaiEditText = (EditText) d.findViewById(R.id.namaSungaiEditText);
        statusWaspadaEditText= (EditText) d.findViewById(R.id.statusWaspadaEditText);
        ketinggianEditText= (EditText) d.findViewById(R.id.ketinggianEditText);
        pHEditText= (EditText) d.findViewById(R.id.pHEditText);
        kecepatanArusEditText= (EditText) d.findViewById(R.id.kecepatanArusEditText);
        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);

        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET DATA
                String namaSungai = namaSungaiEditText.getText().toString();
                String statusWaspada = statusWaspadaEditText.getText().toString();
                int ketinggian = Integer.parseInt(ketinggianEditText.getText().toString());
                double pH = Integer.parseInt(pHEditText.getText().toString());
                int kecepatanArus = Integer.parseInt(kecepatanArusEditText.getText().toString());


                //SET DATA
                Sungai sungai = new Sungai();
                sungai.setNamaSungai(namaSungai);
                sungai.setStatusWaspada(statusWaspada);
                sungai.setKetinggian(ketinggian);
                sungai.setpH(pH);
                sungai.setKecepatanArus(kecepatanArus);

                //SIMPLE VALIDATION
                if(namaSungai != null && namaSungai.length()>0)
                {
                    //THEN SAVE
                    if(helper.save(sungai))
                    {
                        //IF SAVED CLEAR EDITTXT
                        namaSungaiEditText.setText("");
                        statusWaspadaEditText.setText("");
                        ketinggianEditText.setText("");
                        pHEditText.setText("");
                        kecepatanArusEditText.setText("");

                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Nama Sungai tidak boleh kosong!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        d.show();
    }

}