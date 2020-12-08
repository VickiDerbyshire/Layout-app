package com.example.helloworld2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    TextView user_name2;
    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        user_name2=(TextView)findViewById(R.id.titleUserName);
        Intent intent = getIntent();
        user_name = intent.getStringExtra("username");

        ///////////////////////////////////////////

        user_name2.setText(user_name);

    }

    public void createLayout(View view) {
    }

    public void selectLayout(View view) {
        Intent intent = new Intent(this,TableLayout1.class);
        startActivity(intent);
    }

    public void tableReserv(View view) {
        Intent intent = new Intent(this,TableLayout1.class);
        startActivity(intent);
    }

    public void menu(View view) {
        Intent intent = new Intent(this,MenuList.class);
        startActivity(intent);
    }

    public void orderList(View view) {
        Intent intent = new Intent(this,TableOrders.class);
        startActivity(intent);
    }
}