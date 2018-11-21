package com.mettre.wechat_information.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mettre.wechat_information.base.Result;
import com.mettre.wechat_information.base.ResultUtil;
import com.mettre.wechat_information.enum_.DynamicTypeEnum;
import com.mettre.wechat_information.pojo.News;
import com.mettre.wechat_information.pojo.Reply;
import com.mettre.wechat_information.service.ReplyService;
import com.mettre.wechat_information.vm.NewsVM;
import com.mettre.wechat_information.vm.ReplyVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@Slf4j
@RestController
@Api(description = "评论")
public class ReplyController {

    @Autowired
    public ReplyService replyService;

    @RequestMapping(value = "/addReply", method = RequestMethod.POST)
    @ApiOperation(value = "添加评论")
    public Result<Object> insert(@Valid @RequestBody ReplyVM replyVM) {
        replyService.insert(replyVM);
        return new ResultUtil<>().setSuccess();
    }

    @RequestMapping(value = "/findReplyList", method = RequestMethod.POST)
    @ApiOperation(value = "评论列表")
    public Result<Object> findCategoryList(@RequestBody HashMap<String, Object> map) {
        Integer page = Integer.parseInt(map.get("page").toString());
        Integer size = Integer.parseInt(map.get("size").toString());
        String dynamicId = map.get("dynamicId").toString();
        DynamicTypeEnum dynamicTypeEnum = DynamicTypeEnum.valueOf(map.get("dynamicType").toString());
        Page<Reply> page2 = new Page<>(page, size);
        Page<Reply> addressList = replyService.selectPageVo(page2, dynamicId, dynamicTypeEnum);
        return new ResultUtil<>().setData(addressList);
    }

}
