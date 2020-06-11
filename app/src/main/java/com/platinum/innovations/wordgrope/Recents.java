package com.platinum.innovations.wordgrope;

class Recents {
    private String Word;
    private String Date;
    private int N_Words;

    Recents(String word, String date, int n_results) {
        Word = word;
        Date = date;
        N_Words = n_results;
    }
    String getWord() {
        return Word;
    }

    String getDate() {
        return Date;
    }

    int getN_Words() {
        return N_Words;
    }
}
