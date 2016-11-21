package by.black_pearl.journal.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import by.black_pearl.journal.MainActivity;
import by.black_pearl.journal.R;
import by.black_pearl.journal.workers.JournalWorker;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RackFragment extends Fragment {

    private ArrayList<JournalWorker.Journal> journalsList;

    public RackFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RackFragment.
     */
    public static RackFragment newInstance() {
        RackFragment fragment = new RackFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.journalsList = ((MainActivity) getActivity()).getJournalWorker().getJournals();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_rack, container, false);
        if (journalsList == null || journalsList.size() == 0) {
            v.findViewById(R.id.rackLayout).setVisibility(View.GONE);
            v.findViewById(R.id.noItemTextView).setVisibility(View.VISIBLE);
            ((TextView) v.findViewById(R.id.noItemTextView)).setText("No items");
        }
        else {
            v.findViewById(R.id.rackLayout).setVisibility(View.VISIBLE);
            v.findViewById(R.id.noItemTextView).setVisibility(View.GONE);
            prepareRackLayout(v);
        }
        return v;
    }

    private void prepareRackLayout(View v) {
        if (journalsList.size() == 1)
        {
            v.findViewById(R.id.gridView).setVisibility(View.GONE);
            ((ImageView) v.findViewById(R.id.imageView)).setImageResource(R.drawable.test_poster);
        }
        else {
            //TODO: create adapter
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
