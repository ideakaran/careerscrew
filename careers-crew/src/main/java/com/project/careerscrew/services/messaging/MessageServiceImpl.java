package com.project.careerscrew.services.messaging;

import com.project.careerscrew.entities.CandidateJob;
import com.project.careerscrew.entities.MessageEntity;
import com.project.careerscrew.entities.UserEntity;
import com.project.careerscrew.repository.CandidateJobRepository;
import com.project.careerscrew.repository.MessageRepository;
import com.project.careerscrew.security.ExtractAuthUser;
import com.project.careerscrew.services.mail.EmailService;
import com.project.careerscrew.services.mail.MessageTemplateCodeUtil;
import com.project.careerscrew.utils.exceptions.AppExceptionConstants;
import com.project.careerscrew.utils.exceptions.CustomAppException;
import com.project.careerscrew.utils.exceptions.ForbiddenAccessException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Service
@Slf4j
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final CandidateJobRepository candidateJobRepository;
    private final EmailService emailService;

    @Override
    public MessageEntity sendMessage(MessageRequestDTO messageRequestDTO) {
        CandidateJob candidateJob = checkAccessAndRetrieveCandidateJob(messageRequestDTO);
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setCandidateJob(candidateJob);

        // Populating message entity
        messageEntity.setSubject(messageRequestDTO.getSubject());
        messageEntity.setSendingUserEntity(getSendingOrReplyingUserEntity());
        messageEntity.setInitialMsg(messageRequestDTO.getInitialMsg());
        messageRepository.save(messageEntity);
        return messageEntity;
    }

    @Override
    public MessageEntity replyMessage(MessageRequestDTO messageRequestDTO) {
        CandidateJob candidateJob = checkAccessAndRetrieveCandidateJob(messageRequestDTO);
        MessageEntity messageEntity = messageRepository.findById(messageRequestDTO.getId())
                .orElseThrow(() -> new CustomAppException(AppExceptionConstants.INTERNAL_EXCEPTION));

        // Populating message entity
        messageEntity.setReplyingUserEntity(getSendingOrReplyingUserEntity());
        messageEntity.setReplyMsg(messageRequestDTO.getReplyMsg());
        messageRepository.save(messageEntity);
        return messageEntity;
    }

    @Override
    public List<MessageEntity> viewAllMessageByCandidateJobId(Long candidateJobId) {
        return messageRepository.findAllByCandidateJobId(candidateJobId);
    }

    private CandidateJob checkAccessAndRetrieveCandidateJob(MessageRequestDTO messageRequestDTO) {
        CandidateJob candidateJob = candidateJobRepository.findById(messageRequestDTO.getCandidateJobId())
                .orElseThrow(() -> new CustomAppException(AppExceptionConstants.INTERNAL_EXCEPTION));
        // User can only send message on the specific CandidateJob
        // Permission allowed if user is admin OR trying to send message on their own Application i.e Own CandidateJob
        boolean hasAdminAccessOrMatchesUserId = ExtractAuthUser.hasAdminAccessOrMatchesUserId(candidateJob.getUserEntity().getId());
        if (!hasAdminAccessOrMatchesUserId) {
            throw new ForbiddenAccessException(AppExceptionConstants.FORBIDDEN_ACCESS);
        }
        return candidateJob;
    }

    private UserEntity getSendingOrReplyingUserEntity() {
        Supplier<? extends RuntimeException> exceptionSupplier = () -> new CustomAppException(AppExceptionConstants.INTERNAL_EXCEPTION);
        Long senderOrReplyingUserId = ExtractAuthUser.getCurrentUserId().orElseThrow(exceptionSupplier);
        UserEntity senderOrReplyingUserEntity = new UserEntity();
        senderOrReplyingUserEntity.setId(senderOrReplyingUserId);
        return senderOrReplyingUserEntity;
    }

    // To implement for message send/reply if required
    private void sendMessageCommunicationToCandidate(CandidateJob candidateJob, String msgDescription) {
        // sending email to user, if only update is done by Admin
        if (ExtractAuthUser.isAdmin()) {
            Map<String, Object> templateMap = new HashMap<>();
            templateMap.put("fullName", candidateJob.getUserEntity().getFullName());
            templateMap.put("description", msgDescription);
            emailService.sendMessageUsingFreemarkerTemplate(candidateJob.getUserEntity().getEmail(), "We have message for you.", templateMap, MessageTemplateCodeUtil.TemplatesPath.MESSAGE_COMMUNICATION_TEMPLATE, true);
        }
    }
}
