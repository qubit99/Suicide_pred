package com.ritwick.keyboard;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

class Preprocessor {
    private static final int MODEL_INPUT_SHAPE = 150;
    private Map<String, Integer> word_index;

    public Preprocessor() {
        String strCurrentLine;
        word_index = new HashMap<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("assets/w.txt"));
            while ((strCurrentLine = br.readLine()) != null) {
                String[] str = strCurrentLine.split(" ");
                word_index.put(str[0], Integer.parseInt(str[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String cleanText(String text){
        String clean_text = text.toLowerCase();
        clean_text = clean_text.replaceAll("([A-Za-z]+[\\d@]+[\\w@]*|[\\d@]+[A-Za-z]+[\\w@]*)+", "");
        clean_text = clean_text.replaceAll("<!--?.*?-->", "");
        clean_text = clean_text.replaceAll("[\\W|\\d]+", " ");
        clean_text = clean_text.trim();
        return clean_text;
    }
    public float[][] textToInputArray(String text) {
        float[][] input = new float[1][MODEL_INPUT_SHAPE];
        String clean_text = cleanText(text);
        String[] words = clean_text.split(" ");

        for(int i=0;i<Math.min(MODEL_INPUT_SHAPE, words.length);++i) {
            if (word_index.get(words[i]) != null) {
                input[0][i] = (float) word_index.get(words[i]);
            } else {
                input[0][i] = 1.0f;
            }
        }
        return input;
    }
}