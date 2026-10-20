
import java.time.LocalDate;
import model.BorrowRecord;
import model.Member;
import model.PhysicalBook;
import service.MemberService;

public class TestOverdue {

    public static void main(String[] args) {

        MemberService memberService = new MemberService();

        Member member = memberService.registerMember(
                "Arun", "arun@example.com"
        );

        PhysicalBook book = new PhysicalBook(
                "B101", "Java Basics", "James", "Shelf A1"
        );

        BorrowRecord record = new BorrowRecord(
                member,
                book,
                LocalDate.of(2026, 10, 1),
                LocalDate.of(2026, 10, 15)
        );

        record.markReturned(LocalDate.of(2026, 10, 18));

        System.out.println("Borrowing Record:");
        System.out.println(record);

        System.out.println("Overdue: " + record.isOverdue());
        System.out.println("Overdue days: " + record.getOverdueDays());
        System.out.println("Fine: Rs. " + record.calculateFine());
    }
}
