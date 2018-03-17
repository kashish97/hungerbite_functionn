package com.example.kashishgupta.hackathon;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistrationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistrationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RegistrationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registration, container, false);
        MaterialSpinner spinner = (MaterialSpinner) v.findViewById(R.id.spinner);

        // Inflate the layout for this fragment
        final TextView tvgov = (TextView) v.findViewById(R.id.textView2);
        final Button bgov = (Button) v.findViewById(R.id.button);
        final TextView tvInd = (TextView) v.findViewById(R.id.textView2);
        final Button bInd = (Button) v.findViewById(R.id.button2);
        final TextView tvstu = (TextView) v.findViewById(R.id.textView4);
        final Button bstu = (Button) v.findViewById(R.id.button3);



        spinner.setItems("Government Employee", "Industrialist", "Student");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {


            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                switch (position) {
                    case 0:
                        tvInd.setVisibility(View.INVISIBLE);

                        bInd.setVisibility(View.INVISIBLE);

                        tvgov.setVisibility(View.VISIBLE);

                        bgov.setVisibility(View.VISIBLE);
                        tvstu.setVisibility(View.INVISIBLE);

                        bstu.setVisibility(View.INVISIBLE);

                        break;
                    case 1:
                        tvstu.setVisibility(View.INVISIBLE);

                        bstu.setVisibility(View.INVISIBLE);
                        tvInd.setVisibility(View.VISIBLE);

                        bInd.setVisibility(View.VISIBLE);
                        tvgov.setVisibility(View.INVISIBLE);

                        bgov.setVisibility(View.INVISIBLE);

                        break;
                    case 2:
                        tvgov.setVisibility(View.INVISIBLE);

                        bgov.setVisibility(View.INVISIBLE);
                        tvstu.setVisibility(View.VISIBLE);
                        tvInd.setVisibility(View.INVISIBLE);

                        bInd.setVisibility(View.INVISIBLE);
                        bstu.setVisibility(View.VISIBLE);

                        break;
                }
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

            }
        });
        return v;}
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        void onFragmentInteraction(Uri uri);
    }
}
