package com.samborskiy.entity.instances.functions.sign;

import com.samborskiy.entity.instances.functions.AccountFunction;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Whiplash on 22.04.2015.
 */
public abstract class SignFunction extends AccountFunction {

    protected static final Map<String, String> SIGNS = new HashMap<>();

    static {
        SIGNS.put("`", "stress");
        SIGNS.put("~", "tilde");
        SIGNS.put("!", "exclamation_point");
        SIGNS.put("@", "at");
        SIGNS.put("#", "hash");
        SIGNS.put("$", "dollar_sign");
        SIGNS.put("%", "percent");
        SIGNS.put("^", "exponent");
        SIGNS.put("&", "ampersand");
        SIGNS.put("*", "multiply");
        SIGNS.put("(", "opening_parenthesis");
        SIGNS.put(")", "closing_parenthesis");
        SIGNS.put("-", "minus");
        SIGNS.put("_", "underline");
        SIGNS.put("+", "plus");
        SIGNS.put("=", "equals");
        SIGNS.put("[", "opening_bracket");
        SIGNS.put("{", "opening_brace");
        SIGNS.put("]", "closing_bracket");
        SIGNS.put("}", "closing_brace");
        SIGNS.put("\\", "backslash");
        SIGNS.put("|", "vertical_slash");
        SIGNS.put(";", "semicolon");
        SIGNS.put(":", "colon");
        SIGNS.put("'", "apostrophe");
        SIGNS.put("\"", "quote");
        SIGNS.put(",", "comma");
        SIGNS.put("<", "less_than");
        SIGNS.put(".", "point");
        SIGNS.put(">", "great_than");
        SIGNS.put("/", "divide");
        SIGNS.put("?", "question_mark");
        SIGNS.put("№", "number_sign");
    }
}
