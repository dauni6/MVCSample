package com.dontsu.mvcsample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dontsu.mvcsample.controller.MainAdapter;
import com.dontsu.mvcsample.model.Database;
import com.dontsu.mvcsample.model.Person;
import com.dontsu.mvcsample.view.MainViewHolder;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements MainViewHolder.HolderClickListener {
    public static final String TAG = MainActivity.class.getName();

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MainAdapter adapter;
    Database database = Database.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MainAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setItems(database.getPersonList());
        database.setOnDatabaseListener(new Database.DatabaseListener() {
            @Override
            public void onChanged() {
                adapter.setItems(database.getPersonList());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Add");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        database.add(new Person(System.currentTimeMillis(), String.format("New Person %d", new Random().nextInt(1000))));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteClick(Person person) {
        database.remove(person);
    }
}
