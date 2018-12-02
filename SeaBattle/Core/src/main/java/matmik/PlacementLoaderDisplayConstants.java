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
    private PlacementLoaderDisplayConstants instance;
    private int shipCellSize;
    private Bounds fieldBounds;
    
    public PlacementLoaderDisplayConstants getInstance(){
        if(instance == null){
            instance = new PlacementLoaderDisplayConstants();
        }
        return instance;
    }
    
    private PlacementLoaderDisplayConstants(){}
    
    public void calcConstants(int x, int y){
        int shipCellSize = y / 9 * 15 / 20;
        fieldBounds = new Bounds();
        fieldBounds.setLeftBound((x - shipCellSize * 10)/2);
        fieldBounds.setTopBound((y - shipCellSize * 10)/2);
    }
    
}
