package org.cybooks;

/**
 * The UserContext class manages the current user logged into the system.
 * It provides methods to retrieve and set the current user.
 */
public class UserContext {
    private static Member currentUser;

    /**
     * Retrieves the current user.
     *
     * @return The current user logged into the system.
     */
    public static Member getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user.
     *
     * @param user The member representing the current user to be set.
     */
    public static void setCurrentUser(Member user) {
        currentUser = user;
    }
}
