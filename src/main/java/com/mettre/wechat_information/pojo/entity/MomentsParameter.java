package com.mettre.wechat_information.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mettre.wechat_information.enum_.MomentsTypeEnum;
import com.mettre.wechat_information.pojo.entity.inside.UserBean;
import lombok.Data;

import java.util.Date;

@Data
public class MomentsParameter {

    private String momentsId;

    private String momentsTitle;

    private Date creationTime;

    private String momentsImage;//暂时只单张图

    private UserBean userBean;

    private MomentsTypeEnum momentsType;

    @JsonIgnore
    private Boolean isShow;



}