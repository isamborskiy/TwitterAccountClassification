package com.samborskiy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Whiplash on 21.05.2015.
 */
public class Ololo {

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("knn_attrs"))) {
            String line;
            List<String> list = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(" ");
                list.add(arr[1]);
            }
            Collections.reverse(list);
            list.forEach(System.out::println);
        } catch (Exception ignore) {
        }
    }
}
