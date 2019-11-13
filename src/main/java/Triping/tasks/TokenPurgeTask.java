package Triping.tasks;

import Triping.repositories.InvitationTokenRepository;
import Triping.repositories.VerificationTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

//Server scheduled task that purges all expired tokens from the database
@Service
@Transactional
public class TokenPurgeTask {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    InvitationTokenRepository invitationTokenRepository;

    /*
    @Autowired
    PasswordResetTokenRepository passwordTokenRepository;
    */

    @Scheduled(cron = "${purge.cron.expression}") //purge.cron.expression at application.properties
    public void purgeExpired() {

        Date now = Date.from(Instant.now());

        //passwordTokenRepository.deleteAllExpiredSince(now);
        verificationTokenRepository.deleteAllExpiredSince(now);
        invitationTokenRepository.deleteAllExpiredSince(now);
    }
}