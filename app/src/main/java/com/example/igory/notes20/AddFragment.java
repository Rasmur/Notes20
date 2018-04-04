package com.example.igory.notes20;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.util.Log;
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

import java.util.Objects;
import java.util.Random;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment implements View.OnClickListener{

    public final static String TAG = "AddFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String head, String description, int color, int position) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();

        args.putString("head", head);
        args.putString("description", description);
        args.putInt("color", color);
        args.putInt("position", position);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        setRetainInstance(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
            head.setText(getArguments().getString("head"));
            description.setText(getArguments().getString("description"));
            color = getArguments().getInt("color");
            position = getArguments().getInt("position");

            use = true;
        } else if (color == 0) {
            Random rand = new Random();
            color = Color.argb(255, rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
        }

        colorImage.setBackgroundColor(color);

        colorImage.setOnClickListener(this);

        return view;
    }

    public void SetArguments() {
        Bundle bundle = new Bundle();
        bundle.putString("head", head.getText().toString());
        bundle.putString("description", description.getText().toString());

        bundle.putInt("color", color);

        Time time = new Time(Time.getCurrentTimezone());
        time.setToNow();
        bundle.putString("date", time.format("%d.%m.%Y"));

        bundle.putInt("position", position);

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


            InputMethodManager imm = (InputMethodManager) getActivity().getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getWindow().getCurrentFocus().getWindowToken(), 0);


            SetArguments();
        } else {
               Toast toast = Toast.makeText(this.getContext(), "Введите заголовок",Toast.LENGTH_LONG);
            toast.show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void GetArguments(int color)
    {
        this.color = color;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Bundle bundle) {
        if (mListener != null) {
            mListener.onFragmentInteraction(bundle);
        }
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

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) description.getLayoutParams();

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Bundle bundle);
    }
}
