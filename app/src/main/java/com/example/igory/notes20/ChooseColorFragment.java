package com.example.igory.notes20;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChooseColorFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "ChooseColorFragment";

    public static final int widthColor = 80;
    public static final int widthMargin = 24;

    private OnFragmentInteractionListener mListener;

    public ChooseColorFragment() {
    }

    public static ChooseColorFragment newInstance(int color) {
        ChooseColorFragment fragment = new ChooseColorFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.color, color);
        fragment.setArguments(args);
        return fragment;
    }

    TextView RGB;
    TextView HSV;
    ImageView selectedColor;

    View view;

    int color;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        setRetainInstance(true);

        if (getArguments() != null) {
            color = getArguments().getInt("color");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_choose_color, container, false);

        RGB = view.findViewById(R.id.RGB);
        HSV = view.findViewById(R.id.HSV);
        selectedColor = view.findViewById(R.id.selected_color);

        final LinearLayout layout = view.findViewById(R.id.circles);

        int px = (int) getResources().getDisplayMetrics().density;

        int width = px * widthColor;
        int margin = px * widthMargin;

        int length = width + margin / 2;

        //get Bitmap
        layout.setDrawingCacheEnabled(true);
        layout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        layout.layout(0, 0, layout.getMeasuredWidth(), layout.getMeasuredHeight());
        layout.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(layout.getDrawingCache());
        layout.setDrawingCacheEnabled(false);

        int centr;

        for (int i = R.id.imageView10, j = 0; j < 16; i++, j++) {
            centr = bitmap.getPixel(length - width / 2, width / 2);

            (view.findViewById(i)).setBackgroundColor(centr);
            (view.findViewById(i)).setOnClickListener(this);

            length += width + margin;
        }

        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        RGB.setText(String.format("RGB: %s,%s,%s",
                String.valueOf(red),
                String.valueOf(green),
                String.valueOf(blue)));

        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);

        HSV.setText(String.format("HSV: %s,%s,%s",
                String.valueOf((int) hsv[0]),
                String.valueOf((int) hsv[1]),
                String.valueOf((int) hsv[2])));

        selectedColor.setBackgroundColor(color);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getActivity().getSupportFragmentManager().popBackStack();

        SetArguments();

        return super.onOptionsItemSelected(item);
    }

    private void SetArguments() {
        mListener.onFragmentInteraction(color);
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

        ImageView selected = view.findViewById(v.getId());

        Bitmap screenshot;
        selected.setDrawingCacheEnabled(true);
        screenshot = Bitmap.createBitmap(selected.getDrawingCache());
        selected.setDrawingCacheEnabled(false);

        int pixel = screenshot.getPixel(10, 10);

        int red = Color.red(pixel);
        int green = Color.green(pixel);
        int blue = Color.blue(pixel);

        selectedColor.setBackgroundColor(pixel);

        RGB.setText(String.format("RGB: %s,%s,%s",
                String.valueOf(red),
                String.valueOf(green),
                String.valueOf(blue)));

        float[] hsv = new float[3];
        Color.colorToHSV(pixel, hsv);

        HSV.setText(String.format("HSV: %s,%s,%s",
                String.valueOf((int) hsv[0]),
                String.valueOf((int) hsv[1]),
                String.valueOf((int) hsv[2])));

        color = pixel;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int color);
    }
}
