package tankea.gui.core;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import tankea.world.core.WorldEngine;

public class MainStage extends Application implements EventHandler<ActionEvent> {
	private WorldEngine worldControl;
	private GameWindow gameWindow; 
	public MainStage() {
		//worldControl = new WorldEngine(100,50,50);
		gameWindow = new GameWindow();
	}

	private void mainRun() {
		worldControl.worldRun();
		//worldControl.printTankPositions();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//MainStage temp = new MainStage();
		//temp.mainRun();
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		MainStage temp = new MainStage();
		Stage window = new Stage();
		window.setTitle("Managment");
		window.setScene(gameWindow.getTankWorld());
		window.show();
		
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}
}
