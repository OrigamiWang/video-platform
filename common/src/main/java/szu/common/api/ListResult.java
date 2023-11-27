package szu.common.api;

import lombok.Data;

import java.util.List;

/**
 * 通用列表结果封装类
 * @param <T> 列表元素类型
 */
@Data
public class ListResult<T> {
    private List<T> list;
    private Long total;

    public ListResult() {
    }

    /**
     * 构造结果
     * @param list 列表
     * @param total 全部数据的总数 (因为有分页, 不一定是list.size())
     */
    public ListResult(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    /**
     * 构造结果
     * @param list 列表
     */
    public ListResult(List<T> list) {
        this.list = list;
        if (list != null) {
            this.total = (long) list.size();
        }
    }
}
