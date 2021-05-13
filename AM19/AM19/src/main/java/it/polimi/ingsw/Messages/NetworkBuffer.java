package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.EmptyBufferException;

import java.util.LinkedList;
import java.util.List;

public class NetworkBuffer {
    /**
     * this attribute represents the content of the buffer
     */
    private StringBuilder buffer;
    /**
     * this attribute is a list of the XML Strings correctly extracted from the buffer
     */
    private List<String> extracted;

    private final String messageStart = "<Message>";
    private final String messageEnd = "</Message>";
    private final String ping = "<ping/>";
    private final String pong = "<pong/>";


    /**
     * this is the constructor of the class
     */
    public NetworkBuffer(){
        buffer = new StringBuilder();
        extracted = new LinkedList<>();
    }


    /**
     * @return true if the first message in the buffer is a ping element
     */
    public boolean getPing() {
        if(extracted.size() > 0 && extracted.get(0).equals(ping)){
            extracted.remove(0);
            return true;
        }
        return false;
    }

    /**
     * @return true if the first message in the buffer is a pong element
     */
    public boolean getPong() {
        if(extracted.size() > 0 && extracted.get(0).equals(pong)){
            extracted.remove(0);
            return true;
        }
        return false;
    }

    /**
     * this method is used to add a String to the buffer
     * @param s is the string to be added
     * @throws Exception is thrown if the current state of the buffer does not correspond to a
     * correct XML String. In that case, the buffer is emptied
     */
    public synchronized void append(String s) throws Exception {
        buffer.append(s);
        if(!checkString()) {
            buffer.setLength(0);
            throw new Exception("Malformed message!");
        }
        extract();
    }

    //aggiungere estrazione di tutte le stringhe
    /**
     * @return the oldest XML message or ping element in the buffer
     * @throws EmptyBufferException if there are no available Strings
     */
    public synchronized String get() throws EmptyBufferException {
        if(extracted.size()==0) throw new EmptyBufferException();
        return extracted.remove(0);
    }

    /**
     * this helper method is used to extract a message or a ping element from the buffer
     */
    private void extract(){
        extractPing();
        extractXMLMessage();
    }

    /**
     * this helper method is used to extract a ping element from the buffer
     */
    private void extractPing(){
        if(buffer.toString().startsWith(ping)) {
            extracted.add(ping);
            buffer.delete(0, ping.length());
        }
        else if(buffer.toString().startsWith(pong)) {
            extracted.add(pong);
            buffer.delete(0, pong.length());
        }
    }

    /**
     * this helper method is used to extract a message from the buffer
     */
    private void extractXMLMessage(){
        String s = buffer.toString();
        if(s.startsWith(messageStart)) {
            int index = s.indexOf(messageEnd);
            if(index != -1) {
                index = index + messageEnd.length();
                extracted.add(s.substring(0, index));
                buffer.delete(0, index);
            }
        }
    }

    /**
     * this method is used to check if the first String in the buffer represents a XML message or an element ping
     * @return true if the first String in the buffer is a correct representation, false otherwise
     */
    private boolean checkString(){
        String s = buffer.toString();
        if(!s.startsWith(messageStart) && !s.startsWith(ping)  && !s.startsWith(pong)) return false;
        return true;
    }
}
