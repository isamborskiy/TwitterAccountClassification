// CHECKSTYLE:OFF
package com.samborskiy.extraction;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        File file = new File("DataExtraction/res/ru/config.json");
        Configuration configuration = Configuration.build(file);
    }

}
