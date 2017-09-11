package com.technical.testproject;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.Parse;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class StarterApplication extends Application {

        @Override
        public  void onCreate() {
                super.onCreate();


                Parse.enableLocalDatastore(this);

                Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                        .applicationId("89eee8740f5aad0582bea8590b4f4e8b71e38d19")
                        .clientKey("6bcb6d11c2172e02fd529c5caa53fb6a068148ff")
                        .server("http://ec2-13-126-85-229.ap-south-1.compute.amazonaws.com:80/parse/")
                        .build()
                );




                // ParseUser.enableAutomaticUser();

                ParseACL defaultACL = new ParseACL();
                defaultACL.setPublicReadAccess(true);
                defaultACL.setPublicWriteAccess(true);
                ParseACL.setDefaultACL(defaultACL, true);


        }


}
