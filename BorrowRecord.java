
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BorrowRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final double FINE_PER_DAY = 5.0;

    private final Member member;
    private final LibraryItem item;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;

    public BorrowRecord(Member member, LibraryItem item,
                        LocalDate borrowDate, LocalDate dueDate) {
        this.member = member;
        this.item = item;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.returnDate = null;
    }

    public Member getMember() {
        return member;
    }

    public LibraryItem getItem() {
        return item;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void markReturned(LocalDate returnDate) {
        if (returnDate == null || returnDate.isBefore(borrowDate)) {
            throw new IllegalArgumentException(
                    "Return date cannot be before the borrow date."
            );
        }

        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public boolean isOverdue() {
        LocalDate checkDate =
                (returnDate == null) ? LocalDate.now() : returnDate;

        return checkDate.isAfter(dueDate);
    }

    public long getOverdueDays() {
        LocalDate checkDate =
                (returnDate == null) ? LocalDate.now() : returnDate;

        if (!checkDate.isAfter(dueDate)) {
            return 0;
        }

        return ChronoUnit.DAYS.between(dueDate, checkDate);
    }

    public double calculateFine() {
        return getOverdueDays() * FINE_PER_DAY;
    }

    @Override
    public String toString() {
        return "Member: " + member.getName()
                + " | Item: " + item.getTitle()
                + " | Borrowed: " + borrowDate
                + " | Due: " + dueDate
                + " | Returned: "
                + (returnDate == null ? "Not yet" : returnDate)
                + " | Overdue: " + isOverdue()
                + " | Fine: Rs. " + calculateFine();
    }
}
