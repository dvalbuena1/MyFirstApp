package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tittle;
    Button addBtn;
    EditText inputText;

    DatabaseReference reference;
    Integer id = new Random().nextInt();

    RecyclerView ourTask;
    ArrayList<MyTasks> list;
    TasksAdapter tasksAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tittle = findViewById(R.id.title);

        addBtn = findViewById(R.id.addBtn);
        inputText = findViewById(R.id.inputText);

        Typeface ML = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        Typeface MM = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");

        tittle.setTypeface(MM);

        addBtn.setTypeface(ML);
        inputText.setTypeface(ML);


        ourTask = findViewById(R.id.ourTask);
        ourTask.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

        loadData();


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference().child("TaskApp")
                        .child("Task" + id);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titleTask").setValue(inputText.getText().toString());
                        dataSnapshot.getRef().child("check").setValue(false);

                        loadData();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                closeKeyBoard();
            }
        });
    }

    private void loadData(){

        list.clear();

        reference = FirebaseDatabase.getInstance().getReference().child("TaskApp");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    MyTasks t = data.getValue(MyTasks.class);
                    list.add(t);
                }
                tasksAdapter = new TasksAdapter(MainActivity.this, list);
                ourTask.setAdapter(tasksAdapter);
                tasksAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void closeKeyBoard(){
        View view = this.getCurrentFocus();
        if(view != null) {
            InputMethodManager inmm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inmm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void cleanText(){
        inputText.setText("");
    }


}
