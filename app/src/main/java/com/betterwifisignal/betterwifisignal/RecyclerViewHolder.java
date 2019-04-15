package com.betterwifisignal.betterwifisignal;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    // View holder for gridview recycler view as we used in listview
    public TextView title;
    public ImageView imageview;

    public RecyclerViewHolder(View view) {
        super(view);
        // Find all views ids
        this.title = (TextView) view
                .findViewById(R.id.item_title);
        this.imageview = (ImageView) view
                .findViewById(R.id.signal_image);

    }


}
