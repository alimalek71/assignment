package ir.alimalek.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(indexes = @Index(columnList = "code"))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10)
    private String source;
    @Column(length = 10)
    private String codeListCode;
    @Column(length = 64, unique = true)

    private String code;
    @Column(length = 140)
    private String displayValue;
    @Column(columnDefinition = "CHARACTER VARYING", length = 1000)
    private String longDescription;
    private Date fromDate;
    private Date toDate;
    private Integer sortingPriority;

}
