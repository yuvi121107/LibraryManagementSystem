
package model;

import java.io.Serializable;

public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String memberId;
    private final String name;
    private final String email;
    private final String libraryCardNumber;

    public Member(String memberId, String name, String email,
                  String libraryCardNumber) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.libraryCardNumber = libraryCardNumber;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLibraryCardNumber() {
        return libraryCardNumber;
    }

    @Override
    public String toString() {
        return "Member ID: " + memberId
                + " | Name: " + name
                + " | Email: " + email
                + " | Card Number: " + libraryCardNumber;
    }
}
