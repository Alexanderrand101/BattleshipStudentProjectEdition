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

    public PossiblePlacement(Coordinates bowCoordinate, boolean isRotated) {
        this.bowCoordinate = bowCoordinate;
        this.isRotated = isRotated;
    }
    
    public Coordinates getBowCoordinate() {
        return bowCoordinate;
    }

    public boolean isRotated() {
        return isRotated;
    }   
        
}
