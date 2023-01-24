package com.cslee.wiki.controller;


import com.cslee.wiki.req.EbookQueryReq;
import com.cslee.wiki.req.EbookSaveReq;
import com.cslee.wiki.resp.CommonResp;
import com.cslee.wiki.resp.EbookQueryResp;
import com.cslee.wiki.resp.PageResp;
import com.cslee.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookQueryReq ebookReq) {
        CommonResp<PageResp<EbookQueryResp> > resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(ebookReq);
        resp.setSuccess(true);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq ebookReq) {
        CommonResp resp = new CommonResp<>();
        ebookService.save(ebookReq);
        return resp;
    }


}
