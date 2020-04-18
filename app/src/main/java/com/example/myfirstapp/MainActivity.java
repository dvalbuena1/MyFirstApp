package com.example.myfirstapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView tittle, endpage;
    Button addBtn;

    DatabaseReference reference;
    RecyclerView ourTask;
    ArrayList<MyTasks> list;
    TasksAdapter tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tittle = findViewById(R.id.title);
        endpage = findViewById(R.id.endPage);

        addBtn = findViewById(R.id.addBtn);

        Typeface ML = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Light.ttf");
        Typeface MM = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Medium.ttf");

        tittle.setTypeface(MM);
        endpage.setTypeface(ML);

        addBtn.setTypeface(ML);


        ourTask = findViewById(R.id.ourTask);
        ourTask.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();

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
}
