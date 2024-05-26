package org.cybooks;

/**
 * The MemberContext class manages the current member logged into the system.
 * It provides methods to retrieve and set the current member.
 */
public class MemberContext {
    private static Member currentMember;
    /**
     * Retrieves the current member.
     *
     * @return The current member logged into the system.
     */
    public static Member getCurrentMember() {
        return currentMember;
    }

    /**
     * Sets the current member.
     *
     * @param member The member representing the current member to be set.
     */
    public static void setCurrentMember(Member member) {
        currentMember = member;
    }
}
