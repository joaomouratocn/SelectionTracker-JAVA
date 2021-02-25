package br.com.programadorjm.projectselectiontracker;

import android.view.MotionEvent;

import androidx.recyclerview.selection.ItemDetailsLookup;

public class MyItemDetail extends ItemDetailsLookup.ItemDetails<Long> {
    private final Model itemSelected;
    private final int adapterPosition;

    public MyItemDetail(Model itemSelected, int adapterPosition) {
        this.itemSelected = itemSelected;
        this.adapterPosition = adapterPosition;
    }

    @Override
    public int getPosition() {
        return adapterPosition;
    }

    @Override
    public Long getSelectionKey() {
        return (long) itemSelected.getId();
    }

}
