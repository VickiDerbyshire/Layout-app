package com.example.helloworld2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MenuList extends AppCompatActivity {
    MySQLiteHelper mydb = null;
    public ListView list;
    Button btnAddingNote;
    List<String> listNote;
    ArrayAdapter<String> array;

    ListView myList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        myList = (ListView) findViewById(R.id.list);


        mydb = new MySQLiteHelper(this, null, null, 1);
        listNote = mydb.display();


        list = (ListView) findViewById(R.id.list);


        array = new ArrayAdapter<>(this,  android.R.layout.simple_list_item_1, listNote);
        list.setAdapter(array);
    }

    public void newTask(View view) {
        Intent i = new Intent(this, AddItem.class);
        startActivity(i);
    }
}