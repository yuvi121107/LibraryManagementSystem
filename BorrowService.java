
package service;

import model.BorrowRecord;
import model.LibraryItem;
import model.Member;
import repository.LibraryRepository;
import repository.BorrowRecordRepository;
import exception.BookNotAvailableException;
import exception.MemberNotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BorrowService {

    private final MemberService memberService;

    private final LibraryRepository repository =
            new LibraryRepository();

    private final BorrowRecordRepository recordRepository =
            new BorrowRecordRepository();

    private final Map<String, LibraryItem> catalog =
            new LinkedHashMap<>();

    private final Map<String, BorrowRecord> activeLoans =
            new LinkedHashMap<>();

    private final List<BorrowRecord> allRecords =
            new ArrayList<>();

    // Constructor
    public BorrowService(MemberService memberService) {
        this.memberService = memberService;

        try {
            for (LibraryItem item : repository.loadItems()) {
                catalog.put(item.getId(), item);
            }

            System.out.println(
                    "Loaded " + catalog.size() + " library item(s).");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(
                    "Could not load library items: " + e.getMessage());
        }

        loadBorrowRecords();
    }

    // Add a new book or digital item
    public void addItem(LibraryItem item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "Item cannot be null.");
        }

        if (catalog.containsKey(item.getId())) {
            throw new IllegalArgumentException(
                    "An item with this ID already exists.");
        }

        catalog.put(item.getId(), item);
        saveCatalog();
    }

    // Find a book or digital item by ID
    public LibraryItem findItem(String itemId) {
        return catalog.get(itemId);
    }

    // Check whether an item is currently borrowed
    public boolean isItemBorrowed(String itemId) {
        return activeLoans.containsKey(itemId);
    }

    // Save the library catalog
    private void saveCatalog() {
        try {
            repository.saveItems(
                    new ArrayList<>(catalog.values()));

            System.out.println(
                    "Library catalog saved successfully.");

        } catch (IOException e) {
            System.out.println(
                    "Could not save library catalog: "
                            + e.getMessage());
        }
    }

    // Save borrowing history
    private void saveBorrowRecords() {
        try {
            recordRepository.saveRecords(allRecords);

            System.out.println(
                    "Borrowing records saved successfully.");

        } catch (IOException e) {
            System.out.println(
                    "Could not save borrowing records: "
                            + e.getMessage());
        }
    }

    // Load saved borrowing records
    private void loadBorrowRecords() {
        try {
            allRecords.addAll(
                    recordRepository.loadRecords());

            for (BorrowRecord record : allRecords) {
                String itemId = record.getItem().getId();

                if (!record.isReturned()) {
                    activeLoans.put(itemId, record);

                    LibraryItem catalogItem =
                            catalog.get(itemId);

                    if (catalogItem != null) {
                        catalogItem.setAvailable(false);
                    }
                }
            }

            System.out.println(
                    "Loaded " + allRecords.size()
                            + " borrowing record(s).");

            saveCatalog();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(
                    "Could not load borrowing records: "
                            + e.getMessage());
        }
    }

    // Borrow an item
    public BorrowRecord borrowItem(
            String memberId, String itemId)
            throws BookNotAvailableException,
                   MemberNotFoundException {

        Member member =
                memberService.findMember(memberId);

        if (member == null) {
            throw new MemberNotFoundException(
                    "Member not found. Please register first.");
        }

        LibraryItem item = catalog.get(itemId);

        if (item == null) {
            throw new IllegalArgumentException(
                    "Item not found in the library.");
        }

        if (!item.isAvailable()) {
            throw new BookNotAvailableException(
                    "This item is already borrowed.");
        }

        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusDays(14);

        BorrowRecord record = new BorrowRecord(
                member, item, today, dueDate);

        item.setAvailable(false);
        activeLoans.put(itemId, record);
        allRecords.add(record);

        saveCatalog();
        saveBorrowRecords();

        return record;
    }

    // Return an item
    public BorrowRecord returnItem(
            String memberId, String itemId) {

        BorrowRecord record = activeLoans.get(itemId);

        if (record == null) {
            throw new IllegalStateException(
                    "This item is not currently borrowed.");
        }

        if (!record.getMember().getMemberId()
                .equals(memberId)) {
            throw new IllegalArgumentException(
                    "This item was borrowed by another member.");
        }

        record.markReturned(LocalDate.now());

        LibraryItem catalogItem = catalog.get(itemId);

        if (catalogItem != null) {
            catalogItem.setAvailable(true);
        }

        activeLoans.remove(itemId);

        saveCatalog();
        saveBorrowRecords();

        return record;
    }

    // Display all borrowing records
    public void displayAllRecords() {
        if (allRecords.isEmpty()) {
            System.out.println(
                    "No borrowing records yet.");
            return;
        }

        for (BorrowRecord record : allRecords) {
            System.out.println(record);
        }
    }

    // Display overdue items and fines
    public void displayOverdueItems() {
        boolean found = false;

        for (BorrowRecord record : activeLoans.values()) {
            if (record.isOverdue()) {
                System.out.println(record);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No overdue items.");
        }
    }
}
