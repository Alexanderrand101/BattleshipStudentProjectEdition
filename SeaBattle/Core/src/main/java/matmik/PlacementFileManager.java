/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author Алескандр
 */
public class PlacementFileManager {
    private final static String[] PLACEMENT_FILENAMES = new String[]{"pl1.field","pl2.field","pl3.field","pl4.field"};
    private Field[] fields = new Field[4]; 
    private String pathToPlacements;
    private Field current;
    
    public PlacementFileManager(String pathToPlacements, Field current){
        this.pathToPlacements = pathToPlacements;
        this.current = current;
        for(int i = 0; i < fields.length; i++){
            File fieldFile = new File(pathToPlacements + PLACEMENT_FILENAMES[i]);
            if(fieldFile.exists()){
                try{
                    fields[i] = new Persister().read(Field.class, fieldFile);
                    fields[i].gridRefresh();
                    //add Validation
                }
                catch(Exception e){
                    
                }
            }
        }
    }
    
    public Cell[][] getGrid(int i){
        return fields[i].getGrid();
    }
    
    public void savePlacement(int i){
        File fieldFile = new File(pathToPlacements + PLACEMENT_FILENAMES[i]);
        File pFile = fieldFile.getParentFile();
        try {
            if(!pFile.exists()) pFile.mkdirs();
            fieldFile.createNewFile();
            new Persister().write(current, fieldFile);
        } catch (Exception ex) {
            Logger.getLogger(PlacementFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
            if(fieldFile.exists()){
                try{
                    fields[i] = new Persister().read(Field.class, fieldFile);
                    fields[i].gridRefresh();
                    //add Validation
                }
                catch(Exception e){
                    
                }
            }
    }
    
    public void deletePlacement(int i){
        File fieldFile = new File(pathToPlacements + PLACEMENT_FILENAMES[i]);
        fieldFile.delete();
        fields[i] = null;
    }
    
    public Field loadPlacement(int i){
        return fields[i];
    }
}
