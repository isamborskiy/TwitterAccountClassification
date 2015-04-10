package com.samborskiy.tests;

public class SVMTest {

//    private final int c;
//
//    public SVMTest(int c) {
//        super();
//        this.c = c;
//    }
//
//    @Override
//    public String getName() {
//        return "SVM";
//    }
//
//    @Override
//    public Statistics crossValidationAccount(Configuration configuration, int foldCount, int round, List<Account> sample) {
//        Classifier<Account> classifier = new SVMClassifier<>(configuration.getLang(), new InnerProductKernel<>(new LengthMetric<>()), c);
//        TestMachine<Account> machine = new TestMachine<>(classifier, sample, configuration.getTypes().size());
//        return machine.crossValidationTest(foldCount, round, false);
//    }
//
//    @Override
//    public Statistics crossValidationTweet(Configuration configuration, int foldCount, int round, List<Tweet> sample) {
//        Classifier<Tweet> classifier = new SVMClassifier<>(configuration.getLang(), new InnerProductKernel<>(new LengthMetric<>()), c);
//        TestMachine<Tweet> machine = new TestMachine<>(classifier, sample, configuration.getTypes().size());
//        return machine.crossValidationTest(foldCount, round, false);
//    }
}
