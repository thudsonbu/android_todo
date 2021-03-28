package com.course.example.todo;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class TodoList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public ArrayList<Todo> todoArray = new ArrayList();
    public CustomAdapter todoAdapter;
    public EditText todoInput;
    public ListView todoList;
    public OutputStreamWriter out;
    public String fileName = "list.txt";
    public File saveFile;

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

//        try {
//            File ref = new File(fileName);
//            if ( ref.exists() ) {
//                readTodos();
//            }
//        } catch (Exception e){
//            Log.e( "TodoList", e.getMessage() );
//        }

        try {
            out = new OutputStreamWriter(openFileOutput(fileName, MODE_PRIVATE));
        } catch (IOException e) {
            Log.e( "TodoList", e.getMessage() );
        }
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

    // save list to 'list.txt' file
    public void saveTodos(){
        String todos = "";

        if ( todoArray.size() == 0 ) {
            Toast.makeText(this, "No Todo's to Save",
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        try {
            // delete old todos file
//            if ( saveFile.exists() ) {
//                saveFile.delete();
//            }
            
            // create todo string
            for ( Todo todo : todoArray ) {
                todos += todo.getText().toString() + " \n";
            }

            out.write(todos);

            out.close();

            Toast.makeText(this, "List Saved Successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
        }
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
                saveTodos();
                return true;

            case R.id.close:
                this.finish();
                return true;

            default:
                return true;
        }
    }
}
