package szu.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import szu.model.Partition;
import szu.model.Update;

import java.util.HashMap;
import java.util.List;

/***
 * @Author: zyd
 * @Date: 2023/10/31 00:22
 */
public interface UpdateService {
    /***发布新动态,返回是否成功
     *  minio、dao层插入数据库
     */
    boolean publish(int uid, String title, String content, int type, int pid, MultipartFile[] files);

    void deleteById(int id);

    boolean update(HashMap<String, Object> params, MultipartFile[] files);

    /***
     * 获取所有动态
     * @param pageNum 页码
     * @param pageSize 每页大小
     */
    List<Update> findAll(int pageNum, int pageSize);

    /***
     * 获取指定分区的动态
     */
    List<Update> findByPartition(int pid);

    /***
     * 获取指定类型的动态
     */
    List<Update> findByType(int type);

    /***
     * 获取指定用户的动态
     */
    List<Update> findByUid(int uid);

    /***
     * 获取图片
     */
    ResponseEntity<Resource> getImage(String url);

    /***
     * 获取指定动态
     */
    Update findById(int id);

    /***
     * 获取所有动态分区
     */
    List<Partition> getPartitions();

    /***
     * 删除指定动态分区
     */
    void deletePartitionById(int id);

    /***
     * 添加动态分区
     */
    void addPartition(String name);

    /***
     * 修改动态分区
     */
    void updatePartition(int id, String name);
}
