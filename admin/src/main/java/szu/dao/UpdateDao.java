package szu.dao;


import szu.model.Update;

import java.util.List;

public interface UpdateDao {
    List<Update> findInPage(int pageNum, int pageSize);//获取所有动态,返回List<Update>
    List<Update> findByUid(int uid);//获取指定用户的动态,返回List<Update>
    Update findById(int id);//获取指定id的动态,返回Update
    void update(int id, int vid, int uid, String content, int status, String datetime, String urls);//更新指定id的动态
    void insert(int vid, int uid, String content, int status, String datetime, String urls);//插入动态
    void deleteById(int id);//删除指定id的动态
    List<Update> findAll();

    void deleteByVid(int id);

    Update findByVid(int id);
}
/// @ApiModelProperty(value = "动态id")
//    private Integer id;
//
//    @ApiModelProperty("视频id")
//    private Integer vid;
//
//    @ApiModelProperty(value = "发布者的用户id")
//    private Integer uid;
//
//    @ApiModelProperty(value = "正文")
//    private String content;
//
//    @ApiModelProperty(value = "状态码")
//    private Integer status;
//
//    @ApiModelProperty(value = "时间")
//    private Date datetime;
//
//    @ApiModelProperty(value = "多媒体urls的json")
//    private String urls;