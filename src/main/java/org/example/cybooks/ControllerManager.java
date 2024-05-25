package org.example.cybooks;

public class ControllerManager {
    private static DashboardController dashboardController;
    private static MemberBorrowController memberBorrowController;

    public static void setDashboardController(DashboardController controller) {
        dashboardController = controller;
    }

    public static DashboardController getDashboardController() {
        return dashboardController;
    }
    public static void setMemberBorrowController(MemberBorrowController controller) {
        memberBorrowController = controller;
    }

    public static MemberBorrowController getMemberBorrowController() {
        return memberBorrowController;
    }


}
