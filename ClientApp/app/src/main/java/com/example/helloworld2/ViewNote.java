package com.example.helloworld2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ViewNote extends AppCompatActivity {
    EditText title;
    EditText content;
    Button back, save,delete;
    Intent intent;
    NotesDB notesDB;
    NoteClass note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);

        intent = getIntent();
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        back = (Button) findViewById(R.id.back);
        save = (Button) findViewById(R.id.saveBtn) ;
        delete = (Button)findViewById(R.id.deleteBtn);
        final String old_title = intent.getStringExtra("title");
        NoteClass note = new NoteClass();
        NotesDB notesDB = new NotesDB(this);
        note = notesDB.getNote(old_title);
        title.setText(note.getTitle());
        content.setText(note.getContent());


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newTitle =title.getText().toString();

                nowSave(v);


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText new_name=(EditText) findViewById(R.id.title);
                EditText new_content=(EditText)findViewById(R.id.content);


                String new_Name=new_name.getText().toString();
                String new_Content=new_content.getText().toString();


                notesDB.onDelete(new_Name,new_Content);
                Intent mainintent = new Intent(ViewNote.this, ListReservations.class);
                startActivity(mainintent);



            }
        });
    }

    public void back(View v)
    {
        setResult(RESULT_OK,intent);
        finish();
    }

    public void nowSave(View v){

        EditText title = (EditText) findViewById(R.id.title);
        EditText new_content = (EditText) findViewById(R.id.content);

        String Title =title.getText().toString();
        String new_Content = new_content.getText().toString();


        notesDB.upgradeNote(Title,new_Content);




    }

}