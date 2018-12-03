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
public class PlacementLoaderDisplayConstants {
    private static PlacementLoaderDisplayConstants instance;
    private int shipCellSize;
    private Bounds fieldBounds;
    
    public static PlacementLoaderDisplayConstants getInstance(){
        if(instance == null){
            instance = new PlacementLoaderDisplayConstants();
        }
        return instance;
    }
    
    private PlacementLoaderDisplayConstants(){}
    
    public int getShipCellSize(){
        return shipCellSize;
    }
    
    public Bounds getFieldBounds(){
        return fieldBounds;
    }
    
    public void calcConstants(int x, int y){
        shipCellSize = y / 9 * 8 / 10;
        fieldBounds = new Bounds();
        fieldBounds.setLeftBound((x - shipCellSize * 10)/2);
        fieldBounds.setTopBound((y - shipCellSize * 10)/2);
    }
    
}
