package datajpa.study.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import datajpa.study.entity.Member;
import datajpa.study.entity.QMember;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public MemberJpaRepository(EntityManager em) {
        this.em=em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Member save(Member member) {
        em.persist(member);
        return member;
    }
    public void delete(Member member) {
        em.remove(member);
    }
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }
    public long count() {
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
    public List<Member> findByPage(int age, int offset, int limit) {
        return em.createQuery("""
select m from Member m where m.age = :age order by m.username desc
""").setParameter("age",age)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
    public long totalCount(int age) {
        return em.createQuery("select count(m) from Member m where m.age = :age", Long.class)
                .setParameter("age",age)
                .getSingleResult();
    }
    public List<Member> findAll_Querydsl() {
        return queryFactory.selectFrom(QMember.member)
                .fetch();
    }
    public List<Member> findByUsername(String username) {
        return queryFactory.selectFrom(QMember.member)
                .where(QMember.member.username.eq(username))
                .fetch();
    }
}
