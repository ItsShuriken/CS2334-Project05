import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;



public class Frame extends Application {

		@Override
	public void start(Stage appStage) throws Exception {
			
			//3 columns, 11 rows
			GridPane gPane = new GridPane();
			Scene scene = new Scene(gPane);
			
			HammingDist hamDist = new HammingDist();
			
			
			
			// (0,0) Label, "EnterHamming Dist: 
			Label hamDistLabel = new Label("Enter Hamming Dist:");
			// (4, 0) Label, Compare with: 
			Label compareLabel = new Label("Compare With:");
			
			// (6 - 10, 0) Labels, Distance (0-4) 
			Label Dist0Label = new Label("Distance 0:");
			Label Dist1Label = new Label("Distance 1:");
			Label Dist2Label = new Label("Distance 2:");
			Label Dist3Label = new Label("Distance 3:");
			Label Dist4Label = new Label("Distance 4:");
			
			// (2, 0) Button, "Show Station". from file, displays all with same hamming dist as selected station in dropdown box below.
			Button showStationButton = new Button("Show Station");
			
			// (5, 0) Button, "Calculate HD", Claculates the number of stations with the same hamming distance as the one selected and displays it in the boxes below.
			Button calcHamButton = new Button("Calculate HD");
			
			// (11, 0) Button, "Add Station", adds the station in the text box to the right to the list of stations, auto capitalizes for formatting
			Button addStationButton = new Button("Add Station");
			
			// (3, 0 - 1) giant text box, diaplays all stations with same ham dist
			TextArea stationOutputField = new TextArea();
			stationOutputField.setEditable(false);
			stationOutputField.setPrefRowCount(20);
			
			// (6 - 10, 1) Noneditable textbox that updates with the new hamming distance given the selected station.
			TextField hamDist0Text = new TextField();
			TextField hamDist1Text = new TextField();
			TextField hamDist2Text = new TextField();
			TextField hamDist3Text = new TextField();
			TextField hamDist4Text = new TextField();
			
			hamDist0Text.setEditable(false);
			hamDist1Text.setEditable(false);
			hamDist2Text.setEditable(false);
			hamDist3Text.setEditable(false);
			hamDist4Text.setEditable(false);
				
			// (11, 1) editable text box that can have a station inputed.
			TextField stationInputText = new TextField();
			stationInputText.setEditable(true);
			
			//(0,1) Non-editable textBox, Displays current slider value
			TextField sliderValText = new TextField();
			sliderValText.setEditable(false);
			
			
			// (1,0 - 1) slider, 1-4. when moved event to update text box above it.
			Slider slider = new Slider(0, 4, 1);
			slider.setBlockIncrement(1);
			slider.setMajorTickUnit(1);
			slider.setMinorTickCount(0);
			slider.setShowTickLabels(true);
			slider.setSnapToTicks(true);
			
			// (4, 1) Drop down box
			ObservableList<String> stationList = FXCollections.observableArrayList(hamDist.getStations());
			ComboBox stationComboBox = new ComboBox(stationList);
			stationComboBox.getSelectionModel().selectFirst();
			
			//add stuff to gridPane
			gPane.add(hamDistLabel, 0, 0);
			gPane.add(sliderValText, 1, 0);
			
			gPane.add(slider, 0, 1, 2, 1);
			
			gPane.add(showStationButton, 0, 2);
			
			gPane.add(stationOutputField, 0, 3, 2, 1);
			
			gPane.add(compareLabel, 0, 4);
			gPane.add(stationComboBox, 1, 4);
			
			gPane.add(calcHamButton, 0, 5);
			
			gPane.add(Dist0Label, 0, 6);
			gPane.add(Dist1Label, 0, 7);
			gPane.add(Dist2Label, 0, 8);
			gPane.add(Dist3Label, 0, 9);
			gPane.add(Dist4Label, 0, 10);
			
			gPane.add(hamDist0Text, 1, 6);
			gPane.add(hamDist1Text, 1, 7);
			gPane.add(hamDist2Text, 1, 8);
			gPane.add(hamDist3Text, 1, 9);
			gPane.add(hamDist4Text, 1, 10);
			
			gPane.add(addStationButton, 0, 11);
			gPane.add(stationInputText, 1, 11);
			
			
			
			//when slider moved, update slider val text
			slider.valueProperty().addListener(new ChangeListener<Number>()  {
				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldVal, Number newVal) {
					sliderValText.setText(newVal.toString());
				}
			});
			
			
			//show station clicked, get slider val and calculate all stations with same ham dist, populate text area
			showStationButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					ArrayList<String> stations = hamDist.calcHammingDist(
							(int)(slider.getValue()), 
							stationComboBox.getValue().toString());
					
					stationOutputField.clear();
					Iterator<String> it = stations.iterator();
					while (it.hasNext()) {
						stationOutputField.appendText(it.next() + "\n");
					}
					
				}	
			});
			
			//add station clicked, capitalizes stationInputText then adds and sorts it to the list containing the other stations.
			addStationButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String station = stationInputText.getText();
					if (station.length() != 4) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Invalid Length!");
						alert.setHeaderText("Length must be 4!");

						alert.showAndWait();
					}
					else {
						hamDist.updateStation(station);
						stationList.clear();
						stationList.addAll(hamDist.getStations());
						stationComboBox.setItems(stationList);
					}
					
					
				}				
			});
			
			
			
			//when calc HD button clicked, get dropdown box value and compare it with all stations and update into boxes below
			calcHamButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					 int[] hamDistances = hamDist.calcHammingDist(stationComboBox.getValue().toString());
					 hamDist0Text.setText(Integer.toString(hamDistances[0]));
					 hamDist1Text.setText(Integer.toString(hamDistances[1]));
					 hamDist2Text.setText(Integer.toString(hamDistances[2]));
					 hamDist3Text.setText(Integer.toString(hamDistances[3]));
					 hamDist4Text.setText(Integer.toString(hamDistances[4])); 
				}	
			});
			
			

			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			appStage.setScene(scene);
			appStage.setTitle("Hamming Dist");
			appStage.show();
	}
	
		
		
	public static void main(String[] args) {
		launch(args); 
		
	}
}
