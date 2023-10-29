package szu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VisitMonTotal {
    private String api;
    private Integer visits;
    private String year;
    private String mon;
}
