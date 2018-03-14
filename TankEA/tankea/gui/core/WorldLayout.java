package tankea.gui.core;

import tankea.world.core.Coordinates;
import tankea.world.core.Position;
import tankea.entities.Tank;

import java.util.Observable;
import java.util.Observer;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;

public class WorldLayout {
	private GridPane board;
	private Rectangle visualCoord[][];
	static private int arrayX;
	static private int arrayY;

	public WorldLayout(int x, int y, Position[][] tempWorld) {
		arrayX = x;
		arrayY = y;
		board = this.createGridPane();
		visualCoord = new Rectangle[arrayX][arrayY];
		this.setupBoard();
		this.setupObsever(tempWorld);
	}

	private void setupObsever(Position[][] tempWorld) {
		class RectangleColourUpdater implements Observer {
			public RectangleColourUpdater() {
			}

			@Override
			public void update(Observable o, Object arg) {
				Coordinates newCoord = ((Position) o).getCoord();
				if (arg != null) {
					Coordinates oldCoord = ((Tank) arg).getCoord();
					if(((Tank) arg).isDead()!=true){
						visualCoord[oldCoord.getX()][oldCoord.getY()].setFill(Color.GREEN);
					if (((Position)o).getTank().getTeam() == 'R') {
						visualCoord[newCoord.getX()][newCoord.getY()].setFill(Color.RED);
					} else{
						visualCoord[newCoord.getX()][newCoord.getY()].setFill(Color.BLUE);
					}
					 System.out.println("New Coord: " + newCoord.getX()+" - "+newCoord.getY());
				}
					else {
						visualCoord[oldCoord.getX()][oldCoord.getY()].setFill(Color.BLACK);
					}
				}
			}
		}
		for (int x = 0; x < arrayX; x++) {
			for (int y = 0; y < arrayY; y++) {
				RectangleColourUpdater temp = new RectangleColourUpdater();
				tempWorld[x][y].addObserver(temp);
				temp.update(tempWorld[x][y], null);
				//visualCoord[x][y].setOnMouseClicked(e->{System.out.println("Pressed square");});
			}
		}
	}

	private void setupBoard() {
		for (int x = 0; x < arrayX; x++) {
			for (int y = 0; y < arrayY; y++) {
				Rectangle r = new Rectangle(15.0, 15.0);
				r.setFill(Color.GREEN);

				visualCoord[x][y] = r;
				board.add(r, x, y);
			}
		}
	}

	private GridPane createGridPane() {
		GridPane tempBoard = new GridPane();
		tempBoard.setPadding(new Insets(1));
		tempBoard.setHgap(arrayX);
		tempBoard.setVgap(arrayY);
		ColumnConstraints column1 = new ColumnConstraints(0);
		ColumnConstraints column2 = new ColumnConstraints(0);
		// column2.setHgrow(Priority.ALWAYS);
		tempBoard.getColumnConstraints().addAll(column1, column2);
		tempBoard.setVgap(1);
		tempBoard.setHgap(1);
		return tempBoard;
	}

	public GridPane getVisualBoard() {
		return board;
	}

}