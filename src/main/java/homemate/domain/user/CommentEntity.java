package homemate.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import homemate.constant.Status;
import homemate.domain.TimeStamp;
import jakarta.persistence.*;
import lombok.*;

/**
 * 커뮤니티 댓글 Entity
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentEntity extends TimeStamp {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @JsonIgnore
    private ArticleEntity article;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer complain;
}

