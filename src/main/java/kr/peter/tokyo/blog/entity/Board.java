package kr.peter.tokyo.blog.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "board")
public class Board {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String writer;

    @Column
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private int views;

    @Column(nullable = false)
    private String category;

    @Column
    private String thumbnail;

    @ElementCollection // @ElementCollection: 이 필드는 엔티티가 아니라 간단한 값의 컬렉션(리스트나 맵)임을 나타냅니다. 여기서는 List<String> 형태의 첨부 파일 리스트를 의미합니다.
    @CollectionTable(name = "board_attachments", joinColumns = @JoinColumn(name = "board_id")) // @CollectionTable: 컬렉션 데이터가 저장될 별도의 테이블을 정의합니다. name = "board_attachments"는 테이블 이름을, joinColumns = @JoinColumn(name = "board_id")는 이 테이블이 부모 엔티티(Board)와 board_id로 연결된다는 것을 나타냅니다.
    @Column(name = "attachment")
    private List<String> attachments;

    @Column(nullable = false)
    private boolean isVisible;
    
}
