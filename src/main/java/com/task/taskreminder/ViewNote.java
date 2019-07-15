package com.task.taskreminder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.taskreminder.R;


public class ViewNote extends AppCompatActivity {

    SQLiteDatabase db;
    Databasesqlite dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        final long id = getIntent().getExtras().getLong(getString(R.string.row_id));

        dbHelper = new Databasesqlite(this);
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + dbHelper.TABLE_NAME + " where " + dbHelper.C_ID + "=" + id, null);
        TextView title =  findViewById(R.id.title);
        TextView detail = findViewById(R.id.detail);
        TextView notetype =  findViewById(R.id.note_type_ans);
        TextView time =  findViewById(R.id.alertvalue);
        TextView date =  findViewById(R.id.datevalue);

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                title.setText("Title:-"+cursor.getString(cursor.getColumnIndex(dbHelper.TITLE)));
                detail.setText("Details:-"+cursor.getString(cursor.getColumnIndex(dbHelper.DETAIL)));
                notetype.setText("Type"+cursor.getString(cursor.getColumnIndex(dbHelper.TYPE)));
                time.setText(cursor.getString(cursor.getColumnIndex(dbHelper.TIME)));
                date.setText(cursor.getString(cursor.getColumnIndex(dbHelper.DATE)));

            }
            cursor.close();
        }
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(this, MainActivity.class);
        startActivity(setIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final long id = getIntent().getExtras().getLong(getString(R.string.rowID));

        switch (item.getItemId()) {
            case R.id.action_back:
                Intent openMainActivity = new Intent(this, MainActivity.class);
                startActivity(openMainActivity);
                return true;
            case R.id.action_edit:

                Intent openEditNote = new Intent(ViewNote.this, EditTask.class);
                openEditNote.putExtra(getString(R.string.intent_row_id), id);
                startActivity(openEditNote);
                return true;

            case R.id.action_discard:
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewNote.this);
                builder
                        .setTitle(getString(R.string.delete_title))
                        .setMessage(getString(R.string.delete_message))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Long id = getIntent().getExtras().getLong(getString(R.string.rodID));
                                db.delete(Databasesqlite.TABLE_NAME, Databasesqlite.C_ID + "=" + id, null);
                                db.close();
                                Intent openMainActivity = new Intent(ViewNote.this, MainActivity.class);
                                startActivity(openMainActivity);

                            }
                        })
                        .setNegativeButton(getString(R.string.no), null)                        //Do nothing on no
                        .show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}