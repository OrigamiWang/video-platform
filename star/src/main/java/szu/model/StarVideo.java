package szu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName: StarDetail
 * @Description: 收藏内容信息
 * @Version 1.0
 * @Date: 2023-11-28 10:32
 * @Auther: UserXin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StarVideo implements Serializable {
    @Serial
    private static final long serialVersionUID = 19826192849347L;
    private Integer updateId;
    private LocalDateTime starDate;
}
