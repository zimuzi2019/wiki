package com.cslee.wiki.service;

import com.cslee.wiki.domain.Doc;
import com.cslee.wiki.domain.DocExample;
import com.cslee.wiki.mapper.DocMapper;
import com.cslee.wiki.req.DocQueryReq;
import com.cslee.wiki.req.DocSaveReq;
import com.cslee.wiki.resp.DocQueryResp;
import com.cslee.wiki.resp.PageResp;
import com.cslee.wiki.util.CopyUtil;
import com.cslee.wiki.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocService {
    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    private DocMapper docMapper;

    @Resource
    private SnowFlake snowFlake;

    public List<DocQueryResp> all(){
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);

        //列表复制
        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);

        return respList;
    }


    public PageResp<DocQueryResp> list(DocQueryReq docReq){
        DocExample docExample = new DocExample();
        docExample.setOrderByClause("sort asc");
        DocExample.Criteria criteria = docExample.createCriteria();
        PageHelper.startPage(docReq.getPage(), docReq.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        //List<DocResp> respList = new ArrayList<>();
        //for (Doc doc : docList) {
        //    //DocResp docResp = new DocResp();
        //    //BeanUtils.copyProperties(doc, docResp);
        //    //对象复制
        //    DocResp docResp = CopyUtil.copy(doc, DocResp.class);
        //    respList.add(docResp);
        //}

        //列表复制
        List<DocQueryResp> respList = CopyUtil.copyList(docList, DocQueryResp.class);

        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }



    /**
     * 保存
     */
    public void save(DocSaveReq docSaveReq) {
        Doc doc = CopyUtil.copy(docSaveReq, Doc.class);

        if (ObjectUtils.isEmpty(docSaveReq.getId())) {
            // 新增
            doc.setId(snowFlake.nextId());
            docMapper.insertSelective(doc);
        } else {
            // 更新
            docMapper.updateByPrimaryKey(doc);
        }
    }

    public void delete(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }

    public void delete(List<String> ids) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        docMapper.deleteByExample(docExample);
    }
}
