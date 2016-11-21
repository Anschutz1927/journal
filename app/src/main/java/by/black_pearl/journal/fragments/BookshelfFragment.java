package by.black_pearl.journal.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import by.black_pearl.journal.R;
import by.black_pearl.journal.views.RowBooksView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookshelfFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookshelfFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookshelfFragment extends Fragment implements View.OnClickListener {
    private static final String JOURNALS_COUNT = "journals_count";
    private static final String JOURNALS_POSTERS = "journals_posters";

    private int mJournalsCount;
    private int[] mJournalsPosters;
    private OnFragmentInteractionListener mListener;

    public BookshelfFragment() {
        setRetainInstance(true);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param j_count is quantity of existing journals.
     * @param j_posters is resource posters of journals.
     * @return A new instance of fragment MainBookBookshelfFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookshelfFragment newInstance(int j_count, int[] j_posters) {
        BookshelfFragment fragment = new BookshelfFragment();
        Bundle args = new Bundle();
        args.putInt(JOURNALS_COUNT, j_count);
        args.putIntArray(JOURNALS_POSTERS, j_posters);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mJournalsCount = getArguments().getInt(JOURNALS_COUNT);
            this.mJournalsPosters = getArguments().getIntArray(JOURNALS_POSTERS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        LinearLayout postersLayout = (LinearLayout) v.findViewById(R.id.postersLayout);

        //The creation of the required number of rows.
        int rows_cnt = (this.mJournalsCount - 1) / RowBooksView.NUMBER_COLUMNS_IN_ROW;
        for(int i = 0; i <= rows_cnt; i++) {
            RowBooksView rbView = new RowBooksView(v.getContext());
            for(int j = 0; j < RowBooksView.NUMBER_COLUMNS_IN_ROW ; j++) {
                int cnt = i * RowBooksView.NUMBER_COLUMNS_IN_ROW + j;
                if(cnt < this.mJournalsCount) {
                    rbView.setPosterAt(j, mJournalsPosters[cnt]);
                    rbView.setOnClickListenerAt(j, this);
                }
                else {
                    break;
                }
            }
            postersLayout.addView(rbView);
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
