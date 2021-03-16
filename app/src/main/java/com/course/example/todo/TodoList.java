package com.course.example.todo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TodoList extends AppCompatActivity implements AdapterView.OnItemClickListener {

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

        todoList.setOnItemClickListener(this);

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

    // list item handler
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        String todo = todoArray.get(position).getText();
        // the simplest way to do this is to remove it from the list while editing then add it
        // back if they press update
        todoInput.setText(todo);
        todoArray.remove(position);
        todoAdapter.notifyDataSetChanged();
    }


    // menu handler
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add:
                if ( todoInput.getText().toString().length() > 0 ) {
                    todoArray.add(new Todo(todoInput.getText().toString()));
                    todoAdapter.notifyDataSetChanged();
                    todoInput.getText().clear();
                    Toast.makeText(this, "Added Todo", Toast.LENGTH_SHORT).show();
                }
               return true;

            case R.id.update:
                if ( todoInput.getText().toString().length() > 0 ) {
                    todoArray.add(new Todo(todoInput.getText().toString()));
                    todoAdapter.notifyDataSetChanged();
                    todoInput.getText().clear();
                    Toast.makeText(this, "Updated Todo", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.delete:
                if ( todoInput.getText().toString().length() > 0 ) {
                    todoInput.getText().clear();
                    Toast.makeText(this, "Deleted Todo", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.save:
                Toast.makeText(this, "List Saved Successfully", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.close:
                this.finish();
                return true;

            default:
                return true;
        }
    }
}
