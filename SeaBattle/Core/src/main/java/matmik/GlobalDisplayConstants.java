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
public class GlobalDisplayConstants {
    private GlobalDisplayConstants instance;
    private int offsetFieldPlayerRight;
    private int offsetFieldPlayerLeft;
    private int offsetFieldPlayerBottom;
    private int offsetFieldPlayerTop;
    private int offsetShipBankRight;
    private int offsetShipBankLeft;
    private int offsetShipBankBottom;
    private int offsetShipBankTop;
    
    public GlobalDisplayConstants getInstanceAndUpdate(){//todo a better Singleton
        if (instance == null){
            instance = new GlobalDisplayConstants();
        }
        return instance;
    }
    
    private GlobalDisplayConstants(){
        
    }
    
    public void CalcConstants(int sizex, int sizey){
        
    }
}
