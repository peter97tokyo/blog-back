package kr.peter.tokyo.blog.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "code")
@Getter
@Setter
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codeKey;

    @Column(nullable = false)
    private String codeValue;

    @Column(nullable = false)
    private String groupYn;

    @Column(name = "description")
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 부모 코드와의 Many-to-One 관계 설정
     * 부모 코드는 하나일 수 있음
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id") // 부모 코드의 ID를 참조
    @JsonIgnore // 순환 참조 방지
    private Code parent;

    /**
     * 하위 코드와의 One-to-Many 관계 설정
     * 하위 코드는 여러 개일 수 있음
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Code> children = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 하위 코드 추가 메서드 (편의 메서드)
     */
    public void addChild(Code child) {
        children.add(child);
        child.setParent(this);
    }

    /**
     * 하위 코드 제거 메서드 (편의 메서드)
     */
    public void removeChild(Code child) {
        children.remove(child);
        child.setParent(null);
    }
}
