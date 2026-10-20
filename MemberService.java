
package service;

import model.Member;
import repository.MemberRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MemberService {

    private final List<Member> members = new ArrayList<>();
    private final MemberRepository repository =
            new MemberRepository();

    private int nextMemberNumber = 1;

    public MemberService() {
        try {
            members.addAll(repository.loadMembers());

            for (Member member : members) {
                String id = member.getMemberId();

                if (id.matches("M\\d+")) {
                    int number = Integer.parseInt(id.substring(1));
                    nextMemberNumber =
                            Math.max(nextMemberNumber, number + 1);
                }
            }

            System.out.println(
                    "Loaded " + members.size() + " saved member(s).");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(
                    "Could not load members: " + e.getMessage());
        }
    }

    public Member registerMember(String name, String email) {
        if (name == null || name.isBlank()
                || email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "Name and email cannot be empty.");
        }

        String memberId =
                String.format("M%03d", nextMemberNumber);
        String cardNumber =
                String.format("LIB%03d", nextMemberNumber);

        Member member = new Member(
                memberId, name, email, cardNumber);

        members.add(member);
        nextMemberNumber++;

        try {
            repository.saveMembers(members);
            System.out.println("Member saved successfully.");
        } catch (IOException e) {
            System.out.println(
                    "Could not save member: " + e.getMessage());
        }

        return member;
    }

    public Member findMember(String memberId) {
        for (Member member : members) {
            if (member.getMemberId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }

    public void displayAllMembers() {
        if (members.isEmpty()) {
            System.out.println("No members registered.");
            return;
        }

        for (Member member : members) {
            System.out.println(member);
        }
    }
}
