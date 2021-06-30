package it.polimi.ingsw.Messages;

import it.polimi.ingsw.Exceptions.EmptyBufferException;
import it.polimi.ingsw.Exceptions.MalformedMessageException;

import java.util.LinkedList;
import java.util.List;

public class NetworkBuffer {
    /**
     * this attribute represents the content of the buffer
     */
    private final StringBuilder buffer;
    /**
     * this attribute is a list of the XML Strings correctly extracted from the buffer
     */
    private final List<String> extracted;

    private final String messageStart = "<?xml version=";
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
     * @throws MalformedMessageException is thrown if the current state of the buffer does not correspond to a
     * correct XML String. In that case, the buffer is emptied
     */
    public void append(String s) throws MalformedMessageException {
        buffer.append(s);
        if(!checkString()) {
            buffer.setLength(0);
            throw new MalformedMessageException("Malformed message!");
        }
        extract();
    }

    /**
     * @return the oldest XML message or ping element in the buffer
     * @throws EmptyBufferException if there are no available Strings
     */
    public String get() throws EmptyBufferException {
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
        /*if(buffer.toString().startsWith(ping)) {
            extracted.add(ping);
            buffer.delete(0, ping.length());
        }
        else if(buffer.toString().startsWith(pong)) {
            extracted.add(pong);
            buffer.delete(0, pong.length());
        }*/
        int lastPingOccurrence = 0, lastPongOccurrence = 0;
        while(lastPingOccurrence!=-1){
                lastPingOccurrence = buffer.toString().lastIndexOf(ping);
                if(lastPingOccurrence!=-1) {
                    extracted.add(ping);
                    buffer.delete(lastPingOccurrence, lastPingOccurrence+ping.length());
                }
        }
        while(lastPongOccurrence!=-1){
            lastPongOccurrence = buffer.toString().lastIndexOf(pong);
            if(lastPongOccurrence != -1) {
                extracted.add(pong);
                buffer.delete(lastPongOccurrence, lastPongOccurrence+pong.length());
            }
        }
    }

    /**
     * this helper method is used to extract a message from the buffer
     */
    private void extractXMLMessage(){
        int start, end = 0;

        while(end != -1){
            String s = buffer.toString();
            start = s.indexOf(messageStart);
            end = -1;
            if (start != -1) {
                end = s.indexOf(messageEnd);
                if (end != -1) {
                    end = end + messageEnd.length();
                    extracted.add(s.substring(start, end));
                    buffer.delete(start, end);
                }
            }
        }
    }

    /**
     * this method is used to check if the first String in the buffer represents a XML message or an element ping
     * @return true if the first String in the buffer is a correct representation, false otherwise
     */
    private boolean checkString(){
        String s = buffer.toString();
        return s.startsWith(messageStart) || s.startsWith(ping) || s.startsWith(pong) || s.contains(messageEnd);
    }

    /**
     * this method is used to check if the network buffer is empty
     * @return true if the buffer is empty
     */
    public boolean isEmpty(){
        return extracted.isEmpty();
    }
}
