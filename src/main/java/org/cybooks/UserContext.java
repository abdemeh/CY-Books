package org.cybooks;

public class UserContext {
    private static Member currentUser;
    public static Member getCurrentUser() {
        return currentUser;
    }
    public static void setCurrentUser(Member user) {
        currentUser = user;
    }
}