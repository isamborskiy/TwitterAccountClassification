package com.samborskiy.word.generator;


import com.samborskiy.statistic.Statistic;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;

import static com.samborskiy.Main.getClassifierWrappers;
import static com.samborskiy.Main.getStatistics;

/**
 * Created by Whiplash on 10.05.2015.
 */
public class ClassifierParamsTableGenerator extends WordGenerator {

    public ClassifierParamsTableGenerator(String fileName) {
        super(fileName);
    }

    public void generate() throws Exception {
        List<Statistic> statistics = getStatistics();

        int classifiers = getClassifierWrappers().size();
        int rows = classifiers + 1;

        XWPFDocument doc = new XWPFDocument();
        XWPFTable table = doc.createTable(rows, 3);
        fillHeader(table);
        for (int i = 0; i < classifiers; i++) {
            Statistic statistic = statistics.get(i);
            XWPFTableRow tableRow = table.getRow(i + 1);
            tableRow.getCell(0).setText(getClassifierParams(statistic.getClassifierName()));
            tableRow.getCell(1).setText(statistic.getAccuracyString());
            tableRow.getCell(2).setText(statistic.getFMeasureString());
        }

        try (FileOutputStream out = new FileOutputStream(fileName)) {
            doc.write(out);
        }

        Collections.sort(statistics);
        statistics.forEach(System.out::println);
    }

    private void fillHeader(XWPFTable table) {
        table.getRow(0).getCell(0).setText("Параметры");
        table.getRow(0).getCell(1).setText("Точность");
        table.getRow(0).getCell(2).setText("F1-мера");
    }
}
