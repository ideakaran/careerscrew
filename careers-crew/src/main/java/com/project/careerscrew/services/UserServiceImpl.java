package com.project.careerscrew.services;


import com.project.careerscrew.config.AppProperties;
import com.project.careerscrew.dto.GenericResponseDTO;
import com.project.careerscrew.dto.auth.ForgotPasswordRequestDTO;
import com.project.careerscrew.dto.auth.PasswordResetVerRequestDTO;
import com.project.careerscrew.entities.UserEntity;
import com.project.careerscrew.repository.CandidateJobRepository;
import com.project.careerscrew.repository.UserRepository;
import com.project.careerscrew.security.UserRole;
import com.project.careerscrew.services.mail.EmailService;
import com.project.careerscrew.services.mail.MessageTemplateCodeUtil;
import com.project.careerscrew.utils.AppUtils;
import com.project.careerscrew.utils.exceptions.AppExceptionConstants;
import com.project.careerscrew.utils.exceptions.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppProperties appProperties;
    private final CandidateJobRepository candidateJobRepository;
    private final EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AppProperties appProperties, EmailService emailService, CandidateJobRepository candidateJobRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.appProperties = appProperties;
        this.emailService = emailService;
        this.candidateJobRepository = candidateJobRepository;
    }

    @Override
    public UserEntity findUserByEmail(String userEmail) {
        UserEntity userEntity = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(AppExceptionConstants.USER_RECORD_NOT_FOUND));
        return userEntity;
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        if (ObjectUtils.isEmpty(userEntity.getRoles())) {
            userEntity.setRoles(Set.of(UserRole.ROLE_USER));
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public UserEntity getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(AppExceptionConstants.USER_RECORD_NOT_FOUND));
        return userEntity;
    }

    @Override
    public UserEntity updateUser(UserEntity reqUserEntity) {
        UserEntity userEntity = userRepository.findById(reqUserEntity.getId())
                .orElseThrow(() -> new ResourceNotFoundException(AppExceptionConstants.USER_RECORD_NOT_FOUND));
        userEntity.setFullName(reqUserEntity.getFullName());
        userEntity.setUsersoln(reqUserEntity.getUsersoln());
        userRepository.save(userEntity);
        return userEntity;
    }

    @Override
    public GenericResponseDTO<Boolean> sendForgotPasswordResetEmail(ForgotPasswordRequestDTO forgotPasswordRequestDTO) {
        UserEntity userEntity = userRepository.findByEmail(forgotPasswordRequestDTO.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(AppExceptionConstants.USER_EMAIL_NOT_AVAILABLE));
        String forgotPasswordVerCode = AppUtils.generateRandomAlphaNumericString(20);
        long verificationCodeExpirationSeconds = appProperties.getMail().getVerificationCodeExpirationSeconds();
        userEntity.setVerificationCodeExpiresAt(LocalDateTime.now().plusSeconds(verificationCodeExpirationSeconds));
        userEntity.setVerificationCode(forgotPasswordVerCode);
        userRepository.save(userEntity);

        // Sending password reset link
        Map<String, Object> prTemplateData = constructPasswordResetMailTemplateData(userEntity);
        emailService.sendMessageUsingFreemarkerTemplate(
                userEntity.getEmail(), MessageTemplateCodeUtil.subjectForgotPasswordResetProcess,
                prTemplateData, MessageTemplateCodeUtil.TemplatesPath.PASSWORD_RESET_TEMPLATE);
        GenericResponseDTO<Boolean> genericResponseDTO = GenericResponseDTO.<Boolean>builder().response(Boolean.TRUE).build();
        return genericResponseDTO;
    }

    @Override
    public GenericResponseDTO<Boolean> verifyAndProcessPasswordResetVerRequest(PasswordResetVerRequestDTO passwordResetVerRequestDTO) {
        Optional<UserEntity> optionalUserEntity = userRepository.verifyPasswordResetVerificationRequest(
                passwordResetVerRequestDTO.getEmail(), passwordResetVerRequestDTO.getForgotPasswordVerCode());
        UserEntity userEntity = optionalUserEntity
                .orElseThrow(() -> new ResourceNotFoundException(AppExceptionConstants.INVALID_REQUEST));
        userEntity.setVerificationCodeExpiresAt(null);
        userEntity.setVerificationCode(null);
        userEntity.setPassword(passwordEncoder.encode(passwordResetVerRequestDTO.getNewPassword()));
        userRepository.save(userEntity);
        GenericResponseDTO<Boolean> emailVerifiedResponseDTO = GenericResponseDTO.<Boolean>builder().response(true).build();
        return emailVerifiedResponseDTO;
    }

    private Map<String, Object> constructPasswordResetMailTemplateData(UserEntity userEntity) {
        String linkPasswordReset = UriComponentsBuilder.fromUriString(appProperties.getFrontSite() + "/verify")
                .queryParam("isProcessPasswordReset", true)
                .queryParam("email", userEntity.getEmail())
                .queryParam("forgotPasswordVerCode", userEntity.getVerificationCode())
                .build().toUriString();

        // Populate the template data for forgot password mail template
        Map<String, Object> templateData = new HashMap<>();
        templateData.put(MessageTemplateCodeUtil.TemplateKeys.fullName, userEntity.getFullName());
        templateData.put(MessageTemplateCodeUtil.TemplateKeys.linkForgotPasswordReset, linkPasswordReset);
        return templateData;
    }

    // After User submits resume with referrer email
    // construct eg. https://localhost:4200/verify-request?isReferrerVerification=true&verificationCode=safa313afa
    //    String linkForReferrerVerification = UriComponentsBuilder.fromUriString(trackingSite + "/verify-request")
    //            .queryParam("isProcessVerifyFromReferrer", true)
    //            .queryParams(extrasFreemarkerValues.get("vQueryParams"))
    //            .build().toUriString();
    //            templateData.put(MessageTemplateCodeUtil.TemplateKeys.linkForReferrerVerification, linkForReferrerVerification);

}
