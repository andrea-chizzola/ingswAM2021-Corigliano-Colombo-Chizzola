package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResQuantityTest {

    @Test
    public void addTest(){
       ResQuantity resquantity = new ResQuantity(new Coin(),0);
       resquantity.add(1);
       assertTrue(resquantity.getQuantity() == 1);
    }


}