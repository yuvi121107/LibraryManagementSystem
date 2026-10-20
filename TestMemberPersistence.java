
import service.MemberService;

public class TestMemberPersistence {
    public static void main(String[] args) {
        MemberService memberService = new MemberService();

        System.out.println("\nSaved Members:");
        memberService.displayAllMembers();
    }
}
