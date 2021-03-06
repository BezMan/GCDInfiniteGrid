package com.example.silve.gcdinfinitegrid;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class MyGridAdapter extends RecyclerView.Adapter<MyGridAdapter.ViewHolder> {

    // Store a member variable for the items
    private List<GridItemModel> mItemList;
    private boolean mIsLongClicked;
    private int mNumLongClicked;

    private ItemClickListener mClickListener;

    // Pass in the items array into the constructor
    MyGridAdapter(List<GridItemModel> itemList) {
        setHasStableIds(true);
        mItemList = itemList;
    }

    public MyGridAdapter(List<GridItemModel> allItems, boolean isLongClicked, int numLongClicked) {
        setHasStableIds(true);
        mItemList = allItems;
        mIsLongClicked = isLongClicked;
        mNumLongClicked = numLongClicked;
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        GridItemModel itemModel = mItemList.get(position);

        TextView textView = viewHolder.numTextView;
        int num = itemModel.getNum();

        textView.setText(String.valueOf(num));

        if (mIsLongClicked) {
            if (GCD(mNumLongClicked, num) > 1) {
                textView.setBackgroundColor(Color.GREEN);
            }
        } else {
            textView.setBackgroundColor(Color.TRANSPARENT);
        }

        if (isPrime(num)) {
            textView.setBackgroundColor(Color.RED);

        }
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemLongClick(View view, int id);

        void onItemClick(View view, int num);
    }


    //checks whether an int is prime or not.
    private boolean isPrime(int n) {
        //check if n is a multiple of 2
        if (n%2==0) return false;
        //if not, then just check the odds
        for(int i=3;i*i<=n;i+=2) {
            if(n%i==0)
                return false;
        }
        return true;
    }


    private int GCD(int a, int b) {
        if (b == 0) return a;
        return GCD(b, a % b);
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        TextView numTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            numTextView = itemView.findViewById(R.id.item_text);

            Log.d("BEZ", "setClickListeners: ");
            numTextView.setOnClickListener(this);
            numTextView.setOnLongClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                GridItemModel itemModel = mItemList.get(getAdapterPosition());
                mClickListener.onItemClick(view, itemModel.getNum());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if (mClickListener != null) {
                GridItemModel itemModel = mItemList.get(getAdapterPosition());
                mClickListener.onItemLongClick(view, itemModel.getNum());
            }
            return true;
        }

    }


}