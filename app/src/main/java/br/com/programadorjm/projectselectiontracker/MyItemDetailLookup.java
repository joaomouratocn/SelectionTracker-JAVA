package br.com.programadorjm.projectselectiontracker;

import android.view.MotionEvent;
import android.view.View;

import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

public class MyItemDetailLookup extends ItemDetailsLookup<Long> {
    RecyclerView recyclerView;

    public MyItemDetailLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Override
    public ItemDetails<Long> getItemDetails(MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null){
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            return ((MainAdapter.MainViewHolder) viewHolder).getItemDetail();
        }
        return null;
    }
}
