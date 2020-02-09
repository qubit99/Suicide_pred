import java.io.File;
import org.tensorflow.lite.Interpreter;

public class Classifier {
    File tflite_model;
    Preprocessor preprocessor;
    public Classifier(String file) {
        tflite_model = new File(file);
        preprocessor = new Preprocessor();
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
