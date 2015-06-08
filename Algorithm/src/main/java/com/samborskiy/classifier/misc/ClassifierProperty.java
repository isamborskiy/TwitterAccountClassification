package com.samborskiy.classifier.misc;

import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import org.reflections.Reflections;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.util.Properties;
import java.util.Set;

public class ClassifierProperty {

    private static FrequencyAnalyzer frequencyAnalyzer = FrequencyAnalyzer.get();
    private static GrammarAnalyzer grammarAnalyzer = GrammarAnalyzer.get();
    private static MorphologicalAnalyzer morphologicalAnalyzer = MorphologicalAnalyzer.get();
    private static TweetParser tweetParser = TweetParser.get();
    private static boolean isDebug = false;

    public static final String DEFAULT_FREQUENCY_ANALYZER = "com.samborskiy.entity.analyzers.frequency.FrequencyDictionary";
    public static final String DEFAULT_GRAMMAR_ANALYZER = "com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer";
    public static final String DEFAULT_MORPHOLOGICAL_ANALYZER = "com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer";
    public static final String DEFAULT_SENTENCE_ANALYZER = "com.samborskiy.entity.analyzers.sentence.TweetParser";

    static {
        try (InputStream stream = new FileInputStream("twitter_classifier.property")) {
            Properties properties = new Properties();
            properties.load(stream);
            frequencyAnalyzer = getClass(properties.getProperty("frequency_analyzer", DEFAULT_FREQUENCY_ANALYZER), FrequencyAnalyzer.class);
            grammarAnalyzer = getClass(properties.getProperty("grammar_analyzer", DEFAULT_GRAMMAR_ANALYZER), GrammarAnalyzer.class);
            morphologicalAnalyzer = getClass(properties.getProperty("morphological_analyzer", DEFAULT_MORPHOLOGICAL_ANALYZER), MorphologicalAnalyzer.class);
            tweetParser = getClass(properties.getProperty("sentence_analyzer", DEFAULT_SENTENCE_ANALYZER), TweetParser.class);
            isDebug = Boolean.parseBoolean(properties.getProperty("debug", "false"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ClassifierProperty() {
    }


    private static <E> E getClass(String packageName, Class<E> type) throws ReflectiveOperationException {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends E>> allClasses = reflections.getSubTypesOf(type);
        for (Class clazz : allClasses) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                return (E) clazz.newInstance();
            }
        }
        return null;
    }

    public static FrequencyAnalyzer getFrequencyAnalyzer() {
        return frequencyAnalyzer;
    }

    public static void setFrequencyAnalyzer(FrequencyAnalyzer frequencyAnalyzer) {
        ClassifierProperty.frequencyAnalyzer = frequencyAnalyzer;
    }

    public static GrammarAnalyzer getGrammarAnalyzer() {
        return grammarAnalyzer;
    }

    public static void setGrammarAnalyzer(GrammarAnalyzer grammarAnalyzer) {
        ClassifierProperty.grammarAnalyzer = grammarAnalyzer;
    }

    public static MorphologicalAnalyzer getMorphologicalAnalyzer() {
        return morphologicalAnalyzer;
    }

    public static void setMorphologicalAnalyzer(MorphologicalAnalyzer morphologicalAnalyzer) {
        ClassifierProperty.morphologicalAnalyzer = morphologicalAnalyzer;
    }

    public static TweetParser getTweetParser() {
        return tweetParser;
    }

    public static void setTweetParser(TweetParser tweetParser) {
        ClassifierProperty.tweetParser = tweetParser;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean isDebug) {
        ClassifierProperty.isDebug = isDebug;
    }
}
