package com.platinum.innovations.wordgrope;

import java.util.ArrayList;

class Dataset {
    String mWord;

    Dataset(String word) {
        mWord = word;
    }

    String getmWord() {
        return mWord;
    }

    public void setmWord(String mWord) {
        this.mWord = mWord;
    }

    // Here out
    static ArrayList<Dataset> createContactsList(String theWord) {
        ArrayList<Dataset> datasets = new ArrayList<>();

        datasets.add(new Dataset(theWord));

        return datasets;
    }
}