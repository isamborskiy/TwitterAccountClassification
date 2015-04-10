package com.samborskiy.algorithm.svm;

import com.samborskiy.algorithm.Classifier;
import com.samborskiy.entity.instances.Instance;
import com.samborskiy.entity.Language;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Random;

public class SVMClassifier<E extends Instance> extends Classifier<E> {

    private static final int MAX_PASSES = 1;
    private static final double EPS = 10e-8;
    private static final Random random = new Random();

    protected final Kernel<E> kernel;
    protected final int c;

    protected List<E> data;
    protected int[] modifiedClassIds;
    protected double[] alphas;
    protected double b;
    protected int size;

    public SVMClassifier(Language language, InputStream is, Kernel<E> kernel, int c) {
        super(language, is);
        this.kernel = kernel;
        this.c = c;
    }

    public SVMClassifier(Language language, Kernel<E> kernel, int c) {
        super(language);
        this.kernel = kernel;
        this.c = c;
    }

    @Override
    public void train(List<E> data) {
        this.data = data;
        this.size = data.size();
        this.modifiedClassIds = new int[size];
        for (int i = 0; i < size; i++) {
            int classId = data.get(i).getClassId();
            modifiedClassIds[i] = getModifyClassId(classId);
        }
        alphas = smo();
    }

    private int getModifyClassId(int classId) {
        switch (classId) {
            case 0:
                return -1;
            case 1:
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public void clear() {
        data = null;
        size = 0;
        modifiedClassIds = null;
        alphas = null;
        b = 0.;
    }

    @Override
    public int getClassId(Instance element) {
        return 0;
    }

    @Override
    protected void read(InputStream in) {
        throw new UnsupportedOperationException("Method not realised");
    }

    @Override
    public void write(OutputStream out) {
        throw new UnsupportedOperationException("Method not realised");
    }

    private double[] smo() {
        alphas = new double[size];
        b = 0;
        int passes = 0;
        while (passes < MAX_PASSES) {
            boolean hasChanged = false;
            for (int i = 0; i < size; i++) {
                double ei = calculateE(i);
                if (modifiedClassIds[i] * ei < -EPS && alphas[i] < c ||
                        modifiedClassIds[i] * ei > EPS && alphas[i] > 0) {
                    int j;
                    do {
                        j = random.nextInt(size);
                    } while (j == i);
                    double ej = calculateE(j);
                    double oldAlphaI = alphas[i];
                    double oldAlphaJ = alphas[j];
                    double l, h;
                    if (modifiedClassIds[i] != modifiedClassIds[j]) {
                        l = Math.max(0, oldAlphaJ - oldAlphaI);
                        h = Math.min(c, c + oldAlphaJ - oldAlphaI);
                    } else {
                        l = Math.max(0, oldAlphaI + oldAlphaJ - c);
                        h = Math.min(c, oldAlphaI + oldAlphaJ);
                    }
                    if (l == h) {
                        continue;
                    }
                    E xi = data.get(i);
                    E xj = data.get(j);
                    double n = 2 * kernel.eval(xi, xj) - kernel.eval(xi, xi) - kernel.eval(xj, xj);
                    if (n >= 0) {
                        continue;
                    }
                    alphas[j] -= modifiedClassIds[j] * (ei - ej) / n;
                    if (alphas[j] > h) {
                        alphas[j] = h;
                    } else if (alphas[j] < l) {
                        alphas[j] = l;
                    }
                    if (Math.abs(alphas[j] - oldAlphaJ) < EPS) {
                        continue;
                    }
                    alphas[i] += modifiedClassIds[i] * modifiedClassIds[j] * (oldAlphaJ - alphas[j]);
                    double b1 = b - ei - modifiedClassIds[i] * (alphas[i] - oldAlphaI) * kernel.eval(xi, xi) -
                            modifiedClassIds[j] * (alphas[j] - oldAlphaJ) * kernel.eval(xi, xj);
                    double b2 = b - ej - modifiedClassIds[i] * (alphas[i] - oldAlphaI) * kernel.eval(xi, xj) -
                            modifiedClassIds[j] * (alphas[j] - oldAlphaJ) * kernel.eval(xj, xj);
                    if (alphas[i] > 0 && alphas[i] < c) {
                        b = b1;
                    } else if (alphas[j] > 0 && alphas[j] < c) {
                        b = b2;
                    } else {
                        b = (b1 + b2) / 2;
                    }
                    hasChanged = true;
                }
            }
            if (!hasChanged) {
                passes++;
            } else {
                passes = 0;
            }
        }
        return alphas;
    }

    private double calculateE(int i) {
        return function(data.get(i)) - modifiedClassIds[i];
    }

    protected double function(E instance) {
        double result = 0;
        for (int i = 0; i < size; i++) {
            E point = data.get(i);
            result += alphas[i] * modifiedClassIds[i] * kernel.eval(point, instance);
        }
        result += b;
        return result;
    }
}
