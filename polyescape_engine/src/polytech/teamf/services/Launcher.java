package polytech.teamf.services;

import polytech.teamf.plugins.Plugin;

public class Launcher {

    public static void main(String[] args) {
        GoogleSheetsService s = new GoogleSheetsService();
        Plugin p = s.buildMultipleChoiceQuestionPlugin("https://docs.google.com/spreadsheets/d/17d4SfrjbdyPq9x8HEjumkfYN8b_aBPwaIHVFJnNqbG0/gviz/tq?tqx=out:csv");

        System.out.println(p.toString());
    }
}
