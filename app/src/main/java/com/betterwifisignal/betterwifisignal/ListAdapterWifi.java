package com.betterwifisignal.betterwifisignal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public class ListAdapterWifi extends RecyclerView.Adapter<RecyclerViewHolder> {// Recyclerview will extend to

    // recyclerview adapter
    private List<WifiElementModel> arrayList;
    private Context context;

    public ListAdapterWifi(Context context, List<WifiElementModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final WifiElementModel model = arrayList.get(position);

        RecyclerViewHolder mainHolder = (RecyclerViewHolder) holder;// holder
        switch (model.getStrengthRating()) {
            case 0:
                mainHolder.imageview.setImageResource(R.drawable.signal0_nv);
                break;
            case 1:
                mainHolder.imageview.setImageResource(R.drawable.signal1_nv);
                break;
            case 2:
                mainHolder.imageview.setImageResource(R.drawable.signal2_nv);
                break;
            case 3:
                mainHolder.imageview.setImageResource(R.drawable.signal3_nv);
                break;
            case 4:
                mainHolder.imageview.setImageResource(R.drawable.signal4_nv);
                break;
        }

        mainHolder.title.setText(model.getSSDI());

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.items, viewGroup, false);
        RecyclerViewHolder listHolder = new RecyclerViewHolder(mainGroup);
        return listHolder;

    }
}
