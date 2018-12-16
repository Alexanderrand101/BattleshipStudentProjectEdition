/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import matmik.model.Coordinates;
import matmik.model.Field;
import matmik.model.Ship;

/**
 *
 * @author Алескандр
 */
public class AutoPlacer{
    
    public static boolean placeShips(Field field, List<Ship> ships) {
        ships.sort(new Ship.ShipComparator());
        if(ships.isEmpty()) return true;
        return placeShipsAfterSorting(field, ships, 0);
    }  
    
    public static boolean borderPlaceShips(Field field, List<Ship> ships) {
        ships.sort(new Ship.ShipComparator());
        if(ships.isEmpty()) return true;
        return borderPlaceShipsAfterSorting(field, ships, 0);
    } 
    
    private static boolean borderPlaceShipsAfterSorting(Field field, List<Ship> ships, int depth){
        Ship currentShip = ships.get(depth);
        Coordinates originalCoordinates = currentShip.getBow();
        boolean originalRotation = currentShip.isRotated();
        field.gridRefresh();
        List<PossiblePlacement> candidatePlacements = new LinkedList<PossiblePlacement>(); 
        for(int i = 0; i < field.getGRID_HEIGHT(); i++)
            for(int j = 0; j < field.getGRID_WIDTH(); j++)
            {
                currentShip.setBow(new Coordinates(i,j));
                if(field.nonPaintingValidate(currentShip)){
                    field.add(currentShip);
                    field.gridExtendedRefresh();
                    candidatePlacements.add(new PossiblePlacement(currentShip.getBow(),
                            currentShip.isRotated(), field.freeCellCount()));
                    field.remove(currentShip);
                    field.gridRefresh();
                }
                currentShip.rotate();
                if(field.nonPaintingValidate(currentShip)){
                    field.add(currentShip);
                    field.gridExtendedRefresh();
                    candidatePlacements.add(new PossiblePlacement(currentShip.getBow(),
                            currentShip.isRotated(), field.freeCellCount()));
                    field.remove(currentShip);
                    field.gridRefresh();
                }
                currentShip.rotate();
            }
        List<PossiblePlacement> placements = new LinkedList<PossiblePlacement>();
        int maxFree = candidatePlacements.get(0).getFreeReamining();
        for(PossiblePlacement placement: candidatePlacements){
            if(placement.getFreeReamining() > maxFree)
                maxFree = placement.getFreeReamining();
        }
        for(PossiblePlacement placement: candidatePlacements){
            if(placement.getFreeReamining() == maxFree)
                placements.add(placement);
        }
        Random r = new Random();
        while(!placements.isEmpty())
        {
            PossiblePlacement placementChoice = placements.get(r.nextInt(placements.size()));
            currentShip.setBow(placementChoice.getBowCoordinate());
            currentShip.setIsRotated(placementChoice.isRotated());
            field.add(currentShip);
            if(depth == ships.size() - 1) return true;
            if(borderPlaceShipsAfterSorting(field, ships, depth + 1))return true;
            field.remove(currentShip);
            field.gridExtendedRefresh();
            placements.remove(placementChoice);
        }
        //no possible placements found; rollback and return
        currentShip.setIsRotated(originalRotation);//cause it was rotated once
        currentShip.setBow(originalCoordinates);
        return false;
    }
    
    private static boolean placeShipsAfterSorting(Field field, List<Ship> ships, int depth){
        //obtain all possible values
        Ship currentShip = ships.get(depth);
        Coordinates originalCoordinates = currentShip.getBow();
        boolean originalRotation = currentShip.isRotated();
        field.gridRefresh();
        List<PossiblePlacement> placements = new LinkedList<PossiblePlacement>(); 
        for(int i = 0; i < field.getGRID_HEIGHT(); i++)
            for(int j = 0; j < field.getGRID_WIDTH(); j++)
            {
                currentShip.setBow(new Coordinates(i,j));
                if(field.nonPaintingValidate(currentShip))
                    placements.add(new PossiblePlacement(
                            currentShip.getBow(), currentShip.isRotated(), -1)
                    );
            }
        currentShip.rotate();
        for(int i = 0; i < field.getGRID_HEIGHT(); i++)
            for(int j = 0; j < field.getGRID_WIDTH(); j++)
            {
                currentShip.setBow(new Coordinates(i,j));
                if(field.nonPaintingValidate(currentShip))
                    placements.add(new PossiblePlacement(
                            currentShip.getBow(), currentShip.isRotated(), -1)
                    );
            }
        Random r = new Random();
        while(!placements.isEmpty())
        {
            PossiblePlacement placementChoice = placements.get(r.nextInt(placements.size()));
            currentShip.setBow(placementChoice.getBowCoordinate());
            currentShip.setIsRotated(placementChoice.isRotated());
            field.add(currentShip);
            if(depth == ships.size() - 1) return true;
            if(placeShipsAfterSorting(field, ships, depth + 1))return true;
            field.remove(currentShip);
            field.gridRefresh();
            placements.remove(placementChoice);
        }
        //no possible placements found; rollback and return
        currentShip.setIsRotated(originalRotation);//cause it was rotated once
        currentShip.setBow(originalCoordinates);
        return false;
    }
}
