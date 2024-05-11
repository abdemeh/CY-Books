
public class Main {
    public static void main(String[] args) 
    {
        Member newMember = new Member(5, "Minou", "Pico", "123 Main Street", 99112233, "alex.pico@example.com");
        newMember.insertIntoDatabase();

        Member.displayAllMembers();
        Member.displayMemberById(5);
    }
}
