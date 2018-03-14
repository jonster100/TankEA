package tankea.world.core;

import tankea.entities.Tank;

import java.util.Observable;

public class Position extends Observable {
	private Coordinates coordinates;
	private Tank tank;
	
	/**class constructor
	 * @param tempX the initial horizontal coordinate
	 * @param tempY the initial vertical coordinate*/
	public Position(int tempX, int tempY) {
		coordinates = new Coordinates(tempX, tempY);
	}
	
	/**this method will add the specified tank to the class
	 * @param tempTank the specified tank to be added to the class
	 * @param setupOrNot whether the tank being added is for the initial engine setup or for moving the tank during the rounds*/
	public void addTank(Tank tempTank, boolean setupOrNot) {
		try{
		tank = tempTank;
		setChanged();
		notifyObservers(tempTank);
		if (setupOrNot) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		}catch(NullPointerException e){}
	}
	
	public void setTankDiedAtPosition(){
		setChanged();
		notifyObservers(tank);
	}
	/**this method will set the tank variable to null, used when a tank is moving*/
	public void setPositionNull() {
		tank = null;
	}
	/**
	 * @return Tank returns the tank at this position*/
	public Tank getTank() {
		return tank;
	}
	/**
	 * @return Coordinate returns the coordinates for the class*/
	public Coordinates getCoord() {
		return coordinates;
	}
	/**
	 * @return boolean returns whether a tank is at this position*/
	public boolean checkTankAtPosition() {
		return (tank == null) ? false : true;
	}
}
