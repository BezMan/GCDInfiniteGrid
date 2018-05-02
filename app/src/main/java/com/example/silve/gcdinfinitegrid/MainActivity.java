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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        final List<GridItemModel> allItems = GridItemModel.addItemsToList(40);
        final MyGridAdapter adapter = new MyGridAdapter(allItems);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, GRID_COLUMN_COUNT);
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
        Toast.makeText(this, "" + id, Toast.LENGTH_SHORT).show();

    }
}