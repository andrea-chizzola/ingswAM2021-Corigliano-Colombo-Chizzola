package it.polimi.ingsw.View;

import it.polimi.ingsw.Messages.Enumerations.ItemStatus;
import it.polimi.ingsw.Model.MarketBoard.Marble;
import it.polimi.ingsw.Model.Resources.ResQuantity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CLIPainter{

    /**
     * the following attributes represent the offsets needed to draw the CLI elements
     */
    private static final int CARD_WIDTH = 27;
    private static final int DEV_CARD_LENGTH = 16;
    private static final int LEADER_CARD_LENGTH = 13;

    private static final int SPHERE_WIDTH = 7;
    private static final int SPHERE_LENGTH = 3;

    private static final int TOKEN_WIDTH = 30;
    private static final int TOKEN_LENGTH = 5;

    private static final int SQUARE_LENGTH = 3;
    private static final int SQUARE_WIDTH = 4;

    private static final int FAVOR_LENGTH = 3;
    private static final int FAVOR_WIDTH = 7;

    private static final int END_BOX_WIDTH = 60;
    private static final int END_BOX_LENGTH = 20;

    private static CLIColors baseFont = CLIColors.F_BLACK;

    /**
     * this method prints the logo of the game
     */
    public static void printLogo(){
        System.out.println(baseFont.getColor() +
                "                __    __     __     ______  ________ ______  _______  ______\n" +
                "               |  \\  /  |   /  \\   /  ____||__   __||  ____||  ___  \\/  ____|\n" +
                "               | | \\/ | |  / /  \\  \\___  \\    | |   | |__   | |__   /\\___  \\\n" +
                "               | |    | | /  __  \\  ____| |   | |   | |____ | |  \\ \\  ____| |\n" +
                "               |_|    |_|/_/    \\_\\|______/   |_|   |______||_|   \\_\\|______/\n"+
                "                                       ______  _____\n"+
                "                                      |  __  || ____|\n"+
                "                                      | |__| ||  _|\n"+
                "                                      |______||_|\n"+
                " _______  ______  __     _     __     _  ______  ______     __     __     __  ______  ______\n"+
                "|  ___  \\| ____ ||  \\   | |   /  \\   | |/  ____|/  ____|   /  \\   |  \\   | |/  _____|| ____ |\n"+
                "| |__   /| |__   | |\\ \\ | |  / /  \\  | |\\___  \\ \\___  \\   / /  \\  | |\\ \\ | || /      | |__\n"+
                "| |  \\ \\ | |____ | | \\ \\| | /  __  \\ | | ____| | ____| | /  __  \\ | | \\ \\| || \\____  | |____\n"+
                "|_|   \\_\\|______||_|  \\___|/_/    \\_\\|_||______/|______//_/    \\_\\|_|  \\___|\\_______||______|\n"
        );
    }

    /**
     * @return the value of the attribute SQUARE_LENGTH
     */
    public static int getSquareLength(){
        return SQUARE_LENGTH;
    }

    /**
     * this method prints a card
     * @param target is the matrix in which the item will be painted
     * @param V_OFFSET is the vertical position of the item
     * @param H_OFFSET is the horizontal position of the item
     * @param content is a String that represents the content of the item
     * @param length is the length of the card
     */
    private static void cardPainter
        (String[][] target, int V_OFFSET, int H_OFFSET, String content, int length){
        fill(target, V_OFFSET, H_OFFSET, CARD_WIDTH, length);
        paintRectangle(target,V_OFFSET, H_OFFSET, CARD_WIDTH, length, baseFont);
        insertString(target, V_OFFSET + 1, H_OFFSET +2, content, baseFont);
    }

    /**
     * this method prints a development card
     * @param target is the matrix in which the item will be painted
     * @param V_OFFSET is the vertical position of the item
     * @param H_OFFSET is the horizontal position of the item
     * @param content is a String that represents the content of the item
     */
    public static void devCardPainter
            (String[][] target, int V_OFFSET, int H_OFFSET, String content){
        cardPainter(target, V_OFFSET, H_OFFSET, content, DEV_CARD_LENGTH);
    }

    /**
     * this method prints a leader card
     * @param target is the matrix in which the item will be painted
     * @param V_OFFSET is the vertical position of the item
     * @param H_OFFSET is the horizontal position of the item
     * @param content is a String that represents the content of the item
     */
    public static void leaderCardPainter
            (String[][] target, int V_OFFSET, int H_OFFSET, String content){
        cardPainter(target, V_OFFSET, H_OFFSET, content, LEADER_CARD_LENGTH);
    }

    /**
     * this method prints a sphere
     * @param target is the matrix in which the item will be painted
     * @param V_OFFSET is the vertical position of the item
     * @param H_OFFSET is the horizontal position of the item
     * @param content is a String that represents the content of the item
     * @param font is the color of the content
     */
    private static void spherePainter(
            String[][] target, int V_OFFSET, int H_OFFSET, String content, CLIColors inside, CLIColors font){

        for(int i=1; i<SPHERE_WIDTH-1; i++){
            target[V_OFFSET][H_OFFSET+i] = inside.getColor() + font.getColor() + "-";
            target[V_OFFSET+SPHERE_LENGTH-1][H_OFFSET+i] = inside.getColor() + font.getColor() + "-";
        }
        for(int j=1; j<SPHERE_LENGTH-1;j++){
            for(int i=0; i<SPHERE_WIDTH; i++){
                if( i == 0 || i == SPHERE_WIDTH -1)
                    target[V_OFFSET+j][H_OFFSET+i]= inside.getColor() + font.getColor() +  "|";
                else target[V_OFFSET+j][H_OFFSET+i]= inside.getColor() + " ";
            }
        }
        target[V_OFFSET+SPHERE_LENGTH/2][H_OFFSET+SPHERE_WIDTH/2] = content;
    }

    /**
     * this method is used to paint a warehouse
     * @param target is the matrix in which the item will be painted
     * @param V_OFFSET is the vertical position of the item
     * @param H_OFFSET is the horizontal position of the item
     * @param structure represents the number of default shelves and their size
     * @param resources is the content of the warehoyse
     */
    public static void paintWarehouse
            (String[][] target, int V_OFFSET, int H_OFFSET, List<Integer> structure, List<ResQuantity> resources){
        insertString(target, V_OFFSET, H_OFFSET, "Personal Warehouse:", baseFont);
        CLIColors inside;
        int quantity;

        for(int i=0; i<structure.size(); i++){
            if(resources.size()>i) {
                quantity = resources.get(i).getQuantity();
                inside = resources.get(i).getResource().toColor();
            }
            else {
                quantity=0;
                inside = CLIColors.B_WHITE;
            }

            for(int j=0; j<structure.get(i); j++) {
                if(quantity>0) quantity--;
                else inside = CLIColors.B_WHITE;
                spherePainter(target, (V_OFFSET+2)+i*(SPHERE_LENGTH+1), H_OFFSET+j*(SPHERE_WIDTH+1),
                        " ", inside, baseFont);
            }
        }
    }

    /**
     * this method is used to paint a personal production
     * @param target is the matrix in which the item will be painted
     * @param V_OFFSET is the vertical position of the item
     * @param H_OFFSET is the horizontal position of the item
     * @param materials is the list of materials in the production
     * @param products is the list of products in the production
     * @param customM is the number of custom materials in the production
     * @param customP is the number of custom products in the production
     */
    public static void paintPersonalProduction
            (String[][] target, int V_OFFSET, int H_OFFSET,
             List<ResQuantity> materials, List<ResQuantity> products, int customM, int customP){

        //drawing of custom materials
        for(int i=0; i<customM; i++){
            spherePainter(target, V_OFFSET+2+i*(SPHERE_LENGTH+2)+1, H_OFFSET+3,
                    "?",CLIColors.B_WHITE , baseFont);
        }

        //drawing of custom products
        for(int i=0; i<customP; i++){
            spherePainter(target, V_OFFSET+2+i*(SPHERE_LENGTH+2)+1, SPHERE_WIDTH*2 + H_OFFSET+1,
                    "?",CLIColors.B_WHITE , baseFont);
        }

        //drawing of non-customizable materials
        int c=0;
        for(int i=0; i<materials.size(); i++){
            ResQuantity res = materials.get(i);
            for(int j=0; j < res.getQuantity(); j++){
                spherePainter(target, V_OFFSET+2+(c+customM)*(SPHERE_LENGTH+2)+1, H_OFFSET+3,
                        " ", res.getResource().toColor(), baseFont);
                c++;
            }
        }

        //drawing of non-customizable products
        int k=0;
        for(int i=0; i<products.size(); i++){
            ResQuantity res = products.get(i);
            for(int j=0; j< res.getQuantity(); j++) {
                spherePainter(target, V_OFFSET + 2 + (k + customP) * (SPHERE_LENGTH + 2), SPHERE_WIDTH * 2 + H_OFFSET + 1,
                        " ", res.getResource().toColor(), baseFont);
                k++;
            }
        }

        int width, length, mid;
        width = (SPHERE_WIDTH + 1) * 2 + 9;
        length = (SPHERE_LENGTH + 2) * (Math.max(c + customM, k + customP)) + 2;
        mid = width/2;

        insertString(target, V_OFFSET, H_OFFSET, "Personal production:", baseFont);
        for(int i=0; i<length-1; i++){
            target[i+V_OFFSET+1][mid+H_OFFSET] = baseFont.getColor() + "║";
        }
        paintRectangle(target, V_OFFSET+1, H_OFFSET, width, length, baseFont);
    }

    /**
     * this method is used to paint the top token in the board
     * @param target is the matrix in which the item will be painted
     * @param V_OFFSET is the vertical position of the item
     * @param H_OFFSET is the horizontal position of the item
     * @param content is the content of the top token
     */
    public static void paintToken
            (String[][] target, int V_OFFSET, int H_OFFSET, String content){
        paintRectangle(target, V_OFFSET, H_OFFSET, TOKEN_WIDTH, TOKEN_LENGTH, baseFont);
        insertString(target, V_OFFSET+1, H_OFFSET+2, content, baseFont);
    }

    /**
     * this method is used to paint a rectangle
     * @param target is the matrix in which the item will be painted
     * @param V_OFFSET is the vertical position of the item
     * @param H_OFFSET is the horizontal position of the item
     * @param width is the width of the card
     * @param length is the length of the card
     * @param font is the content of the card
     */
    public static void paintRectangle
            (String[][] target, int V_OFFSET, int H_OFFSET, int width, int length, CLIColors font){

        target[V_OFFSET][H_OFFSET] = "╔";
        target[V_OFFSET + length-1][H_OFFSET]= "╚";
        target[V_OFFSET][H_OFFSET + width-1]= "╗";
        target[V_OFFSET + length-1][H_OFFSET + width-1]= "╝";

        for(int i=1; i < width-1; i++){
            target[V_OFFSET][H_OFFSET+i] = font.getColor() + "=";
            target[V_OFFSET+length-1][H_OFFSET+i] = font.getColor() + "=";
        }
        for(int i=1; i < length-1; i++){
            target[V_OFFSET+i][H_OFFSET] = font.getColor() + "║";
            target[V_OFFSET+i][H_OFFSET+width-1] = font.getColor() + "║";
        }
    }

    /**
     *
     * @param target is the matrix in which the item will be painted
     * @param V_OFFSET is the vertical position of the item
     * @param H_OFFSET is the horizontal position of the item
     * @param marbles represents the current state of the market
     * @param nRows is the number of rows of the market
     * @param nColumns is the number of columns of the market
     */
    public static void paintMarketBoard
            (String[][] target, int V_OFFSET, int H_OFFSET, List<Marble> marbles, int nRows, int nColumns){
        int width, length, c=0;
        width = (SPHERE_WIDTH + 1) * nColumns + 3;
        length = (SPHERE_LENGTH + 1) * (nRows+1) + 1;
        insertString(target, V_OFFSET, H_OFFSET, "Market board:", baseFont);
        paintRectangle(target, V_OFFSET+1, H_OFFSET, width, length, baseFont);

        spherePainter(target, V_OFFSET+2, H_OFFSET+width/2-SPHERE_WIDTH/2, " ",  marbles.get(marbles.size()-1).toColor(), baseFont);
        for(int j=0; j<nRows; j++){
            for(int i=0; i<nColumns; i++){
                spherePainter(target, V_OFFSET+2+(j+1)*(SPHERE_LENGTH+1),
                        H_OFFSET+2+i*(SPHERE_WIDTH+1), " ", marbles.get(c).toColor(), baseFont);
                c++;
            }

        }

    }

    /**
     * this method is used to paint the player's FaithTrack and Sections
     * @param target is the matrix in which the item will be painted
     * @param V_OFFSET is the vertical position of the item
     * @param H_OFFSET is the horizontal position of the item
     * @param trackPoints represents the structure of the FaithTrack
     * @param players is a list of the names of the players
     * @param faith is a map that contains the faith achieved by the players
     * @param sections is the status of the player's vatican sections
     * @param start is the start of the vatican report sections
     * @param end is the end of the vatican report sections
     * @param sectionPoints are the points of each pope favor
     */
    //separare questo metodo in due metodi.  Uno per la faithtrack, uno per le section status e uno per i pope favor
    public static void paintFaithTrack
            (String[][] target, int V_OFFSET, int H_OFFSET, List<Integer> trackPoints, List<String> players,
             Map<String, Integer> faith, Map<String, List<ItemStatus>> sections, List<Integer> start, List<Integer> end,
            List<Integer> sectionPoints){
        int length = trackPoints.size(), num = players.size(), val, big=0, offset=0, position, from=0, to=0;
        String background = CLIColors.B_BLUE.getColor();
        List<String> coloredPoints = new LinkedList<>();

        //suppongo che le sezioni papali siano separate almeno da una cella. Questo codice è da mettere in un helper.
        for(int i=0; i < trackPoints.size(); i++){
            String value = "";
            if(i>=start.get(from) && i<=end.get(to)) value = background + value;
            if(i>end.get(to)){
                from++;
                to++;
            }
            coloredPoints.add(value);
        }

        // drawing FaithTracks and player's position
        for(int i=0; i<length; i++){
            offset = offset + SQUARE_WIDTH;
            val = trackPoints.get(i);
            if(val>=10) big ++;
            for(int j=0; j<players.size(); j++){
                position = faith.get(players.get(j)) - 1;
                target[V_OFFSET + j * (SQUARE_LENGTH+1) + 2][H_OFFSET + (i+1)*SQUARE_WIDTH + big] =
                        baseFont.getColor() + "║";
                if(position == i) {
                    target[V_OFFSET + j * (SQUARE_LENGTH+1) + 2][H_OFFSET + i*SQUARE_WIDTH + 2 + big] =
                            baseFont.getColor() + CLIColors.F_RED.getColor() + "✘";
                }
                else if(val<10) {
                    target[V_OFFSET + j * (SQUARE_LENGTH+1) + 2][H_OFFSET + i*SQUARE_WIDTH + 2 + big] =
                            baseFont.getColor() + coloredPoints.get(i) + ((val == 0) ? " " : String.valueOf(val));
                }
                else{
                    target[V_OFFSET + j * (SQUARE_LENGTH+1) + 2][H_OFFSET + i*SQUARE_WIDTH + big + 1] =
                            baseFont.getColor() + coloredPoints.get(i) + val/10;
                    target[V_OFFSET + j * (SQUARE_LENGTH+1) + 2][H_OFFSET + i*SQUARE_WIDTH + big + 2] =
                            baseFont.getColor() + coloredPoints.get(i) + val%10;
                }
            }
        }
        //drawing section status
        for(int i=0; i<num; i++){
            List<ItemStatus> section = sections.get(players.get(i));
            StringBuilder status = new StringBuilder();
            status.append(players.get(i)).append(" n^").append(i+1).append(": ");
            int size = insertString(target,V_OFFSET + i * (SQUARE_LENGTH+1), H_OFFSET, status.toString(), baseFont);
            for(int j=0; j<section.size(); j++){
                target[V_OFFSET + i * (SQUARE_LENGTH+1)][H_OFFSET +size+j*2] = section.get(j).getFaithSymbol();
            }
            paintRectangle(target,V_OFFSET + i * (SQUARE_LENGTH+1)+1, H_OFFSET,
                    length * SQUARE_WIDTH + 1 + big, SQUARE_LENGTH, baseFont);
        }

        // drawing PopeFavours value
        int totalVOffset, totalHOffset;
        totalHOffset = SQUARE_WIDTH*trackPoints.size()/2 - 3*FAVOR_WIDTH/2;
        totalVOffset = (SQUARE_LENGTH+1)*players.size() + 1;
        insertString(target, totalVOffset+V_OFFSET - 1, H_OFFSET, "Vatican Report Section Favors: ", baseFont);
        for(int i=0; i<sectionPoints.size(); i++){
            paintRectangle(target,totalVOffset+V_OFFSET,
                    totalHOffset + H_OFFSET+i*(FAVOR_WIDTH+2), FAVOR_WIDTH, FAVOR_LENGTH, baseFont);
            target[totalVOffset+V_OFFSET+ FAVOR_LENGTH/2][totalHOffset + H_OFFSET+i*(FAVOR_WIDTH+2) + FAVOR_WIDTH/2] = sectionPoints.get(i).toString();
        }
    }

    /**
     * this method is used to paint a StrongBox
     * @param target is the matrix in which the item will be painted
     * @param V_OFFSET is the vertical position of the item
     * @param H_OFFSET is the horizontal position of the item
     * @param status represent the current status of the StrongBox
     */
    public static void paintStrongbox
            (String[][] target, int V_OFFSET, int H_OFFSET, List<ResQuantity> status){
        paintResourceBox(target, V_OFFSET, H_OFFSET, status, "Strongbox: ");
    }

    /**
     * this method is used to paint a resource box
     * @param target is the matrix in which the item will be painted
     * @param V_OFFSET is the vertical position of the item
     * @param H_OFFSET is the horizontal position of the item
     * @param status represents the current state of a resource box
     * @param title
     */
    private static void paintResourceBox
            (String[][] target, int V_OFFSET, int H_OFFSET, List<ResQuantity> status, String title){

        int width, length;
        width = (SPHERE_WIDTH + 1) * 2 + 7;
        length = status.size()+2;
        fill(target, V_OFFSET*1, H_OFFSET, width, length);
        paintRectangle(target, V_OFFSET+1, H_OFFSET, width, length, baseFont);
        insertString(target, V_OFFSET, H_OFFSET, title, baseFont);
        for(int i=0; i<status.size(); i++){
            ResQuantity r = status.get(i);
            String s = r.getResource().toString() + ": " + r.getQuantity();
            target[V_OFFSET+i+2][H_OFFSET+5] = r.getResource().getSymbol();
            insertString(target, V_OFFSET+i+2, H_OFFSET+7, s, baseFont);
        }
    }

    /**
     * this method is used to insert a String inside an item
     * @param target is the matrix in which the item will be painted
     * @param V_OFFSET is the vertical position of the item
     * @param H_OFFSET is the horizontal position of the item
     * @param s is the String to be inserted
     * @param font is the color of the string font
     * @return the length of the inserted String
     */
    private static int insertString(String[][] target, int V_OFFSET, int H_OFFSET, String s, CLIColors font){
        char[] sequence = s.toCharArray();
        int length = s.length(), v=0, h=0;
        for(int i=0; i<length; i++){
            if(sequence[i] == '\n') {
                v++;
                h=0;
            }
            else {
                target[V_OFFSET+v][H_OFFSET+h] = font.getColor() + sequence[i];
                h++;
            }
        }
        return length;
    }

    /**
     * this method paints a box that contains all the extra slots
     * @param target is the matrix in which the item will be painted
     * @param V_OFFSET is the vertical position of the item
     * @param H_OFFSET is the horizontal position of the item
     * @param status represent the current status of the box
     */
    public static void paintExtraSlots
            (String[][] target, int V_OFFSET, int H_OFFSET, List<ResQuantity> status){
        paintResourceBox(target, V_OFFSET, H_OFFSET, status, "Extra Slots: ");
    }

    /**
     * this method is used to fill a partition of matrix of Strings with " "
     * @param target is the matrix in which the item will be painted
     * @param V_OFFSET is the vertical position of the item
     * @param H_OFFSET is the horizontal position of the item
     * @param width is the width of the partition
     * @param length is the length of the partition
     */
    public static void fill(String[][] target, int V_OFFSET, int H_OFFSET, int width, int length){
        for(int i=0; i<length; i++){
            for(int j=0; j<width; j++){
                target[V_OFFSET+i][H_OFFSET+j] = CLIColors.RESET.getColor() + " ";
            }
        }
    }

    public static void paintEndGameBox(String[][] target, int V_OFFSET, int H_OFFSET, Map<String, Integer> players){
        fill(target, V_OFFSET, H_OFFSET, END_BOX_WIDTH, END_BOX_LENGTH);
        paintRectangle(target, V_OFFSET, H_OFFSET, END_BOX_WIDTH, END_BOX_LENGTH, baseFont);

        StringBuilder content = new StringBuilder();
        String winner = "";
        int top = 0;

        for(String name : players.keySet()){
            int points = players.get(name);
            content.append("Player: ").append(name).append(" Points: ").append(points).append("\n");
            if(points>top){
                winner = name;
                top = points;
            }
        }
        content.append("\nWinner: ").append(winner).append(" Points: ").append(top);
        CLIPainter.insertString(target, V_OFFSET + 7, H_OFFSET + 15, content.toString(), baseFont);
    }
    /**
     * @return the length of a sphere
     */
    public static int getSphereLength() {
        return SPHERE_LENGTH;
    }

    /**
     * @return the width of a card
     */
    public static int getCardWidth() {
        return CARD_WIDTH;
    }

    /**
     * @return the length of a DevelopmentCard
     */
    public static int getDevCardLength() {
        return DEV_CARD_LENGTH;
    }

    /**
     * @return the length of a LeaderCard
     */
    public static int getLeaderCardLength() {
        return LEADER_CARD_LENGTH;
    }
}

