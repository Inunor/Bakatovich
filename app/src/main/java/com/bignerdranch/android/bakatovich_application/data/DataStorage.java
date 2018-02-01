package com.bignerdranch.android.bakatovich_application.data;

import android.graphics.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DataStorage {
    private static DataStorage dataStorage;
    private LinkedList<Item> data;

    private DataStorage() {
        data = new LinkedList<>();
        generateData();
    }

    private void generateData() {
        data.clear();
        final Random rnd = new Random();
        for (int i = 0; i < 1000; i++) {
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            data.add(new Item(color, String.valueOf(color)));
        }
    }

    public Item pushFront() {
        final Random rnd = new Random();
        final int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        final Item item = new Item(color, String.valueOf(color));
        data.addFirst(item);
        return item;
    }

    public static DataStorage get() {
        if (dataStorage == null) {
            dataStorage = new DataStorage();
        }
        return dataStorage;
    }

    public List<Item> getData() {
        return data;
    }
}
