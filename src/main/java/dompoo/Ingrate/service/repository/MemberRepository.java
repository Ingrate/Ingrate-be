package dompoo.Ingrate.service.repository;

import dompoo.Ingrate.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
    Boolean existsByUsername(String username);
    Optional<Member> findByUsernameAndPassword(String username, String password);
}
