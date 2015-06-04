package com.samborskiy.classifier;

import com.samborskiy.classifier.attributes.AttributeFunction;
import com.samborskiy.classifier.fss.FeatureSelection;
import com.samborskiy.classifier.fss.InformationFeatureSelection;
import com.samborskiy.entity.Account;
import com.samborskiy.entity.Attribute;
import com.samborskiy.entity.analyzers.frequency.FrequencyAnalyzer;
import com.samborskiy.entity.analyzers.grammar.GrammarAnalyzer;
import com.samborskiy.entity.analyzers.morphological.MorphologicalAnalyzer;
import com.samborskiy.entity.analyzers.sentence.TweetParser;
import org.reflections.Reflections;
import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.OptionHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TwitterAccountClassifier {

    private FrequencyAnalyzer frequencyAnalyzer = FrequencyAnalyzer.get();
    private GrammarAnalyzer grammarAnalyzer = GrammarAnalyzer.get();
    private MorphologicalAnalyzer morphologicalAnalyzer = MorphologicalAnalyzer.get();
    private TweetParser tweetParser = TweetParser.get();

    private Classifier classifier = new RandomForest();

    {
        try {
            ((OptionHandler) classifier).setOptions(new String[]{"-I", "104"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private FeatureSelection featureSelection = new InformationFeatureSelection(36);

    private Set<weka.core.Attribute> attributes;

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

    public void build() throws Exception {
        Instances subsetInstances = featureSelection.select(instances);
        attributes = new HashSet<>();
        for (int i = 0; i < subsetInstances.numAttributes(); i++) {
            attributes.add(subsetInstances.attribute(i));
        }
        classifier.buildClassifier(subsetInstances);
    }

    public FeatureSelection findFss() {
        // TODO: find fss algo
        return null;
    }

    public int getClassId(Account account) throws Exception {
        setAttributes(account, getAttributeFunctions());
        filterAccount(account);
        Instance instance = accountToInstance(account);
        return (int) classifier.classifyInstance(instance);
    }

    private Instance accountToInstance(Account account) {
        // TODO: cast account to instance
    }

    private void filterAccount(Account account) {
        account.removeIf((attribute) -> {
            for (weka.core.Attribute attr : attributes) {
                if (attr.name().equals(attribute.getName())) {
                    return false;
                }
            }
            return true;
        });
    }

    private void initializeInstances() throws IOException {
        BufferedReader dataFile = new BufferedReader(new FileReader(relationName + ".arff"));
        instances = new Instances(dataFile);
        instances.setClassIndex(instances.numAttributes() - 1);
    }

    private void setAttributes(List<Account> accounts) throws ReflectiveOperationException {
        List<AttributeFunction> functions = getAttributeFunctions();
        for (Account account : accounts) {
            setAttributes(account, functions);
        }
    }

    private void setAttributes(Account account, List<AttributeFunction> functions) {
        for (AttributeFunction function : functions) {
            account.addAttrs(function.apply(account.getTweets()));
            account.addAttr(new Attribute(account.getFollowers(), "follower_number"));
            account.addAttr(new Attribute(account.getFollowing(), "following_number"));
            account.addAttr(new Attribute(account.getVerified(), "is_verified"));
            account.addAttr(new Attribute(account.getFavourite(), "favourite_number"));
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

    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
    }
}
