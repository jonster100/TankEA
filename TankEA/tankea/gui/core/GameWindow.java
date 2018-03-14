package tankea.gui.core;

import tankea.world.core.WorldEngine;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

public class GameWindow {
	private WorldLayout tankLayout;
	private Scene boardScene;
	private WorldEngine engine;

	/**
	 * class constructor will setup the engine, tankLayout which will display
	 * the visual of the engine and the scene which will hold everything.
	 */
	public GameWindow() {
		engine = new WorldEngine(50, 50);
		tankLayout = new WorldLayout(50, 50, engine.getWorld());
		boardScene = this.createBattleScene();

	}

	private Button runOneRoundButton(){
		Button tempButton = new Button("RunOneRound");
		tempButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if(engine.getIfThreadRunning()){
				engine.runSetNoRounds(1);
				}
				else{
					System.out.println("Engine thread not running.");
				}
				}
		});
		
		return tempButton;
	}
	
	
	private Button turnTrainingOffButton(){
		Button tempButton = new Button("TurningTrainingOff");
		tempButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				engine.setSituationTraining(false);
			}
			});
		
		return tempButton;
	}

	private Button runButton(TextField noRoundsTextField) {

		Button tempButton = new Button("Run");
		tempButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				int noRounds = 0;
				try {
					noRounds = Integer.parseInt(noRoundsTextField.getText());
				} catch (NumberFormatException e) {
					noRounds = 0;
				}
				if(engine.getIfThreadRunning()!=true){
				engine.setNoWorldRounds(noRounds);
				if (noRounds >= 0) {
					Thread engineThread = new Thread(engine);
					engineThread.start();
				} else {
					System.out.println("Entered number of rounds not valid.");
				}
			}
			}
		});
		return tempButton;
	}

	/***/
	private HBox createHBoxTopPanel() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(10);
		hbox.setStyle("-fx-background-color: #336699;");
		TextField enterNoRound = new TextField();

		Button run = this.runButton(enterNoRound);

		Button runOneRound = this.runOneRoundButton();
		Button training = this.turnTrainingOffButton();
		hbox.getChildren().addAll(run, runOneRound, enterNoRound,training);

		return hbox;
	}

	private Scene createBattleScene() {
		BorderPane allLayout = new BorderPane();
		GridPane node = tankLayout.getVisualBoard();
		allLayout.setCenter(node);

		HBox buttons = this.createHBoxTopPanel();
		allLayout.setTop(buttons);
		new AnimationTimer() {

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				allLayout.setCenter(node);
			}

		}.start();
		return new Scene(allLayout, 350, 250, Color.WHITE);
	}

	protected Scene getTankWorld() {
		return boardScene;
	}

}
