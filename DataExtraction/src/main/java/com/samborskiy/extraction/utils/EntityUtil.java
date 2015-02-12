package com.samborskiy.extraction.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

public class EntityUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    private EntityUtil() {
    }

    public static <T> T deserialize(byte[] jsonData, Class<T> tClass) throws IOException {
        return mapper.readValue(jsonData, tClass);
    }

    public static String serialize(Object object) {
        StringWriter str = new StringWriter();
        try {
            mapper.writeValue(str, object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

}
