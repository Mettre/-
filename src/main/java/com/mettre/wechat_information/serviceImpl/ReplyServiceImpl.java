package com.mettre.wechat_information.serviceImpl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mettre.wechat_information.base.ReturnType;
import com.mettre.wechat_information.enum_.DynamicTypeEnum;
import com.mettre.wechat_information.enum_.ResultEnum;
import com.mettre.wechat_information.exception.CustomerException;
import com.mettre.wechat_information.mapper.ReplyMapper;
import com.mettre.wechat_information.pojo.Reply;
import com.mettre.wechat_information.service.MomentsService;
import com.mettre.wechat_information.service.NewsService;
import com.mettre.wechat_information.service.ReplyService;
import com.mettre.wechat_information.vm.ReplyVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mettre.wechat_information.enum_.DynamicTypeEnum.NEWS;

@Service
@Transactional
public class ReplyServiceImpl implements ReplyService {

    private static final Logger logger = LoggerFactory.getLogger(ReplyServiceImpl.class);


    @Autowired
    public ReplyMapper replyMapper;

    @Autowired
    public NewsService newsService;

    @Autowired
    public MomentsService momentsService;

    @Override
    public int deleteByPrimaryKey(String replyId) {
        int type = replyMapper.deleteByPrimaryKey(replyId);
        return ReturnType.ReturnType(type, ResultEnum.DELETE_ERROR);
    }

    @Override
    public int deleteByReplyIdAndDynamicUserId(String replyId, String dynamicUserId) {
        Reply reply = replyMapper.selectByPrimaryKey(replyId);
        if (!dynamicUserId.equals(reply.getDynamicUserId())) {
            throw new CustomerException("暂无权限");
        }
        int type = replyMapper.deleteByPrimaryKey(replyId);
        return ReturnType.ReturnType(type, ResultEnum.DELETE_ERROR);

    }

    @Override
    public int insert(ReplyVM replyVM) {

        if (!DynamicTypeEnum.contains(replyVM.getDynamicType().name())) {
            throw new CustomerException(ResultEnum.DYNAMICTYPE);
        }
        switch (replyVM.getDynamicType()) {
            case NEWS:
            case MOMENTS:
                if (replyVM.getDynamicType() == NEWS) {
                    newsService.selectByPrimaryKey(replyVM.getDynamicId(), null, null);
                } else {
                    momentsService.selectByPrimaryKey(replyVM.getDynamicId());
                }
                if (StrUtil.isNotBlank(replyVM.getReplyParentId())) {
                    Reply reply = selectByPrimaryKey(replyVM.getReplyParentId());

                    if (StrUtil.isNotBlank(reply.getSecondDynamicId())) {
                        return replyMapper.insert(new Reply(replyVM, reply.getReplyId(), reply.getDynamicUserId(), reply.getSecondDynamicId()));
                    } else {
                        return replyMapper.insert(new Reply(replyVM, reply.getReplyId(), reply.getDynamicUserId(), reply.getReplyId()));
                    }

                } else {
                    return replyMapper.insert(new Reply(replyVM));
                }
            case FORUM:
                return replyMapper.insert(new Reply(replyVM));

            default:
                return replyMapper.insert(new Reply(replyVM));

        }

    }

    @Override
    public int insertSelective(Reply record) {
        return 0;
    }

    @Override
    public Reply selectByPrimaryKey(String replyId) {
        Reply reply = replyMapper.selectByPrimaryKey(replyId);
        if (reply == null) {
            throw new CustomerException("该条评论不存在");
        }
        return reply;
    }

    @Override
    public int updateByPrimaryKeySelective(Reply record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Reply record) {
        return 0;
    }

    @Override
    public Page<Reply> selectPageVo(Page<Reply> page, String dynamicId,String userId) {
        List<Reply> addressList = (List<Reply>) replyMapper.selectPageVo(page, dynamicId,userId);
        page = page.setRecords(addressList);
        return page;
    }

    @Override
    public List<Reply> selectMomentsReply(String dynamicId,String userId) {
        List<Reply> replyList = (List<Reply>) replyMapper.selectMomentsReply(dynamicId,userId);
        return replyList;
    }
}
