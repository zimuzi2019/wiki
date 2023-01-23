package com.cslee.wiki.service;

import com.cslee.wiki.domain.Ebook;
import com.cslee.wiki.domain.EbookExample;
import com.cslee.wiki.mapper.EbookMapper;
import com.cslee.wiki.req.EbookReq;
import com.cslee.wiki.resp.EbookResp;
import com.cslee.wiki.util.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    public List<EbookResp> list(EbookReq ebookReq){
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(ebookReq.getName())) {
            criteria.andNameLike("%" + ebookReq.getName() + "%");
        }
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        //List<EbookResp> respList = new ArrayList<>();
        //for (Ebook ebook : ebookList) {
        //    //EbookResp ebookResp = new EbookResp();
        //    //BeanUtils.copyProperties(ebook, ebookResp);
        //    //对象复制
        //    EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
        //    respList.add(ebookResp);
        //}

        List<EbookResp> respList = CopyUtil.copyList(ebookList, EbookResp.class);

        return respList;
    }
}
