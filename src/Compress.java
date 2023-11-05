import java.io.*;
import java.util.*;

class Compress {
    String filePath;
    String data;
    List<Integer> compressedCode;

    public Compress(String filePath){
        this.filePath = filePath;
        readFromFile();
        compress();
        saveCompressedFile();
    }
    public void compress(){
        int dictionarySize = 128;

        // Initialize dictionary <pattern, code>
        Map<String, Integer> dictionary = new HashMap<>();
        for (int i = 0; i < dictionarySize; i++) {
            dictionary.put(String.valueOf((char) i), i);
        }

        String pattern = "";
        compressedCode = new ArrayList<>();

        for (char character: this.data.toCharArray() ) {
            String newPattern = pattern + character;
            if (dictionary.containsKey(newPattern)) {
                pattern = newPattern;
            } else {
                compressedCode.add(dictionary.get(pattern));
                dictionary.put(newPattern, dictionarySize++);
                pattern = String.valueOf(character);
            }
        }
        if (!pattern.isEmpty())
            compressedCode.add(dictionary.get(pattern));
    }

    public void saveCompressedFile() {
        try (FileWriter fileWriter = new FileWriter("CompressedFile.txt");
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            for (Integer number : this.compressedCode) {
                printWriter.println(number);
            }
            System.out.println("Compressed data saved to CompressedFile successfully");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the list to CompressedFile");
        }
    }


    public void readFromFile() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.filePath));
            StringBuilder str = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                str.append(line);
            }
            data = str.toString();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + this.filePath);
        }
    }
}

