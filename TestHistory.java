
import service.BorrowService;
import service.MemberService;

public class TestHistory {

    public static void main(String[] args) {

        MemberService memberService = new MemberService();

        BorrowService borrowService =
                new BorrowService(memberService);

        System.out.println("\n--- SAVED BORROWING HISTORY ---");

        borrowService.displayAllRecords();

        System.out.println("\n--- OVERDUE ITEMS ---");

        borrowService.displayOverdueItems();
    }
}
