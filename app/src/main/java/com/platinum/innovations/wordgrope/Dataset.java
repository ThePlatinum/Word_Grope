package com.platinum.innovations.wordgrope;

import java.util.ArrayList;

class Dataset {
    private String mWord;

    private Dataset(String word) {
        mWord = word;
    }

    String getmWord() {
        return mWord;
    }

    static ArrayList<Dataset> createContactsList(String theWord) {
        ArrayList<Dataset> datasets = new ArrayList<>();

        datasets.add(new Dataset(theWord));

        return datasets;
    }
}