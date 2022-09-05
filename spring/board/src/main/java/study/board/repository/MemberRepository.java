package study.board.repository;

import org.springframework.stereotype.Repository;
import study.board.domain.member.Member;

import java.util.*;

/**
 * 회원 저장소
 */
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();   //  실무에선 동시성 문제로 ConcurrentHashMap 사용을 고려한다.
    private static long sequence = 0L;

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findId(Long id) {
        return store.get(id);
    }

    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
