package com.quiz.server.service.impl;

import com.quiz.server.exception.QRuntimeException;
import com.quiz.server.helper.EmailType;
import com.quiz.server.model.entity.User;
import com.quiz.server.model.entity.VerificationToken;
import com.quiz.server.repository.UserRepository;
import com.quiz.server.repository.VerificationRepository;
import com.quiz.server.service.VerificationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static com.quiz.server.exception.ExceptionCode.E001;
import static com.quiz.server.exception.ExceptionCode.E004;
import static com.quiz.server.exception.ExceptionCode.E005;
import static com.quiz.server.exception.ExceptionCode.E006;
import static com.quiz.server.helper.EmailType.REGISTER;

@Service
@AllArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final VerificationRepository verificationRepository;
    private final UserRepository userRepository;

    @Override
    public VerificationToken generateVerificationToken(String email) {
        String token = UUID.randomUUID().toString();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(15);
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .email(email)
                .expirationTime(expirationTime)
                .build();

        verificationRepository.save(verificationToken);

        return verificationToken;
    }

    @Override
    public void verifyToken(String token, EmailType emailType) {
        Optional<VerificationToken> verificationToken = Optional.ofNullable(verificationRepository.findByToken(token)
                .orElseThrow(() -> new QRuntimeException(E004)));

        if (verificationToken.isPresent()) {
            VerificationToken tokenEntity = verificationToken.get();
            checkToken(tokenEntity);
            User user = userRepository.findByEmail(tokenEntity.getEmail())
                    .orElseThrow(() -> new QRuntimeException(E001));

            if (emailType == REGISTER) {
                if (user.getVerified())
                    throw new QRuntimeException(E006);

                user.setVerified(true);
                userRepository.save(user);
                verificationRepository.delete(tokenEntity);
            }
        }

        else throw new QRuntimeException(E004);
    }

    @Override
    public String getEmailByToken(String token) {
        Optional<VerificationToken> verificationToken = verificationRepository.findByToken(token);

        if (verificationToken.isPresent()) {
            VerificationToken tokenEntity = verificationToken.get();
            checkToken(tokenEntity);
            String email = tokenEntity.getEmail();
            verificationRepository.delete(tokenEntity);

            return email;
        }

        else throw new QRuntimeException(E004);
    }

    private void checkToken(VerificationToken token) {
        if (token.getExpirationTime().isBefore(LocalDateTime.now())) {
            verificationRepository.delete(token);
            throw new QRuntimeException(E005);
        }
    }
}
