package com.samborskiy.entity.analyzers.morphological;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Whiplash on 25.04.2015.
 */
public class SimpleMorphologicalAnalyzer implements MorphologicalAnalyzer {

    private static final Map<PartOfSpeech, List<String>> ENDINGS = new HashMap<>();

    static {
        String[] adjectiveEndings = {"ее", "ие", "ые", "ое", "ими", "ыми", "ей", "ий", "ый", "ой", "ем", "им", "ым", "ом", "его", "ого", "ему", "ому", "их", "ых", "ую", "юю", "ая", "яя", "ою", "ею"};
        String[] communionEndings = {"ивш", "ывш", "ующ", "ем", "нн", "вш", "ющ", "ущи", "ющи", "ящий", "щих", "щие", "ляя"};
        String[] verbEndings = {"ила", "ыла", "ена", "ейте", "уйте", "ите", "или", "ыли", "ей", "уй", "ил", "ыл", "им", "ым", "ен", "ило", "ыло", "ено", "ят", "ует", "уют", "ит", "ыт", "ены", "ить", "ыть", "ишь", "ую", "ю", "ла", "на", "ете", "йте", "ли", "й", "л", "ем", "н", "ло", "ет", "ют", "ны", "ть", "ешь", "нно"};
        String[] nounEndings = {"а", "ев", "ов", "ье", "иями", "ями", "ами", "еи", "ии", "и", "ией", "ей", "ой", "ий", "й", "иям", "ям", "ием", "ем", "ам", "ом", "о", "у", "ах", "иях", "ях", "ы", "ь", "ию", "ью", "ю", "ия", "ья", "я", "ок", "мва", "яна", "ровать", "ег", "ги", "га", "сть", "сти"};
        String[] adverbEndings = {"чно", "еко", "соко", "боко", "роко", "имо", "мно", "жно", "жко", "ело", "тно", "льно", "здо", "зко", "шо", "хо", "но"};
        String[] numeralEndings = {"чуть", "много", "мало", "еро", "вое", "рое", "еро", "сти", "одной", "двух", "рех", "еми", "яти", "ьми", "ати", "дного", "сто", "ста", "тысяча", "тысячи", "две", "три", "одна", "умя", "тью", "мя", "тью", "мью", "тью", "одним"};
        String[] conjunctionEndings = {"более", "менее", "очень", "крайне", "скоре", "некотор", "кажд", "други", "котор", "когд", "однак", "если", "чтоб", "хот", "смотря", "как", "также", "так", "зато", "что", "или", "потом", "эт", "тог", "тоже", "словно", "ежели", "кабы", "коли", "ничем", "чем"};
        String[] prepositionEndings = {"в", "на", "по", "из"};

        ENDINGS.put(PartOfSpeech.ADJECTIVE, Arrays.asList(adjectiveEndings));
        ENDINGS.put(PartOfSpeech.COMMUNION, Arrays.asList(communionEndings));
        ENDINGS.put(PartOfSpeech.VERB, Arrays.asList(verbEndings));
        ENDINGS.put(PartOfSpeech.NOUN, Arrays.asList(nounEndings));
        ENDINGS.put(PartOfSpeech.ADVERB, Arrays.asList(adverbEndings));
        ENDINGS.put(PartOfSpeech.NUMERAL, Arrays.asList(numeralEndings));
        ENDINGS.put(PartOfSpeech.CONJUNCTION, Arrays.asList(conjunctionEndings));
        ENDINGS.put(PartOfSpeech.PREPOSITION, Arrays.asList(prepositionEndings));
    }

    @Override
    public PartOfSpeech get(String word) {
        for (PartOfSpeech partOfSpeech : ENDINGS.keySet()) {
            for (String part : ENDINGS.get(partOfSpeech)) {
                switch (partOfSpeech) {
                    case PREPOSITION:
                        if (word.equals(part)) {
                            return partOfSpeech;
                        }
                        break;
                    case CONJUNCTION:
                        if (word.startsWith(part)) {
                            return partOfSpeech;
                        }
                        break;
                    default:
                        if (word.endsWith(part)) {
                            return partOfSpeech;
                        }
                }
            }
        }
        return null;
    }
}
