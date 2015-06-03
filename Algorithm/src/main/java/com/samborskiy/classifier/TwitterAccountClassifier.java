package com.samborskiy.classifier;

import com.samborskiy.classifier.attributes.AttributeFunction;
import com.samborskiy.entity.Account;
import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import org.reflections.Reflections;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TwitterAccountClassifier {

    private FrequencyAnalyzer frequencyAnalyzer = FrequencyAnalyzer.get();
    private GrammarAnalyzer grammarAnalyzer = GrammarAnalyzer.get();
    private MorphologicalAnalyzer morphologicalAnalyzer = MorphologicalAnalyzer.get();
    private TweetParser tweetParser = TweetParser.get();

    private Instances instances;
    private String relationName;

    public TwitterAccountClassifier(String relationName) throws IOException {
        this.relationName = relationName;
        initializeInstances();
    }

    public TwitterAccountClassifier(List<Account> accounts, String relationName) throws ReflectiveOperationException, IOException {
        this.relationName = relationName;
        setAttributes(accounts);
        AccountsToArff.write(accounts.stream()
                .map(Account::getClassId)
                .collect(Collectors.toSet()), accounts, relationName);
        initializeInstances();
    }

    private void initializeInstances() throws IOException {
        BufferedReader dataFile = new BufferedReader(new FileReader(relationName + ".arff"));
        instances = new Instances(dataFile);
        instances.setClassIndex(instances.numAttributes() - 1);
    }

    private void setAttributes(List<Account> accounts) throws ReflectiveOperationException {
        for (AttributeFunction function : getAttributeFunctions()) {
            for (Account account : accounts) {
                account.addAttrs(function.apply(account.getTweets()));
                account.addAttr(new Attribute(account.getFollowers(), "follower_number"));
                account.addAttr(new Attribute(account.getFollowing(), "following_number"));
                account.addAttr(new Attribute(account.getVerified(), "is_verified"));
                account.addAttr(new Attribute(account.getFavourite(), "favourite_number"));
            }
        }
    }

    private List<AttributeFunction> getAttributeFunctions() throws ReflectiveOperationException {
        List<AttributeFunction> accountFunctions = new ArrayList<>();
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.classifier.attributes.partofspeech"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.classifier.attributes.sign"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.classifier.attributes.smile"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.classifier.attributes.length"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.classifier.attributes.grammar"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.classifier.attributes.vocabulary"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.classifier.attributes.hashtag"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.classifier.attributes.reference"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.classifier.attributes.personal"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.classifier.attributes.frequency"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.classifier.attributes.link"));
        return accountFunctions;
    }

    private List<AttributeFunction> getTweetAttributes(String packageName) throws ReflectiveOperationException {
        return getClasses(packageName, AttributeFunction.class);
    }

    private <E> List<E> getClasses(String packageName, Class<E> type) throws ReflectiveOperationException {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends E>> allClasses = reflections.getSubTypesOf(type);
        List<E> classes = new ArrayList<>();
        for (Class clazz : allClasses) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                classes.add((E) clazz.getConstructors()[0].newInstance(frequencyAnalyzer, grammarAnalyzer, morphologicalAnalyzer, tweetParser));
            }
        }
        return classes;
    }

    public void setFrequencyAnalyzer(FrequencyAnalyzer frequencyAnalyzer) {
        this.frequencyAnalyzer = frequencyAnalyzer;
    }

    public void setGrammarAnalyzer(GrammarAnalyzer grammarAnalyzer) {
        this.grammarAnalyzer = grammarAnalyzer;
    }

    public void setMorphologicalAnalyzer(MorphologicalAnalyzer morphologicalAnalyzer) {
        this.morphologicalAnalyzer = morphologicalAnalyzer;
    }

    public void setTweetParser(TweetParser tweetParser) {
        this.tweetParser = tweetParser;
    }
}
