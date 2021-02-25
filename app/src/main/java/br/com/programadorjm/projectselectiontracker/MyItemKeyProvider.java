package br.com.programadorjm.projectselectiontracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;

import java.util.List;

public class MyItemKeyProvider extends ItemKeyProvider<Long> {

    private final List<Model> modelList;

    protected MyItemKeyProvider(List<Model> modelList) {
        super(SCOPE_MAPPED);
        this.modelList = modelList;
    }

    @Override
    public Long getKey(int position) {
        return (long) modelList.get(position).getId();
    }

    @Override
    public int getPosition(Long key) {
        for (Model model : modelList){
            if ((long) model.getId() == key){ return modelList.indexOf(model);}
        }
        return 0;
    }
}
