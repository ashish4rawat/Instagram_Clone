package com.technical.testproject;

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
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsersList extends AppCompatActivity {
        ListView usersListView;
        ArrayList<String> users;
        List<ParseObject>  list;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_users_list);

                 usersListView = (ListView)findViewById(R.id.userListView);
                 users = new ArrayList<>();
                final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,users);

                ParseQuery<ParseUser> query  =  ParseUser.getQuery();

                query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
                query.addAscendingOrder("username");
                query.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {
                                if(e==null){



                                                for(ParseUser user : objects){

                                                        users.add(user.getUsername());

                                                }

                                        usersListView.setAdapter(adapter);


                                }
                                else{
                                        Log.i("Info",e.getMessage());

                                }
                        }
                });

                usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                                Intent intent = new Intent(UsersList.this,UserFeed.class);
                                intent.putExtra("username",users.get(i));
                                startActivity(intent);


                        }
                });





        }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.main_menu,menu);



                return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

                if(item.getItemId() == R.id.uploadImage){

                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent,1);

                }

                if(item.getItemId() == R.id.logout){

                        ParseUser.getCurrentUser().logOut();
                        Intent toMain = new Intent(this,MainActivity.class);
                        startActivity(toMain);

                }


                return super.onOptionsItemSelected(item);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);


                if(requestCode==1 && resultCode==RESULT_OK && data!=null){

                        Uri selectedImage = data.getData();

                        try{
                                Bitmap imagBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                                Log.i("Info","Image Recived");

                                ByteArrayOutputStream Ostream = new ByteArrayOutputStream();
                                imagBitmap.compress(Bitmap.CompressFormat.PNG,100,Ostream);
                                byte[] byteArray = Ostream.toByteArray();



                                ParseFile file = new ParseFile("image.png",byteArray);

                                ParseObject obj = new ParseObject("Image");
                                obj.put("image",file);
                                obj.put("username",ParseUser.getCurrentUser().getUsername());

                                obj.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                                if(e==null){

                                                        Toast.makeText(UsersList.this,"Image Shared",Toast.LENGTH_LONG).show();
                                                }
                                                else{
                                                        Toast.makeText(UsersList.this,"Image Could Not Be Shared",Toast.LENGTH_LONG).show();


                                                }
                                        }
                                });











                        }
                        catch (Exception e){
                                e.printStackTrace();
                        }



                }


        }


}
