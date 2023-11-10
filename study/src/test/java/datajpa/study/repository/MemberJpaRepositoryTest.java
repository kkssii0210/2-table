package datajpa.study.repository;

import datajpa.study.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {
    @Autowired MemberJpaRepository memberJpaRepository;
//    @Test
//    public void testMember() {
//        Member member = new Member("memberA");
//        Member saveMember = memberJpaRepository.save(member);
//
//        Member findMember = memberJpaRepository.find(saveMember.getId());
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//    }
}