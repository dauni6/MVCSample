package com.dontsu.mvcsample.model;

import java.util.ArrayList;

import timber.log.Timber;

public class Database {
    private static volatile Database instance = new Database();
    private ArrayList<Person> personList = new ArrayList<>();
    private DatabaseListener databaseListener;

    private Database() {
        Timber.d("실행순서 : Database 객체에서 생성자를 통해 리스트 만들어줌");
        for (int index = 0; index < 100; index++) {
            personList.add(new Person(index, String.format("Person%d", index)));
        }
    }

    public static Database getInstance() {
        return instance;
    }

    //입력의 역할
    public void add(Person person) {
        personList.add(0, person);
        notifyChange();
    }

    //삭제의 역할
    public void remove(Person person) {
        personList.remove(person);
        notifyChange();
    }

    //변경 알림의 역할
    private void notifyChange() {
        if (databaseListener != null) {
            databaseListener.onChanged();
        }
    }

    public void setOnDatabaseListener(DatabaseListener databaseListener) {
        this.databaseListener = databaseListener;
    }

    public ArrayList<Person> getPersonList() {
        Timber.d("실행순서 : 리스트 얻어옴");
        return personList;
    }

    public interface DatabaseListener {
        void onChanged();
    }

}
