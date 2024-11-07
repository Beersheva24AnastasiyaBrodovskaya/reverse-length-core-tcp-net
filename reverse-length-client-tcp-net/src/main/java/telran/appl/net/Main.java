package telran.net;

import java.io.*;
import telran.view.*;

public class Main {
    static TcpClient client;

    public static void main(String[] args) {
        Menu menu = new Menu("Network application",
                Item.of("Start session", Main::startSession),
                Item.of("Exit", Main::exit, true));
        menu.perform(new StandardInputOutput());
    }

    static void startSession(InputOutput io) {
        String host = io.readString("Enter hostname");
        int port = io.readNumberRange("Enter port", "Wrong port", 3000, 50000).intValue();
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        client = new TcpClient(host, port);
        Menu menu = new Menu("Run Session",
                Item.of("Enter command and data", Main::sessionProcessing),
                Item.ofExit());
        menu.perform(io);
    }

    static void sessionProcessing(InputOutput io) {
        String requestType = io.readString("Enter command (reverse, lenght)");
        String requestData = io.readString("Enter data");
        String response = client.sendAndReceive(requestType, requestData);
        io.writeLine(response);
    }

    static void exit(InputOutput io) {
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}