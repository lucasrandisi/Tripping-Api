package Triping.tasks;

import Triping.repositories.InvitationTokenRepository;
import Triping.repositories.VerificationTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private final static Logger logger = Logger.getLogger(TokenPurgeTask.class.getName());

    /**
     * Task that purges all expired tokens from the database at a fixed time t
     * where t is purge.cron.expression defined at application.properties
     */
    @Scheduled(cron = "${purge.cron.expression}")
    public void purgeExpired() {
        logger.info("[TASK] All expired tokens were deleted");
        Date now = Date.from(Instant.now());

        //passwordTokenRepository.deleteAllExpiredSince(now);
        verificationTokenRepository.deleteAllExpiredSince(now);
        invitationTokenRepository.deleteAllExpiredSince(now);
    }
}