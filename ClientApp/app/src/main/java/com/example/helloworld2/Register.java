package com.example.helloworld2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Register extends AppCompatActivity {

    EditText email, username, shopName, pass1, pass2;
    Button register, goback;
    Socket client;
    BufferedReader in = null;
    PrintWriter os = null;

    TextView testOutput;

    private Toast toast, toast2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = (EditText)findViewById(R.id.enteredEm);
        username= (EditText)findViewById(R.id.enteredUserName);
        shopName = (EditText)findViewById(R.id.enteredShopName);
        pass1= (EditText)findViewById(R.id.enteredPass1);
        pass2= (EditText)findViewById(R.id.enteredPass2);

        testOutput =(TextView) findViewById(R.id.outputTest);


        register= (Button)findViewById(R.id.regiAcc);
        goback= (Button)findViewById(R.id.button4);
        toast = Toast.makeText(this, "Shop already in use", Toast.LENGTH_SHORT);
    }

    public void registerignAccount(View view) {
        email = (EditText)findViewById(R.id.enteredEm);
        username= (EditText)findViewById(R.id.enteredUserName);
        shopName = (EditText)findViewById(R.id.enteredShopName);
        pass1= (EditText)findViewById(R.id.enteredPass1);
        pass2= (EditText)findViewById(R.id.enteredPass2);


        String pass11=pass1.getText().toString();
        final String pass21=pass2.getText().toString();
        String email1=email.getText().toString();
        final String username1=username.getText().toString();
        final String shopnameEntered= shopName.getText().toString();

        if ( pass11.equals("") || pass21.equals("") || email1.equals("") ||username1.equals("") ||shopnameEntered.equals("")){
            Toast.makeText(this, "Please fill out both fields", Toast.LENGTH_SHORT).show();
        } else if (pass11.equals(pass21)){
            //send into to server to create new account

            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        System.out.println("Does it get here");
                        client = new Socket("192.168.2.110", 9000);

                        //Create Input Stream
                        os = new PrintWriter(client.getOutputStream());

                        os.write("register");
                        os.write(" ");
                        os.write(username1);
                        os.write(" ");
                        os.write(pass21);
                        os.write(" ");
                        os.write(shopnameEntered);
                        os.write(" ");
                        os.write(email1);
                        os.flush();


                        in = new BufferedReader(new InputStreamReader(client.getInputStream()));


                        String received = in.readLine();
                        System.out.println("Message Received " + received);
                        testOutput.setText("Message Received " + received);

                        if(received.equals("Verified")){
                            Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_LONG).show();
                            Intent mainintent = new Intent(Register.this, MainActivity.class);
                            startActivity(mainintent);

                        }
                        if(received.equals("Denied2")){
                            toast.show();


                        }



                        os.close();
                        client.close();




                    }catch(Exception e){
                        e.printStackTrace();
                    }


                }
            };
            Thread mythread = new Thread(runnable);
            mythread.start();








        } else {
            Toast.makeText(getApplicationContext(),"Password's don't match",Toast.LENGTH_SHORT).show();
        }

    }

    public void goBack(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}