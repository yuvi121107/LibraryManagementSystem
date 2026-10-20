# Library Management System

A Java-based Library Management System developed as part of the **CodeNova Java Development Internship**.

The application uses object-oriented programming (OOP) to manage library members, physical books, digital media, borrowing and returning operations, borrowing history, and overdue fines. It provides a menu-driven console interface and stores data in local files so that records can be loaded again when the application restarts.

## Features

- **Member registration** with a member ID and library card number.
- **Physical book management**, including book ID, title, author, and shelf location.
- **Digital media management**, including media ID, title, creator, and file format.
- **Borrow and return operations** with availability checks.
- **Borrowing history** showing the member, item, borrow date, due date, return date, overdue status, and fine.
- **Overdue tracking** for items that have not been returned by their due date.
- **Fine calculation** at ₹5 per overdue day, as implemented in `BorrowRecord`.
- **File-based persistence** for members, library items, and borrowing records.
- **Custom exceptions** for unavailable items and missing members.
- **Console menu** for using the main features without editing source code.

> **Note:** The application currently uses Java object serialization to local `.dat` files, not a database. Verify overdue-fine calculations and all edge cases before describing them as fully tested.

## Technologies Used

- Java
- Object-oriented programming (encapsulation, inheritance, abstraction, and polymorphism)
- Java Collections (`List`, `Map`, `ArrayList`, `LinkedHashMap`)
- Java file I/O and object serialization
- `LocalDate` for borrowing, due, and return dates
- Custom exceptions

## Project Structure

```text
LibraryManagementSystem/
├── src/
│   ├── model/
│   │   ├── LibraryItem.java
│   │   ├── PhysicalBook.java
│   │   ├── DigitalMedia.java
│   │   ├── Member.java
│   │   └── BorrowRecord.java
│   ├── service/
│   │   ├── MemberService.java
│   │   └── BorrowService.java
│   ├── repository/
│   │   ├── MemberRepository.java
│   │   ├── LibraryRepository.java
│   │   └── BorrowRecordRepository.java
│   ├── exception/
│   │   ├── BookNotAvailableException.java
│   │   └── MemberNotFoundException.java
│   ├── ui/
│   │   └── LibraryApp.java
│   ├── TestModels.java
│   ├── TestMembers.java
│   ├── TestBorrowing.java
│   ├── TestOverdue.java
│   ├── TestMemberPersistence.java
│   └── TestHistory.java
├── data/
│   ├── members.dat
│   ├── library-items.dat
│   └── borrow-records.dat
├── out/                 # Compiled .class files (generated)
└── README.md
```

The exact test files in your project may differ. Keep the files that actually exist in your `src` directory. The `data/` directory and `.dat` files are created by the application when data is saved.

## Requirements

- JDK installed (Java 11 or newer recommended).
- A terminal such as PowerShell, Command Prompt, or the VS Code terminal.
- VS Code is optional; any Java-compatible editor and terminal can be used.

Check that Java is installed:

```powershell
java -version
javac -version
```

Both commands should print a version number.

## How to Compile and Run

Open a terminal in the project root directory.

### 1. Compile the application

For Windows PowerShell:

```powershell
javac -d out src\model\*.java src\exception\*.java src\repository\*.java src\service\*.java src\ui\LibraryApp.java
```

This command compiles the application classes and places the generated `.class` files in `out/`.

### 2. Run the application

```powershell
java -cp out ui.LibraryApp
```

### 3. Use the menu

The console menu provides these options:

```text
===== LIBRARY MANAGEMENT SYSTEM =====
1. Register Member
2. Add Physical Book
3. Add Digital Media
4. Display Library Items
5. Borrow Item
6. Return Item
7. View Borrowing History
8. View Overdue Items
9. Display Members
0. Exit
```

Enter the number for the operation you want, then follow the prompts. The menu appears again after each operation until you choose `0` to exit.

## How to Use

### Register a member

1. Select `1`.
2. Enter the member's name and email.
3. The application generates a member ID and library card number.
4. Use the displayed member ID when borrowing or returning an item.

### Add a physical book

1. Select `2`.
2. Enter a unique book ID, title, author, and shelf location.
3. Select `4` to check the catalog.

Example values:

```text
Book ID: B102
Title: Python Basics
Author: John Smith
Shelf location: Shelf A2
```

### Add digital media

1. Select `3`.
2. Enter a unique media ID, title, creator, and file format.

Example values:

```text
Media ID: D101
Title: Java Programming PDF
Creator: James
Format: PDF
```

### Borrow an item

1. Select `5`.
2. Enter a registered member ID.
3. Enter an existing item ID.
4. The application checks that the member exists and the item is available.
5. The due date is set to 14 days after the borrow date.

### Return an item

1. Select `6`.
2. Enter the member ID that borrowed the item.
3. Enter the item ID.
4. The application records the return date and makes the item available again.

### View history and overdue items

- Select `7` to display saved borrowing records.
- Select `8` to display currently borrowed items that are overdue.
- Select `9` to display registered members.

## Persistence and Data Files

The application stores data locally using Java object serialization:

| File | Purpose |
|---|---|
| `data/members.dat` | Registered members |
| `data/library-items.dat` | Physical books and digital media |
| `data/borrow-records.dat` | Borrowing and return history |

The repository classes read and write these files. The application loads saved data when it starts and saves changes during member registration, item creation, borrowing, and returning.

**Important:** These `.dat` files are Java-serialized data files, not human-readable text or SQL database files. Keep them if you want to retain the saved records. Deleting them can remove the corresponding saved data.

## Overdue and Fine Rules

The `BorrowRecord` class uses these rules:

- Standard borrowing period: **14 days**.
- Fine rate: **₹5 per overdue day**.
- An item is overdue when the relevant return/check date is after its due date.
- The fine is calculated as:

```text
Fine = Number of overdue days × ₹5
```

For example, 3 overdue days result in a fine of ₹15. The actual result depends on the borrow, due, and return dates saved in the record.

## Object-Oriented Design

The project demonstrates the following OOP concepts:

- **Encapsulation:** Class fields are private and accessed through methods.
- **Abstraction:** `LibraryItem` is an abstract base class that defines common item behaviour.
- **Inheritance:** `PhysicalBook` and `DigitalMedia` extend `LibraryItem`.
- **Polymorphism:** Both item types can be stored and handled through the common `LibraryItem` type, and each supplies its own item type.
- **Separation of responsibilities:** Model classes represent data, service classes implement operations, repository classes handle file storage, and `LibraryApp` provides the console interface.

## Class Diagram Description

The main relationships are:

- `LibraryItem` is an abstract superclass.
- `PhysicalBook` extends `LibraryItem`.
- `DigitalMedia` extends `LibraryItem`.
- `BorrowRecord` connects a `Member` with a `LibraryItem` and stores borrow, due, and return dates.
- `MemberService` manages member registration and lookup using `MemberRepository`.
- `BorrowService` manages the catalog and borrowing lifecycle using `LibraryRepository` and `BorrowRecordRepository`.
- `LibraryApp` calls the service classes to handle user menu selections.
- `BookNotAvailableException` and `MemberNotFoundException` report business-rule errors.

A simplified text diagram:

```text
                  LibraryItem (abstract)
                   /              \
                  /                \
          PhysicalBook          DigitalMedia

Member  -------------------  BorrowRecord
                                  |
                                  |
                             LibraryItem

LibraryApp
    |
    +---- MemberService ---- MemberRepository
    |
    +---- BorrowService ---- LibraryRepository
                  |
                  +--------- BorrowRecordRepository

BorrowService ---- BookNotAvailableException
BorrowService ---- MemberNotFoundException
```

## Testing Checklist

Before submission, run the application and verify:

- [ ] A member can be registered and appears in the member list.
- [ ] A physical book can be added and appears in the catalog.
- [ ] Digital media can be added and appears in the catalog.
- [ ] A registered member can borrow an available item.
- [ ] An unavailable item cannot be borrowed a second time.
- [ ] The correct member can return a borrowed item.
- [ ] Borrowing history remains available after restarting the application.
- [ ] Overdue-day and fine calculations work for both overdue and on-time returns.
- [ ] Invalid member and item IDs produce understandable messages.

## Screenshots for Submission

Add screenshots of your own running application to a folder such as `screenshots/`. Useful screenshots include:

1. Main menu.
2. Successful member registration.
3. Physical book and digital media in the catalog.
4. Successful borrowing.
5. Successful return.
6. Borrowing history.
7. Overdue item and fine calculation, using a test case that you have verified.

If you add screenshots to this repository, link them here using relative paths, for example:

```markdown
![Main menu](screenshots/main-menu.png)
```

Only include screenshots that you have actually captured.

## Known Limitations

- Data is stored in local serialized files rather than SQLite or another database.
- This is a console application; it does not currently provide a graphical interface.
- Fine calculation and edge cases should be tested with dates that are overdue, due today, and returned on time.
- The application is intended as an internship learning project, not a production library system.

## Future Improvements

- Add automated unit tests.
- Add search by title, author, or item ID.
- Validate email formats and prevent duplicate member registrations.
- Improve error handling for file read/write failures.
- Add a graphical interface or web interface.
- Replace serialized files with SQLite/JDBC if a database is required.

## Author

Developed as a Java Development Internship project for **CodeNova**.

---

**Project status:** Core console operations have been run locally. Complete the testing checklist and capture your own screenshots before final submission.
