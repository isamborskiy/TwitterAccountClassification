package com.samborskiy.word.generator;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Whiplash on 10.05.2015.
 */
public class TranslateAttributes extends WordGenerator {

    private final String PROGRAM_ATTRIBUTE = "res/ru/attributes_name_program";
    private final String TRANSLATE_ATTRIBUTE = "res/ru/attributes_name_translate";

    private final Instances instances;
    private final Map<String, String> translator = new HashMap<>();

    public TranslateAttributes(String fileName, Instances instances) {
        super(fileName);
        this.instances = instances;
        String attribute;
        try (BufferedReader programReader = new BufferedReader(new FileReader(PROGRAM_ATTRIBUTE))) {
            try (BufferedReader translateReader = new BufferedReader(new FileReader(TRANSLATE_ATTRIBUTE))) {
                while ((attribute = programReader.readLine()) != null) {
                    translator.put(attribute, translateReader.readLine());
                }
            }
        } catch (IOException ignore) {
        }
    }

    @Override
    public void generate() throws Exception {
        int rows = instances.numAttributes();

        XWPFDocument doc = new XWPFDocument();
        XWPFTable table = doc.createTable(rows, 2);
        fillHeader(table);
        for (int i = 0; i < instances.numAttributes() - 1; i++) {
            XWPFTableRow tableRow = table.getRow(i + 1);
            tableRow.getCell(0).setText(String.valueOf(i + 1));
            tableRow.getCell(1).setText(translator.get(instances.attribute(i).name()));
        }

        try (FileOutputStream out = new FileOutputStream(fileName)) {
            doc.write(out);
        }
    }

    private void fillHeader(XWPFTable table) {
        table.getRow(0).getCell(0).setText("№");
        table.getRow(0).getCell(1).setText("Название атрибута");
    }
}
