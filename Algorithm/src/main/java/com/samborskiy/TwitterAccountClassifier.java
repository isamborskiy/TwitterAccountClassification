package com.samborskiy;

import com.samborskiy.attributes.AttributeFunction;
import com.samborskiy.attributes.partofspeech.PartOfSpeechFunction;
import com.samborskiy.attributes.partofspeech.PartOfSpeechPerAccount;
import com.samborskiy.attributes.partofspeech.ParticlePerAccount;
import com.samborskiy.attributes.partofspeech.TweetsWithPartOfSpeech;
import com.samborskiy.attributes.sign.AvrSignsNumber;
import com.samborskiy.attributes.sign.SignFunction;
import com.samborskiy.attributes.sign.SignLatitude;
import com.samborskiy.attributes.sign.SignPerAccount;
import com.samborskiy.attributes.sign.SignsPerAccount;
import com.samborskiy.attributes.sign.TweetsWithSign;
import com.samborskiy.attributes.sign.TweetsWithSigns;
import com.samborskiy.attributes.smile.DifferentSmiles;
import com.samborskiy.attributes.smile.SmileFunction;
import com.samborskiy.attributes.smile.SmilePerAccount;
import com.samborskiy.attributes.smile.TweetsWithSmile;
import com.samborskiy.attributes.smile.TweetsWithSmiles;
import com.samborskiy.entity.Account;
import com.samborskiy.fss.FeatureSelection;
import com.samborskiy.fss.NoFeatureSelection;
import com.samborskiy.entity.ClassifierProperty;
import org.reflections.Reflections;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.OptionHandler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TwitterAccountClassifier {

    private Classifier classifier = new RandomForest();

    {
        try {
            ((OptionHandler) classifier).setOptions(new String[]{"-I", "104"});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        classifier.buildClassifier(instances);
    }

    public FeatureSelection findFeatureSubsetSelectionAlgorithm(int foldNumber) throws Exception {
        List<FeatureSelection> algorithms = getFeaturesSelectionAlgorithms();
        Classifier[] copiedClassifiers = AbstractClassifier.makeCopies(classifier, algorithms.size());
        FeatureSelection maxFSS = null;
        double maxFMeasure = 0.;
        for (int i = 0; i < copiedClassifiers.length; i++) {
            double fMeasure = new Test(copiedClassifiers[i], algorithms.get(i).select(instances)).crossValidation(foldNumber);
            if (fMeasure > maxFMeasure) {
                maxFMeasure = fMeasure;
                maxFSS = algorithms.get(i);
            }
        }
        return maxFSS;
    }

    public int getClassId(Account account) throws Exception {
        setAttributes(account, getAttributeFunctions());
        return (int) classifier.classifyInstance(account.toInstance());
    }

    private void initializeInstances() throws IOException {
        try (BufferedReader dataFile = new BufferedReader(new FileReader(relationName + ".arff"))) {
            instances = new Instances(dataFile);
            instances.setClassIndex(instances.numAttributes() - 1);
        }
    }

    private void setAttributes(List<Account> accounts) throws ReflectiveOperationException {
        List<AttributeFunction> functions = getAttributeFunctions();
        for (Account account : accounts) {
            setAttributes(account, functions);
        }
    }

    private void setAttributes(Account account, List<AttributeFunction> functions) {
        for (AttributeFunction function : functions) {
            account.addAll(function.apply(account));
        }
    }

    private List<AttributeFunction> getAttributeFunctions() throws ReflectiveOperationException {
        List<AttributeFunction> accountFunctions = new ArrayList<>();
        // ------- get all attributes function from com.samborskiy.attributes package
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.attributes.length"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.attributes.grammar"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.attributes.vocabulary"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.attributes.hashtag"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.attributes.reference"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.attributes.personal"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.attributes.frequency"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.attributes.link"));
        accountFunctions.addAll(getTweetAttributes("com.samborskiy.attributes.meta"));
        accountFunctions.add(new ParticlePerAccount());
        accountFunctions.addAll(PartOfSpeechFunction.SEQUENCES.stream().map(PartOfSpeechPerAccount::new).collect(Collectors.toList()));
        accountFunctions.addAll(PartOfSpeechFunction.SEQUENCES.stream().map(TweetsWithPartOfSpeech::new).collect(Collectors.toList()));
        accountFunctions.add(new AvrSignsNumber());
        accountFunctions.add(new SignLatitude());
        accountFunctions.add(new SignsPerAccount());
        accountFunctions.add(new TweetsWithSigns());
        accountFunctions.addAll(SignFunction.SIGNS.stream().map(SignPerAccount::new).collect(Collectors.toList()));
        accountFunctions.addAll(SignFunction.SIGNS.stream().map(TweetsWithSign::new).collect(Collectors.toList()));
        accountFunctions.add(new DifferentSmiles());
        accountFunctions.add(new TweetsWithSmiles());
        accountFunctions.addAll(SmileFunction.SMILES.stream().map(SmilePerAccount::new).collect(Collectors.toList()));
        accountFunctions.addAll(SmileFunction.SMILES.stream().map(TweetsWithSmile::new).collect(Collectors.toList()));
        // ------- filter attributes by removing extra (using properties of project)
        if (ClassifierProperty.getAttributes() != null) {
            accountFunctions.removeIf(attribute -> !ClassifierProperty.getAttributes().contains(attribute.getName()));
        }
        // -------
        return accountFunctions;
    }

    private List<AttributeFunction> getTweetAttributes(String packageName) throws ReflectiveOperationException {
        return getClasses(packageName, AttributeFunction.class);
    }

    private List<FeatureSelection> getFeaturesSelectionAlgorithms() throws ReflectiveOperationException {
        List<FeatureSelection> featureSelections = new ArrayList<>();
        featureSelections.add(new NoFeatureSelection());
        featureSelections.addAll(getFeaturesSelectionAlgorithms("com.samborskiy.fss.selection"));
        featureSelections.addAll(getFeaturesSelectionAlgorithms("com.samborskiy.fss.extraction"));
        return featureSelections;
    }

    private List<FeatureSelection> getFeaturesSelectionAlgorithms(String packageName) throws ReflectiveOperationException {
        return getClasses(packageName, FeatureSelection.class);
    }

    private <E> List<E> getClasses(String packageName, Class<E> type) throws ReflectiveOperationException {
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends E>> allClasses = reflections.getSubTypesOf(type);
        List<E> classes = new ArrayList<>();
        for (Class clazz : allClasses) {
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                classes.add((E) clazz.newInstance());
            }
        }
        return classes;
    }

    public void setClassifier(Classifier classifier) {
        this.classifier = classifier;
    }
}
