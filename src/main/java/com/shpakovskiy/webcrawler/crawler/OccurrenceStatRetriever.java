package com.shpakovskiy.webcrawler.crawler;

import com.shpakovskiy.webcrawler.pageprocessor.SourceLoader;
import com.shpakovskiy.webcrawler.statistics.OccurrencesTable;
import com.shpakovskiy.webcrawler.statistics.StatItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OccurrenceStatRetriever implements Runnable {

    private String pageAddress;
    private OccurrencesTable occurrencesTable;
    private List<String> terms;
    private final SourceLoader sourceLoader = new SourceLoader();

    @Override
    public void run() {
        occurrencesTable.addItem(new StatItem(pageAddress,
                getTermsOccurrences(sourceLoader.getPageSource(pageAddress), terms)));
    }

    //This method used to count occurrences of every term in the text
    private List<Integer> getTermsOccurrences(String pageSource, List<String> terms) {
        HashMap<String, Integer> occurrencesMap = newOccurrencesMap(terms);
        if (pageSource != null) {
            String allTermsMatchingRegex = makeAllTermsMatchingRegex(terms);
            Pattern pattern = Pattern.compile(allTermsMatchingRegex, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(pageSource);

            int lookFromPosition = 0;
            while (matcher.find(lookFromPosition)) {
                String term = pageSource.substring(matcher.start() + 1, matcher.end() - 1).toUpperCase();
                //occurrencesMap.merge(term, 1, Integer::sum);

                int count = occurrencesMap.get(term);
                count++;
                occurrencesMap.put(term, count);
                lookFromPosition = matcher.end() + 1;
            }
        }

        List<Integer> occurrencesList = new ArrayList<>();
        int sum = 0;
        for (String term : terms) {
            int currentValue = occurrencesMap.get(term);
            sum += currentValue;
            occurrencesList.add(currentValue);
        }
        occurrencesList.add(sum);

        return occurrencesList;
    }

    //This method generates regex, used to match any single term in the text
    private String makeAllTermsMatchingRegex(List<String> terms) {
        return ("[^<\\/\\-\\+=\\(]\\b(" + String.join("|", terms) + ")\\b[^>\\/\\-\\+=\\)]");
    }

    //This method initializes HashMap, used to hold occurrences of every term on one page
    private HashMap<String, Integer> newOccurrencesMap(List<String> terms) {
        HashMap<String, Integer> occurrencesMap = new HashMap<>();
        for (String term : terms) {
            occurrencesMap.put(term, 0);
        }
        return occurrencesMap;
    }

    public static class Builder {
        private final OccurrenceStatRetriever newStatRetriever;

        public Builder() {
            newStatRetriever = new OccurrenceStatRetriever();
        }

        public Builder pageAddress(String pageAddress) {
            newStatRetriever.pageAddress = pageAddress;
            return this;
        }

        public Builder statTable(OccurrencesTable occurrencesTable) {
            newStatRetriever.occurrencesTable = occurrencesTable;
            return this;
        }

        public Builder linksHolder(List<String> terms) {
            newStatRetriever.terms = terms;
            return this;
        }

        public OccurrenceStatRetriever build() {
            return newStatRetriever;
        }
    }
}
