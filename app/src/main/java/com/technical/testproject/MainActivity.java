package com.technical.testproject;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

import static com.technical.testproject.R.id.imageView;


public class MainActivity extends AppCompatActivity {

        EditText usernameText,passwordText;
        Button signupButton;
        TextView loginTextView;
        boolean signupValue = true;   // false means  make sign up button  login
                                        // true means first scree
        Intent intent;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                 usernameText = (EditText)findViewById(R.id.usernameText);
                 passwordText = (EditText)findViewById(R.id.passwordText);
                  signupButton = (Button)findViewById(R.id.signUpButton);
                 loginTextView = (TextView)findViewById(R.id.loginTextView);

                if(ParseUser.getCurrentUser()!=null){

                        intent = new Intent(MainActivity.this,UsersList.class);
                        startActivity(intent);

                }


                passwordText.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View view, int i, KeyEvent keyEvent) {

                                if(i==KeyEvent.KEYCODE_ENTER  && keyEvent.getAction()==keyEvent.ACTION_DOWN){
                                        signUp(view);
                                }
                                return false;
                        }
                });



                ParseAnalytics.trackAppOpenedInBackground(getIntent());


        }

        public void signUp(View view){


                if(usernameText.getText().toString().matches("")  ||  passwordText.getText().toString().matches(""))
                {
                        Toast.makeText(MainActivity.this,"Please Enter the Details",Toast.LENGTH_LONG).show();

                }
                else{



                                if(signupValue){


                                        ParseUser ser = new ParseUser();

                                        ser.setUsername(usernameText.getText().toString());
                                        ser.setPassword(passwordText.getText().toString());

                                        ser.signUpInBackground(new SignUpCallback() {
                                                @Override
                                                public void done(ParseException e) {

                                                        if(e==null){

                                                                Log.i("Sign up","Successful");
                                                                 intent = new Intent(MainActivity.this,UsersList.class);
                                                                startActivity(intent);

                                                        }
                                                        else{

                                                                Log.i("Sign up","Failed");
                                                                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                                                        }

                                                }
                                        });


                                }
                                else {


                                        ParseUser.logInInBackground(usernameText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
                                                @Override
                                                public void done(ParseUser user, ParseException e) {

                                                        if(e==null){

                                                                Log.i("Login","Successful");
                                                                 intent = new Intent(MainActivity.this,UsersList.class);
                                                                startActivity(intent);
                                                        }
                                                        else{

                                                                Log.i("Login","Failed");
                                                                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                                                        }

                                                }
                                        });


                                }










                }





        }

        public void login(View view){

                if(signupValue==false){

                        signupButton.setText("Sign Up");
                        signupValue = true;
                        loginTextView.setText("Ok, Login");

                }
                else {
                        signupButton.setText("LogIn");
                        signupValue = false;
                        loginTextView.setText("Ok, Sign Up");

                }
        }

        public void disappearKeyboard(View v){

                InputMethodManager inputman = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputman.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);



        }



}
