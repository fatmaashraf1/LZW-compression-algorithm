import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to LZW Compression Algorithm");
        System.out.println("Enter the file path that you want to compress: ");
        Scanner in = new Scanner(System.in);
        String filePath = in.nextLine();
        File file = new File(filePath);

        if (file.exists()){
            new Compress(filePath);
        } else {
            System.out.println("File path does not exist: " + filePath);
        }
    }
}