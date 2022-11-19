package ir.alimalek.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class InformationDTO {
    private Long id;
    private String source;
    private String codeListCode;
    private String code;
    private String displayValue;
    private String longDescription;
    private Date fromDate;
    private Date toDate;
    private Integer sortingPriority;
}
