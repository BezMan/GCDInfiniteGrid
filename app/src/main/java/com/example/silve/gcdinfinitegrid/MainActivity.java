package com.example.silve.gcdinfinitegrid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MyGridAdapter.ItemClickListener {

    private final int GRID_COLUMN_COUNT = 5;
    private boolean isLongClicked;
    private int numLongClicked;
    private MyGridAdapter adapter;
    private RecyclerView recyclerView;
    private List<GridItemModel> allItems;
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        allItems = GridItemModel.addItemsToList(50);
        adapter = new MyGridAdapter(allItems, isLongClicked, numLongClicked);
        gridLayoutManager = new GridLayoutManager(this, GRID_COLUMN_COUNT);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                List<GridItemModel> moreItems = GridItemModel.addItemsToList(20);
                final int curSize = adapter.getItemCount();
                allItems.addAll(moreItems);

                view.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemRangeInserted(curSize, allItems.size() - 1);
                    }
                });
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }


    @Override
    public void onItemClick(View view, int id) {
        Toast.makeText(this, "reg click " + id, Toast.LENGTH_SHORT).show();

        if (isLongClicked) {
            switchClickMode(false);
        }

    }


    @Override
    public void onItemLongClick(View view, int id) {
        Toast.makeText(this, "long click " + id, Toast.LENGTH_SHORT).show();

        switchClickMode(true);

    }


    private void switchClickMode(boolean gotoLongClickMode) {
        isLongClicked = gotoLongClickMode;
        adapter = new MyGridAdapter(allItems, isLongClicked, numLongClicked);

//        recyclerView.setAdapter(null);
//        recyclerView.setLayoutManager(null);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        adapter.notifyDataSetChanged();

        recyclerView.swapAdapter(adapter, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (isLongClicked) {
            switchClickMode(false);
        } else {
            super.onBackPressed();
        }
    }
}