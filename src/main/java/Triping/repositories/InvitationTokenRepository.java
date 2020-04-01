package Triping.repositories;

import Triping.models.Group;
import Triping.models.InvitationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.stream.Stream;

public interface InvitationTokenRepository extends JpaRepository<InvitationToken, Long> {

    InvitationToken findByToken(String token);

    InvitationToken findByGroup(Group group);

    Stream<InvitationToken> findAllByExpiryDateLessThan(Date now);

    void deleteByExpiryDateLessThan(Date now);

    @Modifying
    @Query("delete from InvitationToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Date now);
}
