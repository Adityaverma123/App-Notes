package com.example.appnotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.HashSet;

public class EditSection extends AppCompatActivity {
    int noteid;
    public void submit(View view)
    {
        Intent intent=new Intent(EditSection.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Edit Section");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_section);
        EditText editText=findViewById(R.id.editText);
        Intent intent=getIntent();
        noteid=intent.getIntExtra("noteid",-1);
        if(noteid!=-1)
        {
            editText.setText(MainActivity.notes.get(noteid));
        }
        else {
            MainActivity.notes.add("");
            noteid=MainActivity.notes.size()-1;
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.notes.set(noteid,String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                SharedPreferences sharedPreferences=EditSection.this.getSharedPreferences("com.example.appnotes", Context.MODE_PRIVATE);
                HashSet set=new HashSet(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes",set).apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
