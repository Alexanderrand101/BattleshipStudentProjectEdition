/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

/**
 *
 * @author Алескандр
 */
public  class PossiblePlacement{

    private Coordinates bowCoordinate;
    private boolean isRotated;
    private int freeReamining;

    public boolean isIsRotated() {
        return isRotated;
    }

    public void setIsRotated(boolean isRotated) {
        this.isRotated = isRotated;
    }

    public int getFreeReamining() {
        return freeReamining;
    }

    public void setFreeReamining(int freeReamining) {
        this.freeReamining = freeReamining;
    }

    public PossiblePlacement(Coordinates bowCoordinate, boolean isRotated, int freeRemaining) {
        this.bowCoordinate = bowCoordinate;
        this.isRotated = isRotated;
        this.freeReamining = freeRemaining;
    }
    
    public Coordinates getBowCoordinate() {
        return bowCoordinate;
    }

    public boolean isRotated() {
        return isRotated;
    }   
        
}
