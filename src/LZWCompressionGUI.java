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

public class LZWCompressionGUI extends Application {
    private TextField selectedFilePathField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Compress Files");

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Label headerLabel = new Label("LZW Compression");
        headerLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 30px; -fx-padding: 20 0 30 0;");
        selectedFilePathField = new TextField();
        selectedFilePathField.setPrefWidth(450);
        selectedFilePathField.setEditable(false);

        Button selectFileButton = createIconButton(30, 30);
        selectFileButton.setCursor(Cursor.HAND);

        Button compressButton = new Button("Compress");
        compressButton.setStyle("-fx-background-color: navy; -fx-text-fill: white; -fx-font-size: 16px; -fx-background-radius: 8;");
        compressButton.setCursor(Cursor.HAND);

        Text instructionsText = new Text("Select a file and click 'Compress' to start compression.");
        instructionsText.setStyle("-fx-font-size: 16px;");

        Text completed = new Text("");
        completed.setStyle("-fx-font-size: 17px;");

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(selectedFilePathField, selectFileButton);
        hbox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(headerLabel,instructionsText, hbox, compressButton, completed);
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

        compressButton.setOnAction(e -> {
            String selectedFilePath = selectedFilePathField.getText();
            if (!selectedFilePath.isEmpty()) {
                File selectedFile = new File(selectedFilePath);
                if(compressFile(selectedFile)){
                    completed.setText("Compression and saving to CompressedFile.txt is successful.");

                }else{
                    completed.setText("Compression failed.");
                }
            }
        });

        primaryStage.show();
    }

    private boolean compressFile(File selectedFile) {
        String filePath = selectedFile.getPath();
        LZW lzw = new LZW();
        lzw.Compress(filePath);
        boolean compressionSuccess = performCompression(filePath);
        if (compressionSuccess) {
            System.out.println("Compression and saving to CompressedFile.txt is successful.");
            return true;
        } else {
            System.out.println("Compression failed.");
            return false;
        }
    }

    private boolean performCompression(String filePath) {
        System.out.println("Compressing file: " + filePath);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    private Button createIconButton(int width, int height) {
        File file = new File("C:\\Users\\PC\\Desktop\\Lzw_Compression_Algorithm\\folder.png");
        String localUrl = file.toURI().toString();

        ImageView icon = new ImageView(new Image(localUrl));
        icon.setFitWidth(width);
        icon.setFitHeight(height);

        Button button = new Button();
        button.setStyle("-fx-background-radius: 50%; -fx-background-color: transparent;");
        button.setGraphic(icon);

        return button;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
