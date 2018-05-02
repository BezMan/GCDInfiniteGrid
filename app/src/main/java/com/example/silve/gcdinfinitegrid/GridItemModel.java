package com.example.silve.gcdinfinitegrid;

import java.util.ArrayList;
import java.util.List;

public class GridItemModel {
    private String mName;
    private boolean primeNum;

    public GridItemModel(String name, boolean pNum) {
        mName = name;
        primeNum = pNum;
    }

    public String getName() {
        return mName;
    }

    public boolean isPrimeNum() {
        return primeNum;
    }

    private static int lastItemId = 0;

    public static List<GridItemModel> addItemsToList(int numItems) {
        List<GridItemModel> itemList = new ArrayList<GridItemModel>();

        for (int i = 1; i <= numItems; i++) {
            itemList.add(new GridItemModel("Person " + ++lastItemId, i <= numItems / 2));
        }

        return itemList;
    }
}