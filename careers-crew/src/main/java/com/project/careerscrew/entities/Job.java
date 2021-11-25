package com.project.careerscrew.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String position;
    @Column(columnDefinition="TEXT")
    private String description;


    private Boolean isActive;

    public Job(Long id, String title, boolean isActive,String description) {
        this.id = id;
        this.title = title;
        this.isActive = isActive;
        this.description = description;
    }
}
