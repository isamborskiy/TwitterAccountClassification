package com.samborskiy.word.generator;


import com.samborskiy.statistic.Statistic;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.FileOutputStream;
import java.util.List;

import static com.samborskiy.Main.*;

/**
 * Created by Whiplash on 10.05.2015.
 */
public class FSSTableGenerator extends WordGenerator {

    public FSSTableGenerator(String fileName) {
        super(fileName);
    }

    public void generate() throws Exception {
        List<Statistic> statistics = getStatistics();
        int features = getFeatures().size();
        int classifiers = getClassifierWrappers().size();
        int rows = features * (classifiers + 1) + 1;

        XWPFDocument doc = new XWPFDocument();
        XWPFTable table = doc.createTable(rows, 3);
        fillHeader(table);
        for (int i = 0; i < features; i++) {
            fillFeature(table, statistics, classifiers, i);
        }

        try (FileOutputStream out = new FileOutputStream(fileName)) {
            doc.write(out);
        }
    }

    private void fillHeader(XWPFTable table) {
        table.getRow(0).getCell(1).setText("Точность");
        table.getRow(0).getCell(2).setText("F1-мера");
    }

    private void fillFeature(XWPFTable table, List<Statistic> statistics, int classifiers, int fssNumber) {
        int row = fssNumber * (classifiers + 1) + 1;
        String fssName = statistics.get(fssNumber * classifiers).getFeatureSelectionName();
        int attributeNumber = statistics.get(fssNumber * classifiers).getAttributeNumber();
        table.getRow(row).getCell(0).setText(String.format("%s (%s %d %s)", fssName.replace("_", "-"), leftForm(attributeNumber), attributeNumber, attributeForm(attributeNumber)));
        for (int i = 1; i <= classifiers; i++) {
            Statistic statistic = statistics.get(fssNumber * classifiers + i - 1);
            XWPFTableRow tableRow = table.getRow(row + i);
            tableRow.getCell(0).setText(getClassifierName(statistic.getClassifierName()));
            tableRow.getCell(1).setText(statistic.getAccuracyString());
            tableRow.getCell(2).setText(statistic.getFMeasureString());
        }
    }

    private String leftForm(int count) {
        count %= 100;
        if (count >= 10 && count <= 20) {
            return "осталось";
        }
        count %= 10;
        if (count == 1) {
            return "остался";
        }
        if (count <= 4) {
            return "осталось";
        }
        return "осталось";
    }

    private String attributeForm(int count) {
        count %= 100;
        if (count >= 10 && count <= 20) {
            return "признаков";
        }
        count %= 10;
        if (count == 1) {
            return "признак";
        }
        if (count <= 4) {
            return "признака";
        }
        return "признаков";
    }
}
