import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.geometry.Pos;
import java.io.File;
import java.io.IOException;

public class LZWDecompressionGUI extends Application {
    private TextField selectedFilePathField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Decompress Files");

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.bin"));

        // Back btn
        Button backBtn = createIconButton(48, 58, "back.png");
        backBtn.setCursor(Cursor.HAND);
        backBtn.setStyle("-fx-background-color: transparent;-fx-padding: 20 0 30 50;");


        Label headerLabel = new Label("LZW Decompression");
        headerLabel.setStyle("-fx-font-family: 'Comic Sans MS';-fx-font-weight: bold;-fx-font-size: 35px; -fx-padding: 0 0 20 0;");
        selectedFilePathField = new TextField();
        selectedFilePathField.setPrefWidth(450);
        selectedFilePathField.setEditable(false);

        Button selectFileButton = createIconButton(30, 30, "folder.png");
        selectFileButton.setCursor(Cursor.HAND);
        selectFileButton.setStyle("-fx-background-radius: 50%; -fx-background-color: transparent;");


        Button decompressButton = new Button("Decompress");
        decompressButton.setStyle("-fx-background-color: navy; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 8;");
        decompressButton.setCursor(Cursor.HAND);

        Text instructionsText = new Text("Select a file and click 'Decompress' to start decompression.");
        instructionsText.setStyle("-fx-font-size: 16px;");

        HBox BackBox = new HBox(10);
        BackBox.getChildren().addAll(backBtn);
        BackBox.setAlignment(Pos.TOP_LEFT);

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(selectedFilePathField, selectFileButton);
        hbox.setAlignment(Pos.CENTER);

        Text completed = new Text("");
        completed.setStyle("-fx-font-size: 16px;");

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(BackBox,headerLabel,instructionsText, hbox, decompressButton, completed);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 700, 400);
        vbox.setBackground(new Background(new BackgroundFill(Color.SKYBLUE.desaturate(), CornerRadii.EMPTY, Insets.EMPTY)));
        primaryStage.setScene(scene);

        selectFileButton.setOnAction(e -> {
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                selectedFilePathField.setText(selectedFile.getPath());
            }
        });

        decompressButton.setOnAction(e -> {
            String selectedFilePath = selectedFilePathField.getText();
            if (!selectedFilePath.isEmpty()) {
                File selectedFile = new File(selectedFilePath);
                try {
                    if (decompressFile(selectedFile)) {
                        completed.setText("Decompression and saving to DecompressedFile.txt is successful.");
                        completed.setStyle("-fx-text-fill: green; -fx-font-size: 18px;");

                    } else {
                        completed.setText("Decompression failed.");
                        completed.setStyle("-fx-text-fill: red; -fx-font-size: 18px;");
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        backBtn.setOnAction(e -> {
            // Switch to the compression screen
            LZWCompressionMain mainGUI = new LZWCompressionMain();
            mainGUI.start(new Stage());
            primaryStage.close();
        });

        primaryStage.show();
    }

    private boolean decompressFile(File selectedFile) throws IOException {
        String filePath = selectedFile.getPath();
        LZW lzw = new LZW();
        lzw.Decompress(filePath);
        boolean compressionSuccess = performDecompression(filePath);
        if (compressionSuccess) {
            System.out.println("Decompression and saving to CompressedFile.txt is successful.");
            return true;
        } else {
            System.out.println("Decompression failed.");
            return false;
        }
    }

    private boolean performDecompression(String filePath) {
        System.out.println("decompressing file: " + filePath);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    private Button createIconButton(int width, int height, String filename) {
        File file = new File(filename);
        String localUrl = file.toURI().toString();

        ImageView icon = new ImageView(new Image(localUrl));
        icon.setFitWidth(width);
        icon.setFitHeight(height);

        Button button = new Button();
        button.setGraphic(icon);

        return button;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
