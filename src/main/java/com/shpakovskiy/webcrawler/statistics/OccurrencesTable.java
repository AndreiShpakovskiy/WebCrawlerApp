package com.shpakovskiy.webcrawler.statistics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OccurrencesTable implements Table {

    private final List<StatItem> occurrencesTable = new ArrayList<>();

    @Override
    public void addItem(StatItem statItem) {
        occurrencesTable.add(statItem);
    }

    @Override
    public List<StatItem> getAsList() {
        return occurrencesTable;
    }

    @Override
    public List<StatItem> getTop10() {
        return occurrencesTable.stream()
                .sorted(Comparator.comparing(StatItem::getOccurrencesSum).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    @Override
    public String toCsv() {
        StringBuilder tableBuilder = new StringBuilder();

        for (StatItem statItem : occurrencesTable) {
            tableBuilder.append(statItem.toCsv()).append('\n');
        }

        return tableBuilder.toString();
    }

    @Override
    public String top10ToCsv() {
        List<StatItem> top10 = getTop10();
        StringBuilder builder = new StringBuilder();

        for (StatItem statItem : top10) {
            builder.append(statItem.toCsv()).append('\n');
        }

        return builder.toString();
    }
}
