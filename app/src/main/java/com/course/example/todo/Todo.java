package com.course.example.todo;

public class Todo {
    private String text;

    public Todo(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

    public String setText(String text){
        this.text = text;
        return text;
    }
}
