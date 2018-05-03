package com.ccs.app.note.activity.base;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ccs.app.note.R;
import com.ccs.app.note.custom.adapter.base.ListAdapter;
import com.ccs.app.note.model.base.ListModel;

import java.util.List;

public abstract class ListFragment<Item,
        Model extends ListModel<Item>,
        LA extends ListAdapter<Item, ?>>
        extends BaseFragment<Model> {

    protected RecyclerView listView;

    protected RecyclerView.LayoutManager layoutManager;

    protected LA listAdapter;

    protected int divider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layoutManager = onCreateLayoutManager();
        listAdapter = onCreateListAdapter();
        divider = onCreateDivider();

        model.items.observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                if (items != null) updateListAdapter(items);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.list_view);
        updateListView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        listView.setLayoutManager(null);
        listView.setAdapter(null);
        listView = null;
    }

    // abstract
    protected abstract RecyclerView.LayoutManager onCreateLayoutManager();

    protected abstract LA onCreateListAdapter();

    protected abstract int onCreateDivider();

    // update
    protected void updateListAdapter(@NonNull List<Item> items) {
        Log.d(getClass().getSimpleName(), "updateListAdapter");
        listAdapter.setItems(items);
        listAdapter.notifyDataSetChanged();
    }

    protected void updateListView() {
        Log.d(getClass().getSimpleName(), "updateListView");
        listView.setPadding(divider, divider, divider, divider);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(listAdapter);
    }

}