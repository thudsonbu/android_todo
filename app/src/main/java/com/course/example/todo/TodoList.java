package com.course.example.todo;

import android.speech.tts.TextToSpeech;
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

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Locale;

public class TodoList extends AppCompatActivity implements AdapterView.OnItemClickListener, TextToSpeech.OnInitListener {

    public ArrayList<Todo> todoArray = new ArrayList();
    public CustomAdapter todoAdapter;
    public EditText todoInput;
    public ListView todoList;
    public String fileName = "list.txt";
    public String path;
    public TextToSpeech speaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list);
        todoList = findViewById(R.id.list);
        todoInput = findViewById(R.id.todo_input);

        todoList.setOnItemClickListener(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);

        todoAdapter = new CustomAdapter(this, todoArray);
        todoList.setAdapter(todoAdapter);
        actionBar.setDisplayUseLogoEnabled(false);

        path = getFilesDir().toString();
        try {
            if (new File(path + "/" + fileName).exists()) {
                readTodos();
                todoAdapter.notifyDataSetChanged();
            }
        } catch (Exception e){
            Log.e("TodoList", e.getMessage());
        }

        speaker = new TextToSpeech(this, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = speaker.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TodoList", "Invalid language");
            }
        } else {
            Log.e("TodoList", "Failed to initialize text to speech");
        }
    }

    @Override
    public void onDestroy() {
        if (speaker != null) {
            speaker.stop();
            speaker.shutdown();
        }
        super.onDestroy();
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
        OutputStreamWriter out;
        String todos = "";

        try {
            if ( new File(path + "/" + fileName).exists()) {
                File ref = new File(path + "/" + fileName);
                ref.delete();
            }

            // create todo string
            for (Todo todo : todoArray) {
                todos += todo.getText() + " \n";
            }

            out = new OutputStreamWriter(openFileOutput(fileName , MODE_PRIVATE));

            out.write(todos);

            out.close();

            Toast.makeText(this, "List Saved Successfully", Toast.LENGTH_SHORT).show();
            Log.e("TodoList", "Saved List");

        } catch (IOException e) {

            Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
            Log.e("TodoList", e.getMessage());
        }
    }

    // read todos from 'list.txt' file
    public void readTodos() {
        InputStream inStream;
        InputStreamReader inStreamReader;
        BufferedReader bufferedReader;

        try {
            inStream = openFileInput(fileName);
            inStreamReader = new InputStreamReader(inStream);
            bufferedReader = new BufferedReader(inStreamReader);
            String todo;

            while((todo = bufferedReader.readLine()) != null) {
                todoArray.add(new Todo(todo));
            }

            bufferedReader.close();

        } catch (IOException e) {

            Log.e("TodoList", e.getMessage());
        }
    }

    // text to speech
    public void speak(String output) {
        speaker.speak(output, TextToSpeech.QUEUE_FLUSH, null, "id 0");
    }


    // menu handler
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add:
                if (todoInput.getText().toString().length() > 0) {
                    String todo = todoInput.getText().toString();
                    todoArray.add(new Todo(todo));
                    todoAdapter.notifyDataSetChanged();
                    todoInput.getText().clear();
                    Toast.makeText(this, "Added Todo", Toast.LENGTH_SHORT).show();
                    speak("Adding " + todo);
                }
               return true;

            case R.id.update:
                if ( todoInput.getText().toString().length() > 0) {
                    String todo = todoInput.getText().toString();
                    todoArray.add(new Todo(todo));
                    todoAdapter.notifyDataSetChanged();
                    todoInput.getText().clear();
                    Toast.makeText(this, "Updated Todo", Toast.LENGTH_SHORT).show();
                    speak("Updated " + todo);
                }
                return true;

            case R.id.delete:
                if (todoInput.getText().toString().length() > 0) {
                    String todo = todoInput.getText().toString();
                    todoInput.getText().clear();
                    Toast.makeText(this, "Deleted Todo", Toast.LENGTH_SHORT).show();
                    speak("Deleting " + todo);
                }
                return true;

            case R.id.save:
                saveTodos();
                return true;

            case R.id.close:
                saveTodos();
                this.finish();
                return true;

            default:
                return true;
        }
    }
}
