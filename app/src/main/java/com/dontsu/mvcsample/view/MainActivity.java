package com.dontsu.mvcsample.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dontsu.mvcsample.R;
import com.dontsu.mvcsample.controller.MainAdapter;
import com.dontsu.mvcsample.model.Database;
import com.dontsu.mvcsample.model.Person;

import java.util.Random;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainViewHolder.HolderClickListener {
    public static final String TAG = MainActivity.class.getName();

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MainAdapter adapter;
    Database database = Database.getInstance();
    Timber timber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timber.plant(new Timber.DebugTree());

        recyclerView = findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MainAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setItems(database.getPersonList());
        database.setOnDatabaseListener(new Database.DatabaseListener() { //콜백
        @Override
        public void onChanged() {
            //데이터베이스의 리스트를 어댑터에 넣어주기
            Timber.d("데이터 삭제에 따른 변경 알림");
            //database에 변경된 후의 리스트를 다시 adapter에 넣어줌
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
        Timber.d("실행순서 : " + adapter.getItems().size());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDeleteClick(Person person) {
        Timber.d(TAG + " : onDeleteClick()");
        database.remove(person);
    }
}
