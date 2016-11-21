package by.black_pearl.journal.workers;

import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by BLACK_Pearl.
 */

public class Journal {

    public Test getTestFromJournal(int journalNumber, int testNumber) {

        return new Test();
    }

    public class Test {
        private static final String DEFAUL_IMAGE_PATH = "default";
        private ArrayList<String> mQuestions;
        private ArrayList<String[]> mAnswers;
        private ArrayList<String> mImagePaths;

        public Test() {
            this.mQuestions = new ArrayList<>();
            this.mAnswers = new ArrayList<>();
            this.mImagePaths = new ArrayList<>();
        }

        protected void addQuestion(String question, String[] answers, @Nullable String imagePath) {
            this.mQuestions.add(question);
            this.mAnswers.add(answers);
            if(imagePath != null) {
                this.mImagePaths.add(imagePath);
            }
            else {
                this.mImagePaths.add(DEFAUL_IMAGE_PATH);
            }
        }

        public ArrayList<String> getQuestions() {
            return this.mQuestions;
        }

        public ArrayList<String[]> getAnswers() {
            return this.mAnswers;
        }

        public ArrayList<String> getImagePaths() {
            return this.mImagePaths;
        }
    }
}
