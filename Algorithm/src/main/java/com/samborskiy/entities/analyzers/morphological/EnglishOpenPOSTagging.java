package com.samborskiy.entities.analyzers.morphological;

import com.samborskiy.entities.PartOfSpeech;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Whiplash on 18.05.2015.
 */
public class EnglishOpenPOSTagging extends MorphologicalAnalyzer {

    private POSTaggerME tagger;

    public EnglishOpenPOSTagging() {
        try (InputStream modelStream = new FileInputStream("res/en/en-pos-maxent.bin")) {
            POSModel model = new POSModel(modelStream);
            tagger = new POSTaggerME(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*
COMMUNION,
CONJUNCTION,
PREPOSITION,
PARTICLE
 */

    @Override
    public PartOfSpeech get(String word) {
        String partOfSpeechTag = tagger.tag(new String[]{word})[0];
        if (equalTag(partOfSpeechTag, "NN", "NNS", "NNP", "NNPS")) {
            return PartOfSpeech.NOUN;
        } else if (equalTag(partOfSpeechTag, "VB", "VBD", "VBG", "VBN", "VBP", "VBZ")) {
            return PartOfSpeech.VERB;
        } else if (equalTag(partOfSpeechTag, "JJ", "JJR", "JJS")) {
            return PartOfSpeech.ADJECTIVE;
        } else if (equalTag(partOfSpeechTag, "RB", "RBR", "RBS", "WRB")) {
            return PartOfSpeech.ADVERB;
        } else if (equalTag(partOfSpeechTag, "PRP")) {
            return PartOfSpeech.PERSONAL_PRONOUN;
        } else if (equalTag(partOfSpeechTag, "PRP$", "WP", "WP$")) {
            return PartOfSpeech.PRONOUN;
        } else if (equalTag(partOfSpeechTag, "CD")) {
            return PartOfSpeech.NUMERAL;
        }
        return PartOfSpeech.PARTICLE;
    }

    private boolean equalTag(String tag, String... tags) {
        for (String tagValue : tags) {
            if (tag.equals(tagValue)) {
                return true;
            }
        }
        return false;
    }
}

/*
CC Coordinating conjunction
DT Determiner
EX Existential there
FW Foreign word
IN Preposition or subordinating conjunction
LS List item marker
MD Modal
PDT Predeterminer
POS Possessive ending
RP Particle
SYM Symbol
TO to
UH Interjection
WDT Wh­determiner
 */