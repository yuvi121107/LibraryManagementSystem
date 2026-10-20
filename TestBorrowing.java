
import exception.BookNotAvailableException;
import exception.MemberNotFoundException;
import model.LibraryItem;
import model.Member;
import model.PhysicalBook;
import service.BorrowService;
import service.MemberService;

public class TestBorrowing {

    public static void main(String[] args) {

        // Step 1: Load saved members
        MemberService memberService = new MemberService();

        // Step 2: Find an existing member
        Member member = memberService.findMember("M001");

        // Register a member if M001 does not exist
        if (member == null) {
            member = memberService.registerMember(
                    "Arun",
                    "arun@example.com"
            );
        }

        // Step 3: Load the library catalog and borrowing records
        BorrowService borrowService =
                new BorrowService(memberService);

        // Step 4: Find the saved book
        LibraryItem book = borrowService.findItem("B101");

        // Add the book if it does not exist
        if (book == null) {
            book = new PhysicalBook(
                    "B101",
                    "Java Basics",
                    "James",
                    "Shelf A1"
            );

            borrowService.addItem(book);
        }

        // Step 5: Borrow the book
        System.out.println("\n--- BORROW BOOK ---");

        try {
            System.out.println(
                    borrowService.borrowItem(
                            member.getMemberId(),
                            book.getId()
                    )
            );

            System.out.println(
                    "Available after borrowing: "
                            + book.isAvailable()
            );

        } catch (BookNotAvailableException
                 | MemberNotFoundException e) {

            System.out.println("Borrowing failed: " + e.getMessage());
        }

        // Step 6: Return the book
        System.out.println("\n--- RETURN BOOK ---");

        try {
            System.out.println(
                    borrowService.returnItem(
                            member.getMemberId(),
                            book.getId()
                    )
            );

            System.out.println(
                    "Available after returning: "
                            + book.isAvailable()
            );

        } catch (IllegalStateException
                 | IllegalArgumentException e) {

            System.out.println("Returning failed: " + e.getMessage());
        }

        // Step 7: Display borrowing history
        System.out.println("\n--- BORROWING HISTORY ---");
        borrowService.displayAllRecords();

        System.out.println("\nProgram completed.");
    }
}
