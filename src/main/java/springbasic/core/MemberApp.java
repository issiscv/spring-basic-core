package springbasic.core;

import springbasic.core.member.Grade;
import springbasic.core.member.Member;
import springbasic.core.member.MemberService;
import springbasic.core.member.MemberServiceImpl;

public class MemberApp {

    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();

        Member memberA = new Member(1L, "memberA", Grade.VIP);
        memberService.join(memberA);

        Member findMember = memberService.findMember(memberA.getId());

        System.out.println("memberA = " + memberA.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
