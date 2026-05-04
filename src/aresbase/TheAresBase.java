package aresbase;

import aresbase.manager.ResourceManager;
import aresbase.model.Resource;
import aresbase.processors.SimulationController;
import aresbase.tasks.ColonyTask;
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


public class TheAresBase extends Application {
    private Stage stage;
    private SimulationController controller;
    private Timeline timeline;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("The Ares Base");
        GridPane root = new GridPane();
        ResourceManager rm = new ResourceManager();
        this.controller = new SimulationController(rm);
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        ListView<ColonyTask> listView = new ListView<>();
        ComboBox<Resource> menu = new ComboBox<>();
        ComboBox<Integer> unit = new ComboBox<>();
        ObservableList<ColonyTask> tasks_ui = FXCollections.observableArrayList();
        ObservableList<Map.Entry<Resource, Integer>> resource_data = FXCollections.observableArrayList();
        HBox hBox = new HBox();
        HBox hb = new HBox();
        HBox hb1 = new HBox();
        HBox hb2 = new HBox();
        Button execute = new Button("Execute");
        Label status = new Label("Executing...");
        Label result = new Label("Result:");
        Label unitLabel = new Label("Choose quantity");
        Button buy = new Button("Buy");
        Label message = new Label("Bought!");
        Label menuBar = new Label("Store");
        Label display_count = new Label("Choose a resource");
        TextArea logs = new TextArea();
        controller.loadState();
        logs.setText(controller.getLogs());
        logs.setScrollTop(Double.MAX_VALUE);

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


        col1.setPercentWidth(60);
        col2.setPercentWidth(40);
        row1.setPercentHeight(60);
        row2.setPercentHeight(40);
        root.getColumnConstraints().addAll(col1, col2);
        root.getRowConstraints().addAll(row1, row2);

        try {
            Image star = new Image("/resources/star.png");
            stage.getIcons().add(star);
        } catch (Exception e) {
            System.out.println("Icon not loaded: " + e.getMessage());
        }

        listView.setItems(tasks_ui);
        resource_data.setAll(rm.getStock().entrySet());

        this.timeline = new Timeline(new KeyFrame(Duration.seconds(3), _ -> {
            controller.spawnRandomTask();
            tasks_ui.setAll(controller.getTaskQueue());
            listView.scrollTo(tasks_ui.size() - 1);
            logs.setText(controller.getLogs());
            logs.setScrollTop(Double.MAX_VALUE);
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

        colonyVitals.setItems(resource_data);
        colonyVitals.getColumns().add(resourceCol);
        colonyVitals.getColumns().add(amountCol);
        resourceCol.prefWidthProperty().bind(colonyVitals.widthProperty().multiply(0.6));
        amountCol.prefWidthProperty().bind(colonyVitals.widthProperty().multiply(0.4));

        result.setVisible(false);
        status.setVisible(false);
        hBox.getChildren().addAll(execute, status, result);

        execute.setOnAction(_ -> {
            status.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(_ -> {
                status.setVisible(false);
                result.setVisible(true);
            });
            pause.play();
            result.setText(controller.processNextTask());
            logs.setText(controller.getLogs());
            logs.setScrollTop(Double.MAX_VALUE);
            resource_data.setAll(rm.getStock().entrySet());
            tasks_ui.setAll(controller.getTaskQueue());
            listView.scrollTo(tasks_ui.size() - 1);
        });


        HBox.setMargin(execute, new Insets(0, 10, 10, 10));
        HBox.setMargin(status, new Insets(0, 10, 10, 10));
        HBox.setMargin(result, new Insets(0, 10, 10, 10));
        taskQueue.getChildren().addAll(listView, hBox);
        VBox.setMargin(listView, new Insets(15));
        colonyVitals.setPadding(new Insets(15));
        logs.setEditable(false);
        baseTerminal.getChildren().add(logs);
        VBox.setVgrow(logs, Priority.ALWAYS);
        VBox.setMargin(logs, new Insets(15));
        unit.setPromptText("number");
        menu.getItems().addAll(Resource.OXYGEN, Resource.RATIONS, Resource.SPARE_PARTS);
        unit.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        menu.setPromptText("Items");
        menu.setOnAction(_ -> {
            Resource selected = menu.getValue();
            if (unit.getValue() != null) {
                unitLabel.setText("Price is " + rm.getPrice(menu.getValue(), unit.getValue()));
            }
            display_count.setText("You have " + rm.getAmount(selected) + " " + selected);
        });
        unit.setOnAction(_ -> {
            if (menu.getValue() != null) {
                unitLabel.setText("Price is " + rm.getPrice(menu.getValue(), unit.getValue()));
            } else {
                unitLabel.setText(String.valueOf(unit.getValue()));
            }
        });

        buy.disableProperty().bind(menu.valueProperty().isNull().or(unit.valueProperty().isNull()));
        message.setVisible(false);

        buy.setOnAction(_ -> {
            message.setVisible(true);
            message.setText(controller.buy_resources(menu.getValue(), unit.getValue()));
            resource_data.setAll(rm.getStock().entrySet());
            logs.setText(controller.getLogs());
            logs.setScrollTop(Double.MAX_VALUE);
        });

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
        stage.setOnCloseRequest(event -> {
            event.consume();
            handleCloseRequest();
        });
        root.add(taskQueue, 0, 0);
        root.add(colonyVitals, 1, 0);
        root.add(cargoReplicator, 0, 1);
        root.add(baseTerminal, 1, 1);
        Scene scene = new Scene(root, 600, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void handleCloseRequest() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Closing Ares Base");
        alert.setHeaderText("What would you like to do before leaving?");
        alert.setContentText("Choose an option:");

        ButtonType saveButton = new ButtonType("Save State");
        ButtonType clearButton = new ButtonType("Start Fresh Next Time");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(saveButton, clearButton, cancelButton);
        alert.showAndWait().ifPresent(choice -> handleCloseChoice(choice, saveButton, clearButton));
    }

    private void handleCloseChoice(ButtonType choice, ButtonType saveButton, ButtonType clearButton) {
        if (choice == saveButton) {
            timeline.stop();
            controller.saveState();
            stage.close();
        } else if (choice == clearButton) {
            timeline.stop();
            controller.clearStateFile();
            stage.close();
        }
    }

}