package it.polimi.ingsw;

import it.polimi.ingsw.Client.Client;
import it.polimi.ingsw.Server.Server;

import java.util.Arrays;
import java.util.List;

public class Launcher {

    private static final String SERVER_ARG = "-server";
    private static final String CLIENT_ARG = "-client";

    public static void main(String[] args) {

        List<String> argList = Arrays.asList(args);

        if(argList.contains(CLIENT_ARG)){
            Client.main(args);
        }else if(argList.contains(SERVER_ARG)){
            Server.main(args);
        }

    }

}
