package com.example.igory.notes20;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.igory.notes20.ListView.CustomAdapter;
import com.example.igory.notes20.ListView.ListItem;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends android.support.v4.app.Fragment {

    public final static String TAG = "ItemFragment";

    private static final String ARG_COLUMN_COUNT = "column-count";

    public ItemFragment() {
    }

    RecyclerView recyclerView;
    CustomAdapter adapter;

    List<ListItem> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        items = new ArrayList<>();
        adapter = new CustomAdapter(this, items);

        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        recyclerView = view.findViewById(R.id.list);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(adapter);

        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fragmentManager = getActivity()
                        .getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();

                AddFragment addFragment = new AddFragment();
                fragmentTransaction.replace(R.id.main, addFragment, AddFragment.TAG)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(AddFragment.TAG)
                        .commit();
                fragmentManager.executePendingTransactions();
            }
        });

        return view;
    }

    public void updateList(Bundle bundle) {
        String head = bundle.getString(Constants.head);
        String description = bundle.getString(Constants.description);
        String date = bundle.getString(Constants.date);
        int color = bundle.getInt(Constants.color);
        int position = bundle.getInt(Constants.position);

        ListItem newItem;

        if (position == -1) {
            newItem = new ListItem(head,
                    description,
                    color,
                    date);

            items.add(newItem);
        } else {
            newItem = items.get(position);

            newItem.setHead(head);
            newItem.setDescription(description);
            newItem.setColor(color);
        }

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Bundle bundle);
    }
}
