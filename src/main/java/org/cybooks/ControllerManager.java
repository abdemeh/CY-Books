package org.cybooks;

/**
 * Manager class for controllers.
 */
public class ControllerManager {
    private static DashboardController dashboardController;
    private static MemberBorrowController memberBorrowController;

    /**
     * Sets the DashboardController.
     *
     * @param controller the DashboardController to set
     */
    public static void setDashboardController(DashboardController controller) {
        dashboardController = controller;
    }

    /**
     * Gets the DashboardController.
     *
     * @return the DashboardController
     */
    public static DashboardController getDashboardController() {
        return dashboardController;
    }

    /**
     * Sets the MemberBorrowController.
     *
     * @param controller the MemberBorrowController to set
     */
    public static void setMemberBorrowController(MemberBorrowController controller) {
        memberBorrowController = controller;
    }

    /**
     * Gets the MemberBorrowController.
     *
     * @return the MemberBorrowController
     */
    public static MemberBorrowController getMemberBorrowController() {
        return memberBorrowController;
    }
}
