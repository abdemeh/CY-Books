package org.cybooks;

/**
 * The AdminContext class provides a context for the current Admin user.
 * It allows to get and set the current Admin.
 */
public class AdminContext {
    private static Admin currentAdmin;

    /**
     * Gets the current Admin.
     *
     * @return the current Admin
     */
    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    /**
     * Sets the current Admin.
     *
     * @param admin the Admin to be set as the current Admin
     */
    public static void setCurrentAdmin(Admin admin) {
        currentAdmin = admin;
    }
}
