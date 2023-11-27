package szu.common.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * total表示返回结果的总条数，list返回对象
 * @param <T>
 */
@Getter
@Setter
@AllArgsConstructor
public class ListResult<T> {
    private List<T> list;
    private Long total;
}
