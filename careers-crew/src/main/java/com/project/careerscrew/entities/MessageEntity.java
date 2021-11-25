package com.project.careerscrew.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "candidate_job_id")
    private CandidateJob candidateJob;

    @Column(name = "subject")
    private String subject;

    @Column(name = "initial_msg")
    private String initialMsg;

    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private UserEntity sendingUserEntity;

    @Column(name = "reply_msg")
    private String replyMsg;

    @ManyToOne
    @JoinColumn(name = "replying_user_id")
    private UserEntity replyingUserEntity;

    @Column(name = "is_hidden", nullable = false)
    private Boolean isHidden = false;
}
