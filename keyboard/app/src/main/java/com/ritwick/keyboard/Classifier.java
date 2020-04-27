package com.ritwick.keyboard;
import android.content.Context;
import org.tensorflow.lite.support.common.FileUtil;
import java.io.IOException;
import java.nio.MappedByteBuffer;

import org.tensorflow.lite.Interpreter;

public class Classifier {
    private MappedByteBuffer tflite_model;
    Preprocessor preprocessor;
    public Classifier(Context context, String file) {

        try {
            tflite_model = FileUtil.loadMappedFile(context, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        preprocessor = new Preprocessor(context);
    }

    public float[][] classify(String str) {
        float[][] input;
        float[][] output = new float[1][1];

        Interpreter interpreter = new Interpreter(tflite_model);
        input = preprocessor.textToInputArray(str);
        interpreter.run(input, output);

        interpreter.close();
        return output;
    }
}