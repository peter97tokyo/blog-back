package kr.peter.tokyo.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "code")
@Getter
@Setter
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String codeKey;

    @Column(nullable = false, unique = true)
    private String codeValue;

    @Column(nullable = false)
    private String parentId;

    @Column(nullable = false)
    private String groupYn;

    @Column(name = "description")
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

     /* @PrePersist: 엔티티가 처음 데이터베이스에 삽입되기 전에 호출됩니다. 이때 createdAt 필드에 현재 시간을 설정합니다. */
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /* @PreUpdate: 엔티티가 업데이트될 때 호출됩니다. 이때 updatedAt 필드에 현재 시간을 설정합니다. */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
