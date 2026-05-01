package aresbase;

import aresbase.manager.ResourceManager;
import aresbase.model.Resource;
import aresbase.tasks.ColonyTask;
import aresbase.tasks.EngineeringTask;
import aresbase.tasks.LifeSupportTask;
import aresbase.tasks.ResearchTask;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Map;
import java.util.Random;


public class TheAresBase extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("The Ares Base");
        GridPane root = new GridPane();
        StringBuilder sb = new StringBuilder();
        TextArea logs = new TextArea();
        VBox taskQueue = new VBox(); /*(The Crisis List): This panel displays a visual list of pending
        maintenance tasks and colony emergencies.*/
        TableView<Map.Entry<Resource, Integer>> colonyVitals = new TableView<>(); /*(The State): This panel
        displays your current base credits and exactly how much of each critical resource you have left*/
        VBox cargoReplicator = new VBox(); /*(The Supply Chain): A dedicated area containing a dropdown
        menu of available resources and a "Synthesize/Buy" button*/
        VBox baseTerminal = new VBox(); /*(The System Log): A scrolling text area that acts as a live feed of
        everything happening on the station*/
        taskQueue.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(0), new Insets(0))));
        colonyVitals.setBackground(new Background(new BackgroundFill(Color.STEELBLUE, new CornerRadii(0), new Insets(0))));
        cargoReplicator.setBackground(new Background(new BackgroundFill(Color.CORNFLOWERBLUE, new CornerRadii(0), new Insets(0))));
        baseTerminal.setBackground(new Background(new BackgroundFill(Color.DEEPSKYBLUE, new CornerRadii(0), new Insets(0))));
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(60);
        col2.setPercentWidth(40);
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        row1.setPercentHeight(60);
        row2.setPercentHeight(40);
        root.getColumnConstraints().addAll(col1, col2);
        root.getRowConstraints().addAll(row1, row2);
        Image star = new Image("/resources/star.png");
        stage.getIcons().add(star);

        Random rand = new Random();
        ResourceManager rm = new ResourceManager();
        ObservableList<String> tasks_ui = FXCollections.observableArrayList();
        ListView<String> listView = new ListView<>();
        listView.setItems(tasks_ui);
        ObservableList<Map.Entry<Resource, Integer>> data = FXCollections.observableArrayList();
        data.setAll(rm.getStock().entrySet());

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3), _ -> {
            ColonyTask task;
            switch (rand.nextInt(3)) {
                case 0:
                    task = new EngineeringTask();
                    rm.addTask(task);
                    tasks_ui.add(task.toString());
                    sb.append(task).append(" added\n");
                    break;
                case 1:
                    task = new LifeSupportTask();
                    rm.addTask(task);
                    tasks_ui.add(task.toString());
                    sb.append(task).append(" added\n");
                    break;
                case 2:
                    task = new ResearchTask();
                    rm.addTask(task);
                    tasks_ui.add(task.toString());
                    sb.append(task).append(" added\n");
                    break;
            }
            logs.setText(sb.toString());

        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        TableColumn<Map.Entry<Resource, Integer>, String> resourceCol =
                new TableColumn<>("Resource");

        resourceCol.setCellValueFactory(cell ->
                new SimpleStringProperty(cell.getValue().getKey().toString())
        );

        TableColumn<Map.Entry<Resource, Integer>, Number> amountCol =
                new TableColumn<>("Amount");

        amountCol.setCellValueFactory(cell ->
                new SimpleIntegerProperty(cell.getValue().getValue())
        );

        colonyVitals.setItems(data);
        colonyVitals.getColumns().add(resourceCol);
        colonyVitals.getColumns().add(amountCol);

        resourceCol.prefWidthProperty().bind(colonyVitals.widthProperty().multiply(0.6));
        amountCol.prefWidthProperty().bind(colonyVitals.widthProperty().multiply(0.4));
        taskQueue.getChildren().add(listView);
        VBox.setMargin(listView, new Insets(15));
        colonyVitals.setPadding(new Insets(15));
        logs.setEditable(false);
        logs.setText("");
        baseTerminal.getChildren().add(logs);
        VBox.setVgrow(logs, Priority.ALWAYS);
        VBox.setMargin(logs, new Insets(15));
        ComboBox<String> menu = new ComboBox<>();
        ComboBox<String> unit = new ComboBox<>();
        unit.setPromptText("number");
        Label menuBar = new Label("Store");
        Label display_count = new Label("Choose a resource");
        menu.getItems().addAll("OXYGEN", "RATIONS", "SPARE_PARTS");
        unit.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        menu.setPromptText("Items");
        menu.setOnAction(_ -> {
            Resource selected = Resource.valueOf(menu.getValue());
                    //rm.getResourceQuantity(Resource.valueOf(menu.getValue()));
            display_count.setText("You have " + rm.getResourceQuantity(selected) + " " + selected);
        });
        Label unitLabel = new Label("Choose quantity");

        Button buy = new Button("Buy");
        buy.disableProperty().bind(menu.valueProperty().isNull().or(unit.valueProperty().isNull()));
        Label message = new Label("Bought!");
        message.setVisible(false);
        buy.setOnAction(_ -> {
            message.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(_ -> message.setVisible(false));
            pause.play();
            unitLabel.setText(unit.getValue() + " units of " + menu.getValue());
            rm.getStock().merge(Resource.valueOf(menu.getValue()), Integer.valueOf(unit.getValue()), Integer::sum);
            data.setAll(rm.getStock().entrySet());
            sb.append(unit.getValue()).append(" units of ").append(menu.getValue()).append(" bought!\n");
            logs.setText(sb.toString());
            display_count.setText("You have "+ rm.getResourceQuantity(Resource.valueOf(menu.getValue()))+" "+menu.getValue());

        });
        HBox hb = new HBox();
        HBox hb1 = new HBox();
        HBox hb2 = new HBox();
        hb.getChildren().addAll(menu, display_count);
        hb1.getChildren().addAll(unit, unitLabel);
        hb2.getChildren().addAll(buy, message);
        HBox.setMargin(unitLabel, new Insets(10));
        VBox.setMargin(menuBar, new Insets(10));
        HBox.setMargin(display_count, new Insets(10));
        VBox.setMargin(hb, new Insets(10));
        VBox.setMargin(hb1, new Insets(10));

        HBox.setMargin(buy, new Insets(10));
        HBox.setMargin(message, new Insets(10));
        cargoReplicator.getChildren().addAll(menuBar, hb, hb1, hb2);
        HBox.setMargin(display_count, new Insets(10));
        //HBox.setMargin(buy, new Insets(15));
        stage.setOnCloseRequest(_ -> timeline.stop());
        root.add(taskQueue, 0, 0);
        root.add(colonyVitals, 1, 0);
        root.add(cargoReplicator, 0, 1);
        root.add(baseTerminal, 1, 1);
        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.show();
    }
}