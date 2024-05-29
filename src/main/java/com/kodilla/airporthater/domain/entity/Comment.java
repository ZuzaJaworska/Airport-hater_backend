package com.kodilla.airporthater.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "COMMENTS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_ID", unique = true)
    private Long id;

    @Column(name = "COMMENT_TITLE", nullable = false)
    private String title;

    @Column(name = "COMMENT_BODY")
    private String body;

    @Column(name = "COMMENT_SCORE", nullable = false)
    private int score;

    @Column(name = "COMMENT_CREATED_AT", nullable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AIRPORT_ID", nullable = false)
    private Airport airport;

    public Comment(Long id, String title, String body, int score, User user) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.score = score;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Comment{" +
                ", createdAt=" + createdAt +
                ", score=" + score +
                ", body='" + body + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                ", user=" + user +
                '}';
    }

}
