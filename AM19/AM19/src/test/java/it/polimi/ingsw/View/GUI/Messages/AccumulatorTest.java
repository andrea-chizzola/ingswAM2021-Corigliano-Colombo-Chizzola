package it.polimi.ingsw.View.GUI.Messages;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccumulatorTest {

    private Accumulator accumulator;

    @BeforeEach
    void setup(){
        accumulator = new Accumulator();
    }

    @Test
    void test1(){

        accumulator.setInitResources("1");
        accumulator.setInitResources("coin");
        accumulator.setInitResources("2");
        accumulator.setInitResources("coin");
        System.out.println(accumulator.buildSelectedResources());
    }

    @Test
    void test2(){

        System.out.println(accumulator.buildSelectedResources());
    }

    @Test
    void test3(){

        accumulator.setSwapTarget(1);
        accumulator.setSwapSource(2);
        System.out.println(accumulator.buildSwap());
    }

}