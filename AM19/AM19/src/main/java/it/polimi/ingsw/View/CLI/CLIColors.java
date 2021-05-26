package it.polimi.ingsw.View.CLI;

public enum CLIColors {

    //font colors
    F_BLACK("\u001B[30m"),
    F_RED("\u001B[31m"),
    F_GREEN("\u001B[32m"),
    F_YELLOW("\u001B[33m"),
    F_BLUE("\u001B[34m"),
    F_MAGENTA("\u001B[35m"),
    F_CYAN("\u001B[36m"),
    F_WHITE("\u001B[37m"),

    //background colors
    B_BLACK("\u001B[40m"),
    B_RED("\u001B[41m"),
    B_GREEN("\u001B[42m"),
    B_YELLOW("\u001B[43m"),
    B_BLUE("\u001B[44m"),
    B_MAGENTA("\u001B[45m"),
    B_CYAN("\u001B[46m"),
    B_GRAY("\u001B[47m"),
    B_WHITE("\u001B[107m"),

    RESET("\u001B[0m");

    /**
     * this attribute represents the ANSI codification of the color
     */
    private String color;

    /**
     * this is the constructor of the class
     * @param color is the attribute of a CLIColor
     */
    CLIColors(String color){
        this.color = color;
    }

    /**
     * @return the ANSI codification of color associated to the CLIColors instance
     */
    public String getColor(){
        return color;
    }

}
