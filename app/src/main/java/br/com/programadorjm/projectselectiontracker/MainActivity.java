package br.com.programadorjm.projectselectiontracker;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MainAdapter mainAdapter;
    private ActionMode actionMode;
    private SelectionTracker<Long> selectionTracker;
    private MenuItem itemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.main_recycleview);
        mainAdapter = new MainAdapter(getList());
        recyclerView.setAdapter(mainAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        selectionTracker = new SelectionTracker.Builder<>(
                "selection_tracker",
                recyclerView,
                new MyItemKeyProvider(mainAdapter.modelList),
                new MyItemDetailLookup(recyclerView),
                StorageStrategy.createLongStorage()
        ).build();
        mainAdapter.setSelectionTracker(selectionTracker);
        selectionTracker.addObserver(new SelectionTracker.SelectionObserver<Long>() {
            @Override
            public void onItemStateChanged(Long key, boolean selected) {
                super.onItemStateChanged(key, selected);
            }

            @Override
            public void onSelectionRefresh() {
                super.onSelectionRefresh();
            }

            @Override
            public void onSelectionChanged() {
                super.onSelectionChanged();
                if(selectionTracker.hasSelection() && actionMode == null){
                    actionMode = startSupportActionMode(MyActionMode);
                    itemCount.setTitle(String.valueOf(selectionTracker.getSelection().size()));
                }else if(!selectionTracker.hasSelection() && actionMode != null){
                    actionMode.finish();
                    actionMode = null;
                }else {
                    itemCount.setTitle(String.valueOf(selectionTracker.getSelection().size()));
                }
            }

            @Override
            public void onSelectionRestored() {
                super.onSelectionRestored();
            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        selectionTracker.onSaveInstanceState(outState);
    }

    private final ActionMode.Callback MyActionMode = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_selected_items, menu);
            itemCount = menu.findItem(R.id.item_count);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.item_delete){
                List<Model> modelsForBeDeleted = new ArrayList<>();

                //Deletar items do selectedTracker.getSelection()


                for (Long modelId : selectionTracker.getSelection()){
                    for (Model model : mainAdapter.getModelList()) {
                        if (modelId == model.getId()){
                            modelsForBeDeleted.add(model);
                        }
                    }
                }
                mainAdapter.getModelList().removeAll(modelsForBeDeleted);
                mainAdapter.notifyDataSetChanged();
                selectionTracker.clearSelection();

                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            selectionTracker.clearSelection();
        }
    };
    private List<Model> getList(){
        List<Model> modelList = new ArrayList<>();

        for(int i=0; i<17; i++){
            modelList.add(Model.ModelBuilder.modelBuilder()
                    .setId(i)
                    .setTitle("Model "+i)
                    .build());
        }
        return modelList;
    }
}