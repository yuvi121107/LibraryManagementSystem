
package ui;

import model.Member;
import model.LibraryItem;
import model.PhysicalBook;
import model.DigitalMedia;

import service.MemberService;
import service.BorrowService;

import exception.BookNotAvailableException;
import exception.MemberNotFoundException;

import repository.LibraryRepository;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LibraryApp {

    private static final Scanner scanner = new Scanner(System.in);

    private static MemberService memberService;
    private static BorrowService borrowService;
    private static LibraryRepository libraryRepository;

    public static void main(String[] args) {

        memberService = new MemberService();
        borrowService = new BorrowService(memberService);
        libraryRepository = new LibraryRepository();

        int choice;

        do {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Register Member");
            System.out.println("2. Add Physical Book");
            System.out.println("3. Add Digital Media");
            System.out.println("4. Display Library Items");
            System.out.println("5. Borrow Item");
            System.out.println("6. Return Item");
            System.out.println("7. View Borrowing History");
            System.out.println("8. View Overdue Items");
            System.out.println("9. Display Members");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = readInt();

            switch (choice) {
                case 1:
                    registerMember();
                    break;
                case 2:
                    addPhysicalBook();
                    break;
                case 3:
                    addDigitalMedia();
                    break;
                case 4:
                    displayLibraryItems();
                    break;
                case 5:
                    borrowItem();
                    break;
                case 6:
                    returnItem();
                    break;
                case 7:
                    borrowService.displayAllRecords();
                    break;
                case 8:
                    borrowService.displayOverdueItems();
                    break;
                case 9:
                    memberService.displayAllMembers();
                    break;
                case 0:
                    System.out.println("Thank you for using the library system!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);

        scanner.close();
    }

    private static int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }

        int number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    private static void registerMember() {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();

        System.out.print("Enter member email: ");
        String email = scanner.nextLine();

        try {
            Member member = memberService.registerMember(name, email);
            System.out.println("Member registered successfully!");
            System.out.println(member);
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private static void addPhysicalBook() {
        System.out.print("Enter book ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        System.out.print("Enter shelf location: ");
        String shelf = scanner.nextLine();

        try {
            borrowService.addItem(
                    new PhysicalBook(id, title, author, shelf));
            System.out.println("Physical book added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add book: " + e.getMessage());
        }
    }

    private static void addDigitalMedia() {
        System.out.print("Enter media ID: ");
        String id = scanner.nextLine();

        System.out.print("Enter media title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author/creator: ");
        String author = scanner.nextLine();

        System.out.print("Enter file format (PDF, EPUB, etc.): ");
        String format = scanner.nextLine();

        try {
            borrowService.addItem(
                    new DigitalMedia(id, title, author, format));
            System.out.println("Digital media added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add media: " + e.getMessage());
        }
    }

    private static void displayLibraryItems() {
        try {
            List<LibraryItem> items = libraryRepository.loadItems();

            if (items.isEmpty()) {
                System.out.println("No library items found.");
                return;
            }

            System.out.println("\n--- LIBRARY CATALOG ---");

            for (LibraryItem item : items) {
                System.out.println(item);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load items: " + e.getMessage());
        }
    }

    private static void borrowItem() {
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine();

        System.out.print("Enter item ID: ");
        String itemId = scanner.nextLine();

        try {
            System.out.println(
                    borrowService.borrowItem(memberId, itemId));
            System.out.println("Item borrowed successfully!");

        } catch (BookNotAvailableException | MemberNotFoundException e) {
            System.out.println("Borrowing failed: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Borrowing failed: " + e.getMessage());
        }
    }

    private static void returnItem() {
        System.out.print("Enter member ID: ");
        String memberId = scanner.nextLine();

        System.out.print("Enter item ID: ");
        String itemId = scanner.nextLine();

        try {
            System.out.println(
                    borrowService.returnItem(memberId, itemId));
            System.out.println("Item returned successfully!");

        } catch (IllegalStateException | IllegalArgumentException e) {
            System.out.println("Returning failed: " + e.getMessage());
        }
    }
}
