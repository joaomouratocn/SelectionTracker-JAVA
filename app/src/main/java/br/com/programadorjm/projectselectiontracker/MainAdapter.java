package br.com.programadorjm.projectselectiontracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{
    List<Model> modelList;
    SelectionTracker<Long> selectionTracker;

    public MainAdapter(List<Model> modelList) {
        this.modelList = modelList;
    }

    public List<Model> getModelList() {
        return modelList;
    }

    public void setSelectionTracker(SelectionTracker<Long> selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_layout, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainAdapter.MainViewHolder holder, int position) {
        Model model = modelList.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder{
        ImageView check;
        TextView title;

        public MainViewHolder(View itemView) {
            super(itemView);
            check = itemView.findViewById(R.id.img_check);
            title = itemView.findViewById(R.id.txt_title);
        }

        public void bind(Model model) {
            title.setText(model.getTitle());
            if (selectionTracker.isSelected((long) model.getId())){
                itemView.setActivated(true);
                Animation translateIn = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.translate_in);
                check.startAnimation(translateIn);
                check.setVisibility(View.VISIBLE);
            }else {
                itemView.setActivated(false);
                Animation translateOut = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.translate_out);
                check.startAnimation(translateOut);
                check.setVisibility(View.INVISIBLE);
            }
        }

        public ItemDetailsLookup.ItemDetails<Long> getItemDetail() {
            return new MyItemDetail(modelList.get(getAdapterPosition()), getAdapterPosition());
        }
    }
}
