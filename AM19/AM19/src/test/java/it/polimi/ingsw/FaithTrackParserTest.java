package it.polimi.ingsw;

import it.polimi.ingsw.xmlParser.FaithTrackParser;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FaithTrackParserTest {
    String test = "src\\test\\java\\it\\polimi\\ingsw\\XMLSourcesTest\\FaithTrackTest.xml";

    @Test
    public void parseTest(){
        FaithTrack faithTrack = FaithTrackParser.buildFaithTrack(test);

        //Creation of the faithTrack using constructors
        ArrayList<Integer> testTrack = new ArrayList<>();
        for(int i=0; i<25; i++){
            testTrack.add(i);
        }

        VaticanReportSection section1 = new VaticanReportSection(5,8,2);
        VaticanReportSection section2 = new VaticanReportSection(12,16,3);
        VaticanReportSection section3 = new VaticanReportSection(19,24,4);
        ArrayList<VaticanReportSection> sections = new ArrayList<>();
        sections.add(section1);
        sections.add(section2);
        sections.add(section3);

        FaithTrack copy = new FaithTrack(testTrack, sections);

        assertEquals(faithTrack, copy);
    }
}
