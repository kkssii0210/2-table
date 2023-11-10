package datajpa.study.entity;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class QuerydslBasicTest {
    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;
    @BeforeEach
    public void before() {
        queryFactory=new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }
    @Test
    public void startJPQL() {
        //member1을 찾아라
        Member findByJPQL = em.createQuery("select m from Member m where m.username=:username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();
        assertThat(findByJPQL.getUsername()).isEqualTo("member1");
    }
    @Test
    public void startQuerydsl() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QMember m = new QMember("m");
        Member findMember = queryFactory
                .select(m)
                .from(m)
                .where(m.username.eq("member1"))
                .fetchOne();
        assertThat(findMember.getUsername()).isEqualTo("member1");
    }
    @Test
    public void sort() {
        QMember m = new QMember("m");
        em.persist(new Member(null,100));
        em.persist(new Member("member5",100));
        em.persist(new Member("member6",100));
        List<Member> result = queryFactory
                .selectFrom(m)
                .where(m.age.eq(100))
                .orderBy(m.age.desc(), m.username.asc().nullsLast())
                .fetch();
        Member member5 = result.get(0);
        Member member6 = result.get(1);
        Member memberNull = result.get(2);
        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();
    }
    @Test
    public void paging1() {
        QMember m= new QMember("m");
        List<Member> result = queryFactory.selectFrom(m)
                .orderBy(m.username.desc())
                .offset(1)
                .limit(2)
                .fetch();
        assertThat(result.size()).isEqualTo(2);
    }

    // 팀 A에 소속된 모든 회원
    @Test
    public void join() {
        QMember m = new QMember("m");
        QTeam t = new QTeam("t");
        List<Member> result = queryFactory.selectFrom(m)
                .join(m.team, t)
                .where(t.name.eq("teamA"))
                .fetch();
        assertThat(result)
                .extracting("username")
                .containsExactly("member1","member2");
    }
    // 팀 이름이 teamA인 팀만 조인, 회원은 모두 조회
    @Test
    public void joinOn() {
        QMember m = new QMember("m");
        QTeam t = new QTeam("t");
        List<Tuple> result = queryFactory.select(m, t)
                .from(m)
                .leftJoin(m.team, t).on(t.name.eq("teamA"))
                .fetch();
        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }
}
