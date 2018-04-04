package com.example.igory.notes20.ListView;

import android.app.FragmentTransaction;
import android.graphics.PorterDuff;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.igory.notes20.AddFragment;
import com.example.igory.notes20.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private List<ListItem> listData;
    private LayoutInflater layoutInflater;
    private Fragment fragment;

    public CustomAdapter(Fragment fragment, List<ListItem> listData) {
        this.listData = listData;
        this.layoutInflater = LayoutInflater.from(fragment.getContext());
        this.fragment = fragment;
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.fragment_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        ListItem item = listData.get(position);

        holder.head.setText(item.getHead());
        holder.description.setText(item.getDescription());
        holder.date.setText(item.getDate());

        holder.color.getBackground().setColorFilter(item.getColor(), PorterDuff.Mode.ADD);

        holder.head.setTag(position);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView head, description, date;
        final ImageView color;

        ViewHolder(View view) {
            super(view);
            head = view.findViewById(R.id.textView3);
            description = view.findViewById(R.id.textView4);
            date = view.findViewById(R.id.textView5);

            color = view.findViewById(R.id.imageView3);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {

                ListItem item = listData.get(position);

                AddFragment addFragment = AddFragment.newInstance(item.getHead(),
                        item.getDescription(),
                        item.getColor(),
                        position);

                android.support.v4.app.FragmentManager fragmentManager = fragment.getActivity()
                        .getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();

                fragmentTransaction.replace(R.id.main, addFragment, AddFragment.TAG)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(AddFragment.TAG)
                        .commit();
            }
        }
    }
}