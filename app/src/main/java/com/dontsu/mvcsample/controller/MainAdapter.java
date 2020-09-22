package com.dontsu.mvcsample.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dontsu.mvcsample.R;
import com.dontsu.mvcsample.model.Person;
import com.dontsu.mvcsample.view.MainActivity;
import com.dontsu.mvcsample.view.MainViewHolder;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {
    public static final String TAG = MainAdapter.class.getName();

    private List<Person> items = new ArrayList<>();
    private MainViewHolder.HolderClickListener holderClickListener;

    public MainAdapter(MainViewHolder.HolderClickListener holderClickListener) {
        Timber.d("실행순서 : MainAdapter 생성자");
        this.holderClickListener = holderClickListener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.view_main, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.setPerson(items.get(position));
        holder.setOnHolderClickListener(holderClickListener);
    }

    public List<Person> getItems() {
        return items;
    }

    //MainActivity에서 입력받은 List를 넣어주기
    public void setItems(List<Person> items) {
        this.items = items;
        notifyDataSetChanged(); //변경사항 알림
    }
}
