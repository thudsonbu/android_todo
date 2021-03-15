package com.course.example.todo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TodoList extends AppCompatActivity {

    public ArrayList<Todo> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list);
        ListView todoList = findViewById(R.id.list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);

        arrayList = new ArrayList<Todo>();
        arrayList.add(new Todo("Example todo"));

        CustomAdapter todoAdapter = new CustomAdapter(this, arrayList);
        todoList.setAdapter(todoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add:
                return true;

            case R.id.update:
                return true;

            case R.id.delete:
                return true;

            case R.id.save:
                return true;

            case R.id.close:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
