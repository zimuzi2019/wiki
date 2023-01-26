package com.cslee.wiki.service;

import com.cslee.wiki.domain.Category;
import com.cslee.wiki.domain.CategoryExample;
import com.cslee.wiki.mapper.CategoryMapper;
import com.cslee.wiki.req.CategoryQueryReq;
import com.cslee.wiki.req.CategorySaveReq;
import com.cslee.wiki.resp.CategoryQueryResp;
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
public class CategoryService {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> list(CategoryQueryReq categoryReq){
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        PageHelper.startPage(categoryReq.getPage(), categoryReq.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("总行数：{}", pageInfo.getTotal());
        LOG.info("总页数：{}", pageInfo.getPages());

        //List<CategoryResp> respList = new ArrayList<>();
        //for (Category category : categoryList) {
        //    //CategoryResp categoryResp = new CategoryResp();
        //    //BeanUtils.copyProperties(category, categoryResp);
        //    //对象复制
        //    CategoryResp categoryResp = CopyUtil.copy(category, CategoryResp.class);
        //    respList.add(categoryResp);
        //}

        //列表复制
        List<CategoryQueryResp> respList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);

        return pageResp;
    }



    /**
     * 保存
     */
    public void save(CategorySaveReq categorySaveReq) {
        Category category = CopyUtil.copy(categorySaveReq, Category.class);

        if (ObjectUtils.isEmpty(categorySaveReq.getId())) {
            // 新增
            category.setId(snowFlake.nextId());
            categoryMapper.insertSelective(category);
        } else {
            // 更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }
}
