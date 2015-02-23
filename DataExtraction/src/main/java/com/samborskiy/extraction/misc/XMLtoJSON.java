package com.samborskiy.extraction.misc;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Whiplash on 10.12.2014.
 */
public class XMLtoJSON extends DefaultHandler {

    public static final File xmlFile = new File("DataExtraction/res/ru/names_m.xml");
    public static final File jsonFile = new File("DataExtraction/res/ru/man_names");
    private String currentTag = "";
    private List<String> keys = new ArrayList<>();

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start parse XML...");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Stop parse XML...");
        try {
            PrintWriter pw = new PrintWriter(jsonFile);
            keys.forEach(pw::println);
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes attributes) throws SAXException {
        currentTag = qName;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        currentTag = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (currentTag.equals("key")) {
            keys.add(new String(ch, start, length));
        }
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLtoJSON xmLtoJSON = new XMLtoJSON();
        parser.parse(xmlFile, xmLtoJSON);
    }

}
