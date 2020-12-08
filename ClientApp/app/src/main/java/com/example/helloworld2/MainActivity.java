package com.example.helloworld2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {


    //public variables
    EditText usernameEntered, passwordEntered, shopIdEntered;
    TextView e1; // e1 is only there to test the output from server
    Button loginBtn;
    private Toast toast, toastIncorrectCreds, toastIncomplete;

    Socket client;
    PrintWriter os = null;
    BufferedReader in = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //only for testing
        e1=(TextView) findViewById(R.id.ViewText2);


        //declare and initialize
        usernameEntered =(EditText)findViewById(R.id.usernameLogin);
        passwordEntered =(EditText)findViewById(R.id.passwordLogin);
        shopIdEntered = (EditText)findViewById(R.id.shopIDLogIn);
        loginBtn =(Button)findViewById(R.id.loginBtn);

        //in case of exceptions and errors
        toast = Toast.makeText(this, "Incorrect Credentials", Toast.LENGTH_LONG);
        toastIncorrectCreds=Toast.makeText(this, "Incorrect Credentials", Toast.LENGTH_LONG);
        toastIncomplete=Toast.makeText(this, "Enter Username, Password, and ShopID", Toast.LENGTH_LONG);


    }

    public void myLogIn(View v){
        e1=(TextView) findViewById(R.id.ViewText2);
        e1.setText("touch1");

        usernameEntered =(EditText)findViewById(R.id.usernameLogin);
        passwordEntered =(EditText)findViewById(R.id.passwordLogin);
        shopIdEntered=(EditText)findViewById(R.id.shopIDLogIn);

        String UsernameEntered=usernameEntered.getText().toString();
        String PasswordEntered=passwordEntered.getText().toString();
        String ShopIdEntered=shopIdEntered.getText().toString();

        if (UsernameEntered.equals("")||PasswordEntered.equals("")||ShopIdEntered.equals("")){
            toastIncomplete.show();
        } else {

            Runnable runnable = new Runnable() {

                public void run() {
                    try {


                        client = new Socket("192.168.2.110", 9000);
                        e1.setText("touch2");

                        //Create Input Stream
                        os = new PrintWriter(client.getOutputStream());
                        in = new BufferedReader(new InputStreamReader(client.getInputStream()));


                        os.write("login");
                        os.write(" ");
                        os.write(usernameEntered.getText().toString());
                        os.write(" ");
                        os.write(passwordEntered.getText().toString());
                        os.write(" ");
                        os.write(shopIdEntered.getText().toString());
                        os.flush();


                        String received = in.readLine();
                        e1.setText("mssg" + received);

                        if (received.equals("Verified")) {
                            Intent mainintent = new Intent(MainActivity.this, Dashboard.class);
                            mainintent.putExtra("username", usernameEntered.getText().toString());
                            startActivityForResult(mainintent, 100);
                        }
                        if (received.equals("Denied")) {
                            toast.show();

                        }
                        if (received.equals("Denied2")) {
                            toast.show();
                        }

                        os.close();
                        client.close();

                    } catch (Exception e) {

                        e.printStackTrace();
                    }


                }
            };
            Thread mythread = new Thread(runnable);
            mythread.start();
        }
    }

    //Here we will move to the registration page
    public void RegisterNewAcc(View view) {
        //starting intent to move on to the next page
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }
}