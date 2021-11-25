package com.project.careerscrew.services.messaging;

import lombok.Data;

@Data
public class MessageRequestDTO {

    private Long id;

    private Long candidateJobId;

    private String subject;

    private String initialMsg;

    private Long sendingUserId;

    private String replyMsg;

    private Long replyingUserId;

    private Boolean isHidden = false;

}
