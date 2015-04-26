package com.samborskiy.entity.instances.functions.smile;

import com.samborskiy.entity.instances.functions.AttributeFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Whiplash on 26.04.2015.
 */
public abstract class SmileFunction extends AttributeFunction {

    protected static final List<SmileSequence> SMILES = new ArrayList<>();

    static {
        SMILES.add(new SmileSequence("happy_smiles", ":)", ":-)", "^_^", "=)"));
        SMILES.add(new SmileSequence("sad_smiles", ":(", ":-(", "=("));
        SMILES.add(new SmileSequence("winking_smiles", ";)", ";-)", "^_-"));
        SMILES.add(new SmileSequence("glasses_smiles", "8-)", "%)", "%-)", "8)"));
        SMILES.add(new SmileSequence("laugh_with_tears_smiles", ":')", ":'-)", ":,)", ":,-)", "=')", "=,)", ";)", ";-)"));
        SMILES.add(new SmileSequence("cry_smiles", ":'(", ":'-(", ":,(", ":,-(", ";(", ";-(", "='(", "=,("));
        SMILES.add(new SmileSequence("kiss_smiles", ":*", ":-*", "=*"));
        SMILES.add(new SmileSequence("surprised_smiles", "o_o", "o_0", "=o", "=0", "0_0"));
        SMILES.add(new SmileSequence("with_tongue_smiles", ":-b", ":-p", ":b", ":p", "=p", "=b", ";b", ";p"));
        SMILES.add(new SmileSequence("laugh_smiles", ":d", "xd", "=d", ";d"));
        SMILES.add(new SmileSequence("other_smiles", ":-[", "=[", ":3", ">_<", ":-/", ":-\\", "=/", "=\\"));
    }

    protected static class SmileSequence {

        private final String name;
        private final List<String> smiles;

        public SmileSequence(String name, String... smiles) {
            this.name = name;
            this.smiles = Arrays.asList(smiles);
        }

        public boolean match(String tweet, int index) {
            for (String smile : smiles) {
                if (tweet.startsWith(smile, index)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
