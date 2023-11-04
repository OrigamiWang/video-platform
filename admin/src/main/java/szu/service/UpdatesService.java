package szu.service;

import org.springframework.web.multipart.MultipartFile;
import szu.model.Updates;

import java.util.List;

/***
 * @Author: zyd
 * @Date: 2023/10/31 00:22
 */
public interface UpdatesService {
    /***插入新动态,返回是否成功
     *  minio、dao层插入数据库
     */
    boolean publish(int uid, String title, String content, String type, MultipartFile[] urls);
    void deleteById(int id);
    boolean update(Updates updates, MultipartFile[] files);

    /***
     * 获取所有动态,返回List<Updates>,按时间倒序
     */
    List<Updates> findAll();

    /***
     * 获取指定分区的动态,返回List<Updates>,按时间倒序
     */
    List<Updates> findByType(String type);

    /***
     * 获取指定用户的动态,返回List<Updates>,按时间倒序
     */
    List<Updates> findByUid(int uid);
    /***
     * 获取图片
     */
    byte[] getImage(String url);
}
