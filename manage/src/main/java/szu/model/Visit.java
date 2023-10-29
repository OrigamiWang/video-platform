package szu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Visit implements Serializable {
    private Integer id;
    private LocalDateTime lastUpdate;
    private String api;
    private Integer visits;
    private String timeId;
}
