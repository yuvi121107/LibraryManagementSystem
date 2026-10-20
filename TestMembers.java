
import model.Member;
import service.MemberService;

public class TestMembers {

    public static void main(String[] args) {

        MemberService service = new MemberService();

        Member member1 = service.registerMember(
                "Arun", "arun@example.com"
        );

        Member member2 = service.registerMember(
                "Kumar", "kumar@example.com"
        );

        System.out.println("Registered Members:");
        service.displayAllMembers();

        System.out.println("\nSearching for M001:");
        System.out.println(service.findMember("M001"));
    }
}
