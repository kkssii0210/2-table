package datajpa.study.repository;

import datajpa.study.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;
//    @Test
//    public void testMember() {
//        Member member = new Member("memberA");
//        Member saveMember = memberRepository.save(member);
//        Member findMember = memberRepository.findById(saveMember.getId()).get();
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//        Assertions.assertThat(findMember).isEqualTo(member);
//    }
}