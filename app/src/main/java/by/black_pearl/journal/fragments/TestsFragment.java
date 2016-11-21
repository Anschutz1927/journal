package by.black_pearl.journal.fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import by.black_pearl.journal.MainActivity;
import by.black_pearl.journal.R;
import by.black_pearl.journal.TestActivity;
import by.black_pearl.journal.views.TestSingleView;
import by.black_pearl.journal.workers.DataBaseWorker;
import by.black_pearl.journal.workers.JournalWorker;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TestsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestsFragment extends Fragment {
    private static final String POSITION = "position";
    private static final String IS_CHILD = "ischild";

    private boolean mIsChild;
    private int mPosition;

    public TestsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment TestsFragment.
     */
    public static TestsFragment newInstance() {
        return TestsFragment.newInstance(false, 0);
    }

    /**
     * Use this factory method to create a new instance of
     * child this fragment using the provided parameters.
     * @param isChild must be 'true' to create new child of TestFragment.
     * @param position number of current page.
     * @return A new instance of fragment TestsFragment.
     */
    private static TestsFragment newInstance(boolean isChild, int position) {
        TestsFragment fragment = new TestsFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(IS_CHILD, isChild);
        bundle.putInt(POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            this.mIsChild = getArguments().getBoolean(IS_CHILD);
            this.mPosition = getArguments().getInt(POSITION, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final JournalWorker jWorker = ((MainActivity)getActivity()).getJournalWorker();
        if(this.mIsChild) {
            final TestSingleView singleView = new TestSingleView(getContext());

            Cursor testsPreviewCursor = jWorker.getTestsPreview();
            testsPreviewCursor.moveToPosition(mPosition);

            int testId = testsPreviewCursor.getColumnIndex(DataBaseWorker.COLUMN_TEST_ID);
            int name = testsPreviewCursor.getColumnIndex(DataBaseWorker.COLUMN_NAME);
            int magazineId = testsPreviewCursor.getColumnIndex(DataBaseWorker.COLUMN_MAGAZINE_ID);
            int lastScore = testsPreviewCursor.getColumnIndex(DataBaseWorker.COLUMN_LAST_SCORE);
            int posterImage = testsPreviewCursor.getColumnIndex(DataBaseWorker.COLUMN_POSTER_IMAGE);

            singleView.setTestNameView(testsPreviewCursor.getString(name));
            singleView.setTestDescriptionView(
                    "test id = " + testsPreviewCursor.getString(testId) + ", name = " +
                            testsPreviewCursor.getString(name) + "."
            );
            //remove try-catch
            try {
                if (testsPreviewCursor.getString(lastScore) != null) {
                    singleView.setLastResultView(testsPreviewCursor.getString(lastScore));
                } else {
                    singleView.setLastResultView("Not yet passed");
                }
            }
            catch (Exception ignored) {
            }
            testsPreviewCursor.close();
            //
            singleView.setStartButonOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(
                            getContext(),
                            TestActivity.class
                    )
                            .putExtra(TestActivity.JOURNAL_TEST, mPosition));
                }
            });
            return singleView;
        }
        else {
            View v = inflater.inflate(R.layout.fragment_tests, container, false);
            FrameLayout fLayout = (FrameLayout) v.findViewById(R.id.frameLayout);
            Cursor testsPreviewCursor = jWorker.getTestsPreview();
            if(testsPreviewCursor.getCount() == 0) {
                ((TextView)fLayout.getChildAt(0)).setText("No items");
                fLayout.getChildAt(0).setVisibility(View.VISIBLE);
                return v;
            }

            ViewPager vPager = new ViewPager(v.getContext());
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            vPager.setLayoutParams(params);
            vPager.setId(R.id.viewPager);
            vPager.setAdapter(
                    new FPagerAdapter(getChildFragmentManager(), testsPreviewCursor.getCount())
            );
            fLayout.addView(vPager);
            testsPreviewCursor.close();
            return v;
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

    private class FPagerAdapter extends FragmentPagerAdapter {
        private int mTestCount;

        public FPagerAdapter(FragmentManager fm, int testCount) {
            super(fm);
            this.mTestCount = testCount;
        }

        @Override
        public Fragment getItem(int position) {
            return TestsFragment.newInstance(true, position);
        }

        @Override
        public int getCount() {
            return this.mTestCount;
        }
    }
}
