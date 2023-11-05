import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.*;

public class LZWCompressionMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("LZW Compression");

        Label headerLabel = new Label("Welcome to LZW Compression Algorithm!");
        headerLabel.setStyle("-fx-font-family: 'Comic Sans MS';-fx-font-weight: bold; -fx-font-size: 30px; -fx-padding: 20 0 30 0; -fx-letter-spacing:4px;");

        Button compressButton = new Button("Compress");
        Button decompressButton = new Button("Decompress");

        compressButton.setStyle("-fx-background-color: navy; -fx-text-fill: white; -fx-font-size: 19px; -fx-background-radius: 8;");
        decompressButton.setStyle("-fx-background-color: navy; -fx-text-fill: white; -fx-font-size: 19px; -fx-background-radius: 8;");
        decompressButton.setCursor(Cursor.HAND);
        compressButton.setCursor(Cursor.HAND);

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(headerLabel, compressButton, decompressButton);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 700, 300);
        vbox.setBackground(new Background(new BackgroundFill(Color.SKYBLUE.desaturate(), CornerRadii.EMPTY, Insets.EMPTY)));
        primaryStage.setScene(scene);

        compressButton.setOnAction(e -> {
            // Switch to the compression screen
            LZWCompressionGUI compressionGUI = new LZWCompressionGUI();
            compressionGUI.start(new Stage());
            primaryStage.close(); // Close the choice screen
        });

        decompressButton.setOnAction(e -> {
            // Switch to the decompression screen
            LZWDecompressionGUI decompressionGUI = new LZWDecompressionGUI();
            decompressionGUI.start(new Stage());
            primaryStage.close(); // Close the choice screen
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
