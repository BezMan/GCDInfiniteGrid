package com.example.silve.gcdinfinitegrid;

import java.util.ArrayList;
import java.util.List;

public class GridItemModel {
    private int mNum;

    public GridItemModel(int num) {
        mNum = num;
    }

    public int getNum() {
        return mNum;
    }

    private static int lastItemId = 0;

    public static List<GridItemModel> addItemsToList(int numItems) {
        List<GridItemModel> itemList = new ArrayList<GridItemModel>();

        for (int i = 1; i <= numItems; i++) {
            itemList.add(new GridItemModel(++lastItemId));
        }

        return itemList;
    }
}