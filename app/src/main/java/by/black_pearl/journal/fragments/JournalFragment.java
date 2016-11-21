package by.black_pearl.journal.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import by.black_pearl.journal.R;
import by.black_pearl.journal.views.PosterView;
import by.black_pearl.journal.workers.JournalWorker;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JournalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JournalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JournalFragment extends Fragment implements View.OnClickListener {

    private static final String ID_PARAM_POSTER = "param1";

    private OnFragmentInteractionListener mListener;
    private int mResId = -1;
    private JournalWorker mJournalWorker;

    public JournalFragment() {
        setRetainInstance(true);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param resId id of graphical resource of poster.
     * @return A new instance of fragment JournalFragment.
     */
    public static JournalFragment newInstance(int resId) {
        JournalFragment fragment = new JournalFragment();
        Bundle args = new Bundle();
        args.putInt(ID_PARAM_POSTER, resId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            mResId = getArguments().getInt(ID_PARAM_POSTER, -1);

        }
        catch (Exception ignored) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_journal, container, false);
        v.findViewById(R.id.otherJournalButton).setOnClickListener(this);
        v.findViewById(R.id.lookInJournalButton).setOnClickListener(this);
        if(this.mResId != -1) {
            PosterView pView = new PosterView(v.getContext());
            pView.setPoster(this.mResId);
            ((FrameLayout) v.findViewById(R.id.posterLayot)).addView(pView);
        }
        else {
            ((FrameLayout) v.findViewById(R.id.posterLayot))
                    .addView(new PosterView(v.findViewById(R.id.posterLayot).getContext()));
        }
        return v;
    }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
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
