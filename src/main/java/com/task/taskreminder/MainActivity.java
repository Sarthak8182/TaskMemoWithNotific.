package com.task.taskreminder;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.taskreminder.R;


public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Databasesqlite mDbHelper;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Task Reminder");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.orange)));
        list = findViewById(R.id.commentlist);


        mDbHelper = new Databasesqlite(this);
        db = mDbHelper.getWritableDatabase();
        final ImageView alarmImage = findViewById(R.id.alarmImage);

        String[] from = {mDbHelper.TITLE, mDbHelper.DETAIL, mDbHelper.TYPE, mDbHelper.TIME, mDbHelper.DATE};
        final String[] column = {mDbHelper.C_ID, mDbHelper.TITLE, mDbHelper.DETAIL, mDbHelper.TYPE, mDbHelper.TIME, mDbHelper.DATE};
        int[] to = {R.id.title, R.id.Detail, R.id.type, R.id.time, R.id.date};

        final Cursor cursor = db.query(mDbHelper.TABLE_NAME, column, null, null, null, null, null);
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list, cursor, from, to, 0);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View view, int position,
                                    long id) {
                Intent intent = new Intent(MainActivity.this, ViewNote.class);
                intent.putExtra(getString(R.string.rodId), id);
                startActivity(intent);
            }

        });

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_new:
                Intent openCreateNote = new Intent(MainActivity.this, CreateTask.class);
                startActivity(openCreateNote);
                return true;
            case R.id.abt:
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Cant remember your upcoming tasks?Have a lot to do in day? No Worries!! We got you covered. Leave everything to us just save your tasks with description we will let you know when its time!\\n In case you want a notification check on notify or wanna us ring you an alar click on urgent and select an alert, Choose the date and time and we will remind you.\\n Also you can save your data as a memo which can be used as ToDo List and no interruptions from us if its a norma schedule. Also you get to see a pop up button in notification which will display a pop up on your screen with title and description of your task. Click on the notification will lead you to the app that can let you show your tasks details.  \n That's All Folks!! Enjoy.")
                .setTitle("About the app!!")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                })
                        .create();
                builder.show();



            default:
                return super.onOptionsItemSelected(item);
        }
    }
}