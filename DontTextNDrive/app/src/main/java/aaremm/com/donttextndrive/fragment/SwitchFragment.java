package aaremm.com.donttextndrive.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import aaremm.com.donttextndrive.R;
import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class SwitchFragment extends Fragment {

    @InjectView(R.id.iv1)ImageView iv1;
    @InjectView(R.id.iv2)ImageView iv2;
    @InjectView(R.id.iv3)ImageView iv3;
    @InjectView(R.id.iv4)ImageView iv4;

    public SwitchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_switch, container, false);
        ButterKnife.inject(this, rootView);
        loadImages();
        return rootView;
    }
    private void loadImages() {
        Picasso.with(getActivity()).load(R.drawable.bmw).into(iv1);
        Picasso.with(getActivity()).load(R.drawable.both).into(iv2);
        Picasso.with(getActivity()).load(R.drawable.bmwt).into(iv3);
        Picasso.with(getActivity()).load(R.drawable.bmwth).into(iv4);
    }


}
