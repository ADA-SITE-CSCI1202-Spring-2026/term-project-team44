package aresbase;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.awt.*;


public class TheAresBase extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("The Ares Base");
        GridPane root = new GridPane();
        VBox taskQueue = new VBox(); /*(The Crisis List): This panel displays a visual list of pending
        maintenance tasks and colony emergencies.*/
        VBox colonyVitals = new VBox(); /*(The State): This panel displays your current base credits and exactly
        how much of each critical resource you have left*/
        VBox cargoReplicator = new VBox(); /*(The Supply Chain): A dedicated area containing a dropdown
        menu of available resources and a "Synthesize/Buy" button*/
        VBox baseTerminal = new VBox(); /*(The System Log): A scrolling text area that acts as a live feed of
        everything happening on the station*/
        taskQueue.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(0), new Insets(0))));
        colonyVitals.setBackground(new Background(new BackgroundFill(Color.STEELBLUE, new CornerRadii(0), new Insets(0))));
        cargoReplicator.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, new CornerRadii(0), new Insets(0))));
        baseTerminal.setBackground(new Background(new BackgroundFill(Color.DEEPSKYBLUE, new CornerRadii(0), new Insets(0))));
        ColumnConstraints col = new ColumnConstraints();
        col.setPercentWidth(50);
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(50);
        root.getColumnConstraints().addAll(col, col);
        root.getRowConstraints().addAll(row, row);
        Image star = new Image("/resources/star.png");
        stage.getIcons().add(star);
        root.add(taskQueue, 0, 0);
        root.add(colonyVitals, 1, 0);
        root.add(cargoReplicator, 0, 1);
        root.add(baseTerminal, 1, 1);
        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.show();
    }
}