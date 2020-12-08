package com.example.helloworld2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddItem extends AppCompatActivity {

    MySQLiteHelper db;

    EditText txtTaskTitle;
    EditText txtTaskDes;
    Button deleteBtn;
    long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        db = new MySQLiteHelper(this, null, null, 1);

        txtTaskTitle = (EditText) findViewById(R.id.txtTaskTitle);
        txtTaskDes = (EditText) findViewById(R.id.txtTaskDes);
        deleteBtn = (Button) findViewById(R.id.btnDelete);

    }

    public void addATask(View view) {

        ListItem listItem = new ListItem(txtTaskTitle.getText().toString(),  txtTaskDes.getText().toString());


        boolean status = db.addRecord(listItem);
        if (status) {
            Toast.makeText(this, "Task Added", Toast.LENGTH_LONG).show();
            delete(view);
        } else {
            Toast.makeText(this, "Failed to add task", Toast.LENGTH_LONG).show();
        }
    }


    public void delete(View view) {

      /*  deleteBtn.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            ((MainActivity)parent.getContext()).doCallMyOtherActivity(times.get(position));
        });

    }*/
    }


    public void goBack(View view) {
        Intent intent = new Intent(this,MenuList.class);
        startActivity(intent);
    }
}