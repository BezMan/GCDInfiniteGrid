package com.example.silve.gcdinfinitegrid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ItemRecyclerAdapter extends
        RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder> {

    // Store a member variable for the items
    private List<GridItemModel> mItemList;

    // Pass in the items array into the constructor
    public ItemRecyclerAdapter(List<GridItemModel> itemList) {
        mItemList = itemList;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView numTextView;
        public FrameLayout frameLayout;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            numTextView = (TextView) itemView.findViewById(R.id.item_text);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.item_frame);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.grid_item_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        GridItemModel itemModel = mItemList.get(position);

        TextView textView = viewHolder.numTextView;
        textView.setText(itemModel.getName());

        FrameLayout frameLayout = viewHolder.frameLayout;

        if (itemModel.isPrimeNum()) {
            frameLayout.getResources().getColor(R.color.colorPrimary);
//            button.setText("Message");
//            button.setEnabled(true);
        }
        else {
            frameLayout.getResources().getColor(R.color.colorPrimaryDark);
//            button.setText("Offline");
//            button.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}