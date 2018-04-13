package com.example.igory.notes20;

import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.igory.notes20.ListView.CustomAdapter;
import com.example.igory.notes20.ListView.ListItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ItemFragment extends android.support.v4.app.Fragment {

    public final static String TAG = "ItemFragment";
    private static final String LOG_TAG = "Main";

    public ItemFragment() {
    }

    RecyclerView recyclerView;
    CustomAdapter adapter;

    List<ListItem> items;

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        items = new ArrayList<>();
        adapter = new CustomAdapter(this, items);

        setRetainInstance(true);

        dbHelper = new DBHelper(getContext());
        db = dbHelper.getReadableDatabase();
        dbHelper.onCreate(db);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sort, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        db = dbHelper.getReadableDatabase();

        if (item.getItemId() == R.id.namesUp) {
            Cursor c = db.query("notes", null, null, null,
                    null, null, Constants.head);

            if (c.moveToFirst()) {
                int i;

                List<ListItem> newItems = new ArrayList<>();

                do {
                    ListItem item1 = new ListItem(c.getString(c.getColumnIndex(Constants.head)),
                            c.getString(c.getColumnIndex(Constants.description)),
                            c.getInt(c.getColumnIndex(Constants.color)),
                            c.getString(c.getColumnIndex(Constants.date)));

                    i = 0;
                    for (; ; i++) {
                        if (items.get(i).equals(item1)) {
                            break;
                        }
                    }

                    newItems.add(items.get(i));

                } while (c.moveToNext());

                items = newItems;

                adapter = new CustomAdapter(this, items);
                recyclerView.setAdapter(adapter);
            }

            c.close();
        } else if (item.getItemId() == R.id.nameDown) {
            Cursor c = db.query("notes", null, null, null, null, null,
                    Constants.head + " DESC");

            if (c.moveToFirst()) {
                int i;

                List<ListItem> newItems = new ArrayList<>();

                do {
                    ListItem item1 = new ListItem(c.getString(c.getColumnIndex(Constants.head)),
                            c.getString(c.getColumnIndex(Constants.description)),
                            c.getInt(c.getColumnIndex(Constants.color)),
                            c.getString(c.getColumnIndex(Constants.date)));

                    i = 0;
                    for (; ; i++) {
                        if (items.get(i).equals(item1)) {
                            break;
                        }
                    }

                    newItems.add(items.get(i));

                } while (c.moveToNext());

                items = newItems;

                adapter = new CustomAdapter(this, items);
                recyclerView.setAdapter(adapter);
            }

            c.close();
        } else if (item.getItemId() == R.id.dateUp) {
            Cursor c = db.query("notes", null, null, null,
                    null, null, Constants.date);

            if (c.moveToFirst()) {
                int i;

                List<ListItem> newItems = new ArrayList<>();

                do {

                    ListItem item1 = new ListItem(c.getString(c.getColumnIndex(Constants.head)),
                            c.getString(c.getColumnIndex(Constants.description)),
                            c.getInt(c.getColumnIndex(Constants.color)),
                            c.getString(c.getColumnIndex(Constants.date)));

                    i = 0;
                    for (; ; i++) {
                        if (items.get(i).equals(item1)) {
                            break;
                        }
                    }

                    newItems.add(items.get(i));

                } while (c.moveToNext());

                items = newItems;

                adapter = new CustomAdapter(this, items);
                recyclerView.setAdapter(adapter);
            }

            c.close();
        } else if (item.getItemId() == R.id.dateDown) {
            Cursor c = db.query("notes", null, null, null, null, null,
                    Constants.date + " DESC");

            if (c.moveToFirst()) {
                int i;

                List<ListItem> newItems = new ArrayList<>();

                do {
                    ListItem item1 = new ListItem(c.getString(c.getColumnIndex(Constants.head)),
                            c.getString(c.getColumnIndex(Constants.description)),
                            c.getInt(c.getColumnIndex(Constants.color)),
                            c.getString(c.getColumnIndex(Constants.date)));

                    i = 0;
                    for (; ; i++) {
                        if (items.get(i).equals(item1)) {
                            break;
                        }
                    }

                    newItems.add(items.get(i));

                } while (c.moveToNext());

                items = newItems;

                adapter = new CustomAdapter(this, items);
                recyclerView.setAdapter(adapter);
            }

            c.close();
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateList(Bundle bundle) {
        String head = bundle.getString(Constants.head);
        String description = bundle.getString(Constants.description);
        String date = bundle.getString(Constants.date);

        int color = bundle.getInt(Constants.color);
        int position = bundle.getInt(Constants.position);

        ListItem newItem;

        ContentValues cv = new ContentValues();

        cv.put(Constants.head, head);
        cv.put(Constants.description, description);
        cv.put(Constants.color, color);
        cv.put(Constants.date, date);

        if (position == -1) {
            newItem = new ListItem(head,
                    description,
                    color,
                    date);

            items.add(newItem);

            db = dbHelper.getWritableDatabase();

            db.insert("notes", null, cv);
        } else {
            newItem = items.get(position);

            newItem.setHead(head);
            newItem.setDescription(description);
            newItem.setColor(color);

            db = dbHelper.getWritableDatabase();

            if (db.update(Constants.db, cv, Constants.id + "=" + String.valueOf(position + 1),
                    null) == 0) {
                Toast toast = Toast.makeText(this.getContext(), "Не удалось обновить БД",
                        Toast.LENGTH_LONG);
                toast.show();
            }


        }

        recyclerView.setAdapter(adapter);


        Cursor c = db.query("notes", null, null, null, null, null, null);

        if (c.moveToFirst()) {

            int idColIndex = c.getColumnIndex("id");
            int headColIndex = c.getColumnIndex(Constants.head);
            int descColIndex = c.getColumnIndex(Constants.description);
            int colorColIndex = c.getColumnIndex(Constants.color);
            int dateColIndex = c.getColumnIndex(Constants.date);

            do {
                Log.d(LOG_TAG,
                        "ID = " + c.getInt(idColIndex) +
                                ", head = " + c.getString(headColIndex) +
                                ", des = " + c.getString(descColIndex) +
                                ", color = " + c.getString(colorColIndex) +
                                ", date = " + c.getString(dateColIndex));
            } while (c.moveToNext());
        } else
            Log.d(LOG_TAG, "0 rows");
        c.close();
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

        dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE " + Constants.db);

        dbHelper.close();
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Bundle bundle);
    }
}

class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS notes ("
                + "id integer primary key autoincrement,"
                + "head text,"
                + "description text,"
                + "color integer,"
                + "date text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
