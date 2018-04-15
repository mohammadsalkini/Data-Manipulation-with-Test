package com.alsalkini.MDApp;

import com.alsalkini.MDApp.controller.MovieDatabaseSummarizer;
import com.alsalkini.MDApp.model.Summary;
import com.alsalkini.MDApp.view.SummaryDisplayer;

public class AppMain {

    public static void main(String[] args) {
        MovieDatabaseSummarizer summarizer = new MovieDatabaseSummarizer();
        SummaryDisplayer displayer = new SummaryDisplayer();

        // this part is going to do the logic
        // the result will be stored in the summary
        Summary summary = summarizer.summarize();

        // this part will only display the stuff
        displayer.display(summary);
    }
}
