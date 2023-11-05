import java.io.*;
import java.sql.ClientInfoStatus;
import java.util.*;

class Compress {
    String filePath;
    String data;
    List<Integer> compressedCode;
    String decompressed_code;

    public Compress(){
    }

    public Compress(String filePath){
        this.filePath = filePath;
        readFromFile();
        compress();
        saveCompressedFile();
    }
    public void compress(){
        /*number of ASCII symbols*/
        int dictionarySize = 128;

        // Initialize dictionary <pattern, code>
        Map<String, Integer> dictionary = new HashMap<>();
        /*Save all ASCII symbols in the Map*/
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

    public void saveDeCompressedFile() {
        try (FileWriter fileWriter = new FileWriter("DecompressedFile.txt");
             PrintWriter printWriter = new PrintWriter(fileWriter)) {
            fileWriter.write(decompressed_code);
            System.out.println("Decompressed data saved to DecompressedFile successfully");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the list to DecompressedFile");
        }
    }

    /*Description:
     *   This function for user interaction
     *   It gives the local variable (filePath) the compressed file
     *   the user calls Decompress function that reads from a file
     *   calls the decompression function and writes the data to a file
    */
    public void Decompress(String filePath) throws IOException {
        this.filePath = filePath;
        Decompression();
        saveDeCompressedFile();
    }

    /*Description:
     *   Read data from a file using FileReader
     *   Decompress the file using LZW decompression algorithm
     */
    public void Decompression() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.filePath));
        String line;
        String decompressed = "";
        List<String> patterns = new ArrayList<String>();

        /*number of ASCII symbols*/
        int dictionarySize = 128;

        // Initialize dictionary <pattern, code>
        Map<String, Integer> dictionary = new HashMap<String, Integer>();
        /*Save all ASCII symbols in the Map*/
        for (int i = 0; i < dictionarySize; i++) {
            dictionary.put(String.valueOf((char) i), i);
        }

        int code;
        /*read the first line in the compressed file and decompress it*/
        line = reader.readLine();
        /*turn the index value of the first line to character*/
        code = Integer.parseInt(line);
        /*add the decompressed char to decompressed variable*/
        decompressed += (char) (code);
        /*save the decompressed patterns */
        patterns.add(decompressed);
        /*
         * variable to keep track of previous decoded pattern
         * counter has the index of the last encoded pattern
         * */
        int counter = 0;
        boolean code_found = false;

        /*read a line from the compressed file*/
        while ((line = reader.readLine()) != null) {
            code = Integer.parseInt(line);
            /*
             * search for the code in the dictionary
             * if it exists add it to decompressed string
             * and add the previous pattern and the first char in the found one
             */
            for (Map.Entry<String, Integer> entry : dictionary.entrySet()) {
                if (entry.getValue().equals(code)) {
                    String temp_str = entry.getKey();
                    decompressed += temp_str;
                    /*
                     * add the new pattern to patterns list
                     * new patterns = previous pattern + the first char of the found pattern
                     */
                    dictionary.put(patterns.get(counter) + temp_str.charAt(0), dictionarySize++);
                    patterns.add(temp_str);
                    code_found = true;
                    break;
                }
                code_found = false;
            }
            /* if the code of a pattern not found in the dictionary
             * then take the previous pattern + the first character of that pattern as the new pattern
             */
            if (!code_found) {
                String temp_str = patterns.get(counter);
                decompressed += temp_str + temp_str.charAt(0);
                dictionary.put(temp_str + temp_str.charAt(0), dictionarySize++);
                /*add that pattern to the list*/
                patterns.add(temp_str + temp_str.charAt(0));
            }
            counter++;
        }
        /*save the decompressed string*/
        decompressed_code = decompressed;

    }
}

