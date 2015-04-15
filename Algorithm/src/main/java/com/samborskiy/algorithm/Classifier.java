package com.samborskiy.algorithm;

import com.samborskiy.entity.Language;
import com.samborskiy.entity.instances.Instance;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Abstract classifier.
 *
 * @author Whiplash
 */
public abstract class Classifier<E extends Instance> {

    protected final Language language;

    /**
     * Restores instance of {@code Classifier} from file.
     *
     * @param is where will be restored classifier
     */
    public Classifier(Language language, InputStream is) {
        this(language);
        read(is);
    }

    /**
     * Creates instance of classifier with initialize {@code data}.<p>
     * Recommends to train the classifier call method {@code train}.
     */
    public Classifier(Language language) {
        this.language = language;
    }

    /**
     * Trains classifier using {@code data}.
     *
     * @param data training sample
     */
    public abstract void train(List<List<E>> data);

    public abstract void clear();

    /**
     * Returns class id of instance.
     *
     * @param element instance for which will look for class id
     * @return class id of instance
     */
    public abstract int getClassId(List<Instance> element);

    /**
     * Returns key of max value in map.
     *
     * @param map map where it will be searched
     * @param <K> type of map key
     * @param <V> type of map value
     * @return key of max value in map
     */
    protected <K, V extends Comparable<V>> K maxElementKey(Map<K, V> map) {
        V maxValue = null;
        K maxKey = null;
        for (K key : map.keySet()) {
            if (maxValue == null || map.get(key).compareTo(maxValue) == 1) {
                maxKey = key;
                maxValue = map.get(key);
            }
        }
        return maxKey;
    }

    /**
     * Restores instance of {@code Classifier} from file.<p>
     * See {@link #Classifier(java.io.InputStream)}.
     *
     * @param is where will be restored classifier
     */
    protected abstract void read(InputStream in);

    /**
     * Writes snapshot of classifier to stream.
     *
     * @param out where will be stored classifier
     */
    public abstract void write(OutputStream out);
}
