package com.mettre.wechat_information.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mettre.wechat_information.base.Result;
import com.mettre.wechat_information.base.ResultUtil;
import com.mettre.wechat_information.pojo.News;
import com.mettre.wechat_information.service.NewsService;
import com.mettre.wechat_information.vm.NewsVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@Slf4j
@RestController
@Api(description = "新闻模块")
public class NewsController {

    @Autowired
    public NewsService newsService;


    @RequestMapping(value = "/addNews", method = RequestMethod.POST)
    @ApiOperation(value = "添加新增")
    public Result<Object> insert(@Valid @RequestBody NewsVM newsVM) {
        newsService.insert(newsVM);
        return new ResultUtil<>().setSuccess();
    }

    @RequestMapping(value = "/findNewsDetails", method = RequestMethod.POST)
    @ApiOperation(value = "新闻详情")
    public Result<Object> selectByPrimaryKey(@RequestParam String newsId, String readerId, String readerEquipment) {

        return new ResultUtil<>().setData(newsService.selectByPrimaryKey(newsId, readerId, readerEquipment));
    }

    @RequestMapping(value = "/findNewsList", method = RequestMethod.POST)
    @ApiOperation(value = "新闻列表")
    public Result<Object> findCategoryList(@RequestBody HashMap<String, Object> map) {
        Integer page = Integer.parseInt(map.get("page").toString());
        Integer size = Integer.parseInt(map.get("size").toString());
        Long categoryId = null;
        if (map.get("categoryId") != null) {
            categoryId = Long.parseLong(map.get("categoryId").toString());
        }
        Page<News> page2 = new Page<>(page, size);
        Page<News> addressList = newsService.selectPageVo(page2, categoryId);
        return new ResultUtil<>().setData(addressList);
    }


}