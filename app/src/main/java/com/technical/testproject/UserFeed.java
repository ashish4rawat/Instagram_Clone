package com.technical.testproject;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;
import java.util.Objects;


public class UserFeed extends AppCompatActivity {
        LinearLayout linearLayout;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_user_feed);

                  linearLayout = (LinearLayout)findViewById(R.id.linearLayout);


                String user = getIntent().getStringExtra("username");
                setTitle(user+"'s Wall");

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Image");
                query.whereEqualTo("username",user);
                query.orderByDescending("createdAt");

                query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {

                                if(e==null){
                                        Log.i("NumberOfImages",objects.size()+"");

                                        if(objects.size()>0){

                                                for(ParseObject object : objects){

                                                        ParseFile file = (ParseFile)object.get("image");
                                                        file.getDataInBackground(new GetDataCallback() {
                                                                @Override
                                                                public void done(byte[] data, ParseException e) {

                                                                       if(e==null && data!=null){


                                                                               Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);

                                                                               ImageView imgView = new ImageView(UserFeed.this);
                                                                               imgView.setLayoutParams(new ViewGroup.LayoutParams(

                                                                                       ViewGroup.LayoutParams.MATCH_PARENT,
                                                                                       ViewGroup.LayoutParams.MATCH_PARENT

                                                                               ));

                                                                               imgView.setImageBitmap(bitmap);
                                                                               linearLayout.addView(imgView);


                                                                       }

                                                                }
                                                        });

                                                }

                                        }

                                }
                                else{

                                                e.printStackTrace();
                                }

                        }
                });








        }
}
