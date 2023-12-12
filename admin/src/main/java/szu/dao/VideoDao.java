package szu.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.beans.factory.annotation.Qualifier;
import szu.common.api.CommonResult;
import szu.model.Barrage;
import szu.model.UpdateHeat;
import szu.model.Video;
import szu.model.VideoSearchDoc;
import szu.vo.BarrageVo;
import szu.vo.VideoDetailVo;

import java.util.List;

@Mapper
public interface VideoDao {
    @Insert("INSERT INTO video (url,play_num,dm_num,total_time,title,pid,star_num,coin_num) " +
            "VALUES (#{url},#{playNum},#{dmNum},#{totalTime},#{title},#{pid},#{starNum},#{coinNum})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Video video);//返回插入的id

    @Select("SELECT * FROM video WHERE id=#{id}")
    Video findById(int id);//根据id查找视频

    @Delete("DELETE FROM video WHERE id=#{id}")
    void deleteById(int id);
    @Select("select * from video")
    List<Video> selectAll();//测试用，获取所有视频

    @Select("select id from video")
    List<Integer> selectAllId();//获取所有视频id

    /**
     * 获取所有VideoSearchDoc对象，存入es用
     * @return
     */
    @Select("select up.id, up.upload_time, us.name, v.title, v.url, v.play_num, v.dm_num, v.total_time, v.pid, v.star_num " +
            "from updates up " +
            "left join video v on up.vid = v.id " +
            "left join user us on up.uid = us.id")
    List<VideoSearchDoc> selectAllVideoSearchDoc();

    /**
     * 根据动态id获取视频详情
     * @param id
     * @return
     */
    VideoDetailVo getVideoDetail(Integer id);

    /**
     * 根据vid更新video表的视频信息
     */
    void updateVideoByVid(Video video);

    /**
     * 根据视频id更新视频源
     * @param vid
     */
    void updateVideoSource(Integer vid, String url);

    /**
     * 根据视频id更新视频简介信息
     */
    void updateVideoContent(Integer id, String content);

    /**
     * 根据动态id更新视频点赞数、评论数、分享数
     */
    void updateVideoHeatByUpdatesId(UpdateHeat updateHeat);
    @Select("select `text`, `time`, `color`, `mode` from barrage where vid = #{vid}")
    List<BarrageVo> getBarrageByVid(int vid);

    @Insert("insert into `barrage` (vid, uid, text, color, time, mode) values (#{vid}, #{uid}, #{text}, #{color}, #{time}, #{mode})")
    void saveBarrage(Barrage barrage);

    @Select("select `uid`, `time`, `text`, `send_time` from barrage where vid = #{vid} ORDER BY send_time LIMIT #{offset}, #{size};")
    List<Barrage> getBarrageListByVid(int vid, int offset, int size);
}

