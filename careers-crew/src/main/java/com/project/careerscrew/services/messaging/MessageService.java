package com.project.careerscrew.services.messaging;

import com.project.careerscrew.entities.MessageEntity;

import java.util.List;

public interface MessageService {

    MessageEntity sendMessage(MessageRequestDTO messageRequestDTO);

    MessageEntity replyMessage(MessageRequestDTO messageRequestDTO);

    List<MessageEntity> viewAllMessageByCandidateJobId(Long candidateJobId);
}
