package com.mettre.wechat_information.vm;

import com.mettre.wechat_information.enum_.DynamicTypeEnum;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class ReplyVM {

    private String replyParentId;//该评论的父id可空

    @NotBlank(message = "资讯id不能为空")
    private String dynamicId;

    @NotBlank(message = "评论内容不能为空")
    private String replyContent;

    private DynamicTypeEnum dynamicType;
}
