package com.example.appnotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
   static ArrayList<String >notes=new ArrayList<>();
    static ListView listView;
    static  ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.text_part,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_notes)
        {
            Intent intent=new Intent(MainActivity.this,EditSection.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         listView=findViewById(R.id.listview);
         sharedPreferences=MainActivity.this.getSharedPreferences("com.example.appnotes", Context.MODE_PRIVATE);
        HashSet set=(HashSet)sharedPreferences.getStringSet("notes",null);
        if(set==null) {
            notes.add("Example: notes is fun");
        }
        else {
            notes=new ArrayList<>(set);
        }
         arrayAdapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,EditSection.class);
                intent.putExtra("noteid",position);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final int x=position;
                new AlertDialog.Builder(MainActivity.this).setIcon(android.R.drawable.stat_sys_warning)
                        .setTitle("Delete the note")
                        .setMessage("Do you really want to delete the note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(x);
                                arrayAdapter.notifyDataSetChanged();

                                HashSet set=new HashSet(notes);
                                sharedPreferences=MainActivity.this.getSharedPreferences("com.example.appnotes", Context.MODE_PRIVATE);

                                sharedPreferences.edit().putStringSet("notes",set).apply();
                            }
                        })
                        .setNegativeButton("No",null).show();
                return true;
            }
        });
    }
}
