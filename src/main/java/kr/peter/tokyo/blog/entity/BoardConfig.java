package kr.peter.tokyo.blog.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor // 기본 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 모든 필드를 포함한 생성자를 자동으로 생성합니다.
@Builder
@Table(name = "board_config")
public class BoardConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String boardName;

    @Column(nullable = false)
    private boolean isActive;

    @Column(name = "blocked_extension", length = 500)
    private String blockedExtensions;

    @Column(nullable = false)
    private String boardType;

    @Column
    private String boardCategory;  

}
