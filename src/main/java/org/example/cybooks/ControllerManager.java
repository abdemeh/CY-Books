package org.example.cybooks;

public class ControllerManager {
    private static DashboardController dashboardController;

    public static void setDashboardController(DashboardController controller) {
        dashboardController = controller;
    }

    public static DashboardController getDashboardController() {
        return dashboardController;
    }
}
