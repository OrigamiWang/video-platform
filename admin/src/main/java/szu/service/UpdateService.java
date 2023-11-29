package szu.service;

import org.springframework.web.multipart.MultipartFile;
import szu.model.Partition;
import szu.model.Update;
import szu.model.Video;
import szu.vo.VideoVo;

import java.util.List;

/***
 * @Author: zyd
 * @Date: 2023/10/31 00:22
 */
public interface UpdateService {
/*****************************************************操作动态*******************************************************/
    /***
     * 发布图文动态,返回是否成功
     */
    void publishEssay(int uid, String content, MultipartFile[] files);

    void deleteEssayById(int id);

    boolean updateEssay(int id, String content, MultipartFile[] files);

    /***
     * 获取所有动态
     */
    List<Update> findEssayAll();
    /***
     * 分页获取动态
     */
    List<Update> findEssayInPage(int pageNum, int pageSize);

    /***
     * 获取指定用户的动态
     */
    List<Update> findEssayByUid(int uid);

    /***
     * 获取图片
     */
    byte[] getImage(String url);

    /***
     * 获取指定动态
     */
    Update findEssayById(int id);
/*****************************************************视频分区*******************************************************/
    /***
     * 获取所有视频分区
     */
    List<Partition> getPartitions();

    /***
     * 删除指定视频分区
     */
    void deletePartitionById(int id);

    /***
     * 添加视频分区
     */
    void addPartition(String name);

    /***
     * 修改视频分区
     */
    void updatePartition(int id, String name);
/*****************************************************面向前端******************************************/
    List<VideoVo> getHomePage(int pageSize);

    //发布视频
    void publishVideo(Integer id, String title, String content, Integer pid, MultipartFile video);
    void deleteVideoByVid(int id);//删除指定id的视频

    Video findVideoByVid(int id);//获取指定id的视频,返回Video
}
