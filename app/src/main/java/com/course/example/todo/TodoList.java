package com.course.example.todo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TodoList extends AppCompatActivity {

    public ArrayList<Todo> todoArray = new ArrayList();
    public CustomAdapter todoAdapter;
    public EditText todoInput;
    public ListView todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list);
        todoList = findViewById(R.id.list);
        todoInput = findViewById(R.id.todo_input);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayUseLogoEnabled(false);

        todoAdapter = new CustomAdapter(this, todoArray);
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
                todoArray.add(new Todo(todoInput.getText().toString()));
                todoAdapter.notifyDataSetChanged();
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
                return true;
        }
    }
}
