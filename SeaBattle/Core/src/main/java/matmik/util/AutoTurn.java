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

/**
 *
 * @author Алескандр
 */
public class AutoTurn {
    
    public static Coordinates makeMove(Field tohit) {
        List<Coordinates> possibleHits = new LinkedList<Coordinates>();
        for(int i = 0; i < tohit.getGRID_HEIGHT(); i++)
            for(int j = 0; j < tohit.getGRID_WIDTH(); j++){
                Coordinates hitCoodinates = new Coordinates(i,j);
                if(tohit.isHittable(hitCoodinates))
                    possibleHits.add(hitCoodinates);
            }
        Random r = new Random();
        return possibleHits.get(r.nextInt(possibleHits.size()));
    }
}
