package com.cslee.wiki.controller;


import com.cslee.wiki.req.CategoryQueryReq;
import com.cslee.wiki.req.CategorySaveReq;
import com.cslee.wiki.resp.CategoryQueryResp;
import com.cslee.wiki.resp.CommonResp;
import com.cslee.wiki.resp.PageResp;
import com.cslee.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq categoryReq) {
        CommonResp<PageResp<CategoryQueryResp> > resp = new CommonResp<>();
        PageResp<CategoryQueryResp> list = categoryService.list(categoryReq);
        resp.setSuccess(true);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq categoryReq) {
        CommonResp resp = new CommonResp<>();
        categoryService.save(categoryReq);
        return resp;
    }

    @DeleteMapping ("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }

}
