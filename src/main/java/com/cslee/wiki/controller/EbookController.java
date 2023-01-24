package com.cslee.wiki.controller;


import com.cslee.wiki.req.EbookReq;
import com.cslee.wiki.resp.CommonResp;
import com.cslee.wiki.resp.EbookResp;
import com.cslee.wiki.resp.PageResp;
import com.cslee.wiki.service.EbookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookReq ebookReq) {
        CommonResp<PageResp<EbookResp> > resp = new CommonResp<>();
        PageResp<EbookResp> list = ebookService.list(ebookReq);
        resp.setSuccess(true);
        resp.setContent(list);
        return resp;
    }
}
