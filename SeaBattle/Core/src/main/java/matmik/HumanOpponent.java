/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package matmik;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

/**
 *
 * @author Ð�Ð»ÐµÑ�ÐºÐ°Ð½Ð´Ñ€
 */
public abstract class HumanOpponent implements Opponent {
    
    AbstractConnector connector;
    boolean readAllowed = true;
    boolean writeAllowed = true;
    
    public Coordinates makeMove() throws Exception {
            syncRead();
            return new Persister().read(Coordinates.class, connector.in());
    }

    public CellState checkMove(Coordinates move) throws Exception {
            Serializer sc = new Persister();
            syncWrite();
            sc.write(move, connector.out());  
            syncRead();
            return sc.read(BodgeEnumWrap.class, connector.in()).getState();
    }

    public void responseDelivery(Coordinates coords, CellState result) throws Exception {
            syncWrite();
            new Persister().write(new BodgeEnumWrap(result), connector.out());
    }

    public Ship destroyedShip() throws Exception {
            //new Persister().write(new Coordinates(-1,-1), connector.out());
            syncRead();
            return (Ship)new Persister().read(Ship.class, connector.in());
    }

    public void sendDestroyedShip(Ship ship) throws Exception {
            //new Persister().read(Coordinates.class, connector.in());
            syncWrite();
            new Persister().write(ship, connector.out());
    }
    
    
    //херня идея, не робит
    protected void syncRead() throws Exception{
        if(!readAllowed){
           new Persister().write(new Coordinates(-1,-1), connector.out());
        }
        writeAllowed = true;
        readAllowed = false;
    }
//    
    protected void syncWrite() throws Exception{
        if(!writeAllowed){
           new Persister().read(Coordinates.class, connector.in());
        }
        writeAllowed = false;
        readAllowed = true;
    }
    
//    protected void initParser(){
//            xmi = XMLInputFactory.newFactory();
//    }
    
    
    //Вот так я решал проблему разъезда двух приложений.
    //К сожалению, андройд отправит нас с этим далего и надолго
//    protected StringReader extractXMLObject(InputStream in) throws XMLStreamException{
//        XMLEventReader xer = xmi.createXMLEventReader(in);
//        xer.nextEvent();
//        xer.peek();
//        String object = xer.toString();
//        xer.close();
//        return new StringReader(object);
//    }
    
        //тожечет не робит надо разбиратся
//    protected StringReader extractXMLObject(InputStream in) throws IOException{
//        StringBuilder serializedObject = new StringBuilder();
//        StringBuilder closingTag = new StringBuilder();
//        InputStreamReader isr = new InputStreamReader(in);
//        char[] symb = new char[1];
//        isr.read(symb, 0, 1);
//        while(true){
//            isr.read(symb, 0, 1);
//            if(symb[0] == '<') {
//               serializedObject.append(symb[0]);
//               closingTag.append(symb[0]).append('/');
//               break;
//            }
//        }
//        while(true){
//            isr.read(symb, 0, 1);
//            serializedObject.append(symb[0]);
//            closingTag.append(symb[0]);
//            if(symb[0] == '>') {
//                break;
//            }
//        }
//        String closingTagStr = closingTag.toString();
//        while(serializedObject.lastIndexOf(closingTagStr) == -1){
//            isr.read(symb, 0, 1);
//            serializedObject.append(symb[0]);
//        }
//        return new StringReader(serializedObject.toString());
//    }
}
