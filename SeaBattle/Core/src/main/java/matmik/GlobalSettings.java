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
public class GlobalSettings {
    
    private static GlobalSettings instance;
    private PlacementStrategy placementStrategy;
    
    public PlacementStrategy getPlacementStrategy() {
        return placementStrategy;
    }

    public void setPlacementStrategy(PlacementStrategy placementStrategy) {
        this.placementStrategy = placementStrategy;
    }
    
    public static GlobalSettings getInstance(){
        if(instance == null){
            instance = new GlobalSettings();
        }
        return instance;
    }
    
    private GlobalSettings(){
        placementStrategy = PlacementStrategy.RANDOM;
    }
    
}
