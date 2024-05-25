package org.cybooks;

import java.io.IOException;


import java.net.Socket;

public class ServerManager {
    private static final String WAMP_MANAGER_PATH = "C:\\wamp64\\wampmanager.exe";
    private static final String SERVER_HOST = "localhost"; // Change if your server is on a different host
    private static final int SERVER_PORT = 80; // Change to the port your server is running on

    public static void main(String[] args) {
        ServerManager manager = new ServerManager();
        if (manager.isServerRunning()) {
            System.out.println("Server is already running.");
        } else {
            if (manager.startServer()) {
                System.out.println("Server started successfully.");
            } else {
                System.err.println("Failed to start the server.");
            }
        }
    }

    public boolean isServerRunning() {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean startServer() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(WAMP_MANAGER_PATH);
            processBuilder.start();
            System.out.println("Starting the server...");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
