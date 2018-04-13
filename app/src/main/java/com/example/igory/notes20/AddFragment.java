package com.example.igory.notes20;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class AddFragment extends Fragment implements View.OnClickListener {

    public final static String TAG = "AddFragment";

    private OnFragmentInteractionListener mListener;

    public AddFragment() {
    }

    public static AddFragment newInstance(String head, String description, int color, int position)
    {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();

        args.putString(Constants.head, head);
        args.putString(Constants.description, description);
        args.putInt(Constants.color, color);
        args.putInt(Constants.position, position);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        setRetainInstance(true);
    }

    EditText head;
    EditText description;
    ImageView colorImage;
    int color = 0;

    int position = -1;
    boolean use = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        head = view.findViewById(R.id.head);
        description = view.findViewById(R.id.description);
        colorImage = view.findViewById(R.id.color);

        if (getArguments() != null && !use) {
            head.setText(getArguments().getString(Constants.head));
            description.setText(getArguments().getString(Constants.description));
            color = getArguments().getInt(Constants.color);
            position = getArguments().getInt(Constants.position);

            use = true;
        } else if (color == 0) {
            Random rand = new Random();
            color = Color.argb(255, rand.nextInt(255), rand.nextInt(255),
                    rand.nextInt(255));
        }

        colorImage.setBackgroundColor(color);

        colorImage.setOnClickListener(this);

        return view;
    }

    public void SetArguments() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.head, head.getText().toString());
        bundle.putString(Constants.description, description.getText().toString());

        bundle.putInt(Constants.color, color);

        Time time = new Time(Time.getCurrentTimezone());
        time.setToNow();
        bundle.putString(Constants.date, time.format("%Y.%m.%d"));

        bundle.putInt(Constants.position, position);

        mListener.onFragmentInteraction(bundle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (head.getText().length() > 0) {
            getActivity().getSupportFragmentManager().popBackStack();

            //hide a keyboard
            InputMethodManager imm = (InputMethodManager) getActivity().getApplicationContext()
                    .getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(
                    getActivity().getWindow().getCurrentFocus().getWindowToken(), 0);

            SetArguments();
        } else {
            Toast toast = Toast.makeText(this.getContext(), "Введите заголовок",
                    Toast.LENGTH_LONG);
            toast.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void GetArguments(int color) {
        this.color = color;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        android.support.v4.app.FragmentManager fragmentManager = getFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        ChooseColorFragment chooseColorFragment = ChooseColorFragment.newInstance(color);
        fragmentTransaction.replace(R.id.main, chooseColorFragment)
                .setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(chooseColorFragment.TAG)
                .commit();
    }

    @Override
    public void onResume() {

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)
                description.getLayoutParams();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            params.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50,
                    getResources().getDisplayMetrics());

            description.setLayoutParams(params);
        } else {
            params.matchConstraintDefaultHeight = 244;

            description.setLayoutParams(params);
        }

        super.onResume();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Bundle bundle);
    }
}
