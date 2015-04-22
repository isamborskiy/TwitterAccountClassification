package com.samborskiy.misc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class SmilesFilter {

    public static void main(String[] args) throws IOException {
        Set<String> smiles = new HashSet<>();
        try (BufferedReader bf = new BufferedReader(new FileReader("smiles"))) {
            String line;
            while ((line = bf.readLine()) != null) {
                smiles.add(line.trim().toLowerCase());
            }
        }
        List<String> listOfSmiles = new ArrayList<>(smiles);
        Collections.sort(listOfSmiles, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(o2.length(), o1.length());
            }
        });
        try (PrintWriter pw = new PrintWriter("res/smiles")) {
            listOfSmiles.forEach(pw::println);
        }
    }
}
