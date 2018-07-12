/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.mj.mmanage.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.mj.mmanage.core.AppException;
import com.mj.mmanage.core.BaseController;
import com.mj.mmanage.model.GdBook;
import com.mj.mmanage.model.GdBookRescource;
import com.mj.mmanage.model.UserInfo;
import com.mj.mmanage.service.GdBookService;
import com.mj.mmanage.util.ConstantErr;
import com.mj.mmanage.util.Constants;
import com.mj.mmanage.util.DateUtil;
import com.mj.mmanage.util.RetMessageVo;
import com.mj.mmanage.util.StringUtil;
import com.mj.mmanage.util.UploadFile;

/**
 * <p> Description:图书维护，包括：新增、修改、删除 </p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Create Date: 2017-12-26</p>
 * <p> Company: YUSYS </p> 
 *@author:YX-LiAnDong
 *@version:GdBookController,v1.0 YX-LiAnDong
 */
@RestController
@RequestMapping("/book")
@SessionAttributes(Constants.SESSION_USER)
public class GdBookController  extends BaseController{

 

    @Autowired
    private GdBookService gdBookService;
    
 
    /**
     * 图书列表查询
     *@param userInfo
     *@return
     *@throw:
     */
    @RequestMapping
    public RetMessageVo  getAll(GdBook gdBook) {
    	RetMessageVo retMessageVo= new RetMessageVo();
        gdBookService.getAll(gdBook,retMessageVo);
        return retMessageVo;
    }

    /**
     * 查询图书详情
     *@param bookId
     *@return
     *@throw:
     */
    @RequestMapping(value = "/{bookId}")
    public GdBook getBookDetail(@PathVariable("bookId") Integer bookId) {
        return gdBookService.getById(bookId);
    }
    
    /**
     * 新增或修改图书基本信息 根据图书编号存在与否判断
     *@param gdBook 图书基本信息对象
     *@return 公共返回信息
     *@throw:  @RequestParam("file") MultipartFile file,
     */
    @RequestMapping(value = "/addOrModify")
    public Integer addOrModify(GdBook gdBook,@ModelAttribute(Constants.SESSION_USER) UserInfo user )throws AppException {
    	//上传图片
    	String fileName = gdBook.getBookImg().getOriginalFilename();
    	//获取年份
		String year = DateUtil.now(Constants.SHORT_DATE_FORMAT_STR);
    	if(fileName.lastIndexOf(".")>0){
    		//文件后缀 如.jpg
    		fileName = UploadFile.updateFileName(fileName);
    		try {
				UploadFile.SaveFileFromInputStream( gdBook.getBookImg().getInputStream(), Constants.WEB_PROJECT_PATH+Constants.BOOK_IMG_PATH+year, fileName);
			} catch (IOException e) {
				//上传文件失败
	    		new AppException("IMBK001");
			}
    	}else if(StringUtil.isEmpty(gdBook.getBookId())){
    		//上传文件格式异常
    		new AppException("IMBK001");
    	}
    
    	String userId = user.getId();
    	//拼装固定值
    	if (gdBook.getBookId() != null) {
    		GdBook gdBookDB = gdBookService.getById(gdBook.getBookId());
    		gdBookDB.setBookAuthor(gdBook.getBookAuthor());
    		gdBookDB.setBookName(gdBook.getBookName());
    		gdBookDB.setBookTag(gdBook.getBookTag());
    		//修改
    		gdBook = gdBookDB;
    		gdBook.setUpdateTime(new Date());
        	gdBook.setUpdateUserId(userId);
         } else {
        	 //新增 
        	 gdBook.setCreateTime(new Date());
         	 gdBook.setCreateUserId(userId);
         }
    	gdBook.setBookSltPath(Constants.BOOK_IMG_PATH+year+"/"+ fileName);
    	return gdBookService.saveOrUpdate(gdBook);
    }

    /**
     * 根据图书id修改图书说明
     *@param summary 图书说明
     *@param bootId  图书编号
     *@return 公共返回信息
     *@throw:
     */
    @RequestMapping(value = "/summaryModify")
    public  void summaryModify(@RequestParam("bookSummary") String bookSummary ,@RequestParam("bookId") Integer bookId) throws AppException{
    	gdBookService.summaryModify(bookSummary,bookId);
    }

    /**
     * 图书文件列表查询
     *@param bookId 图书编号
     *@param type 01：文本 02：是视频
     *@return
     *@throw:
     */
    @RequestMapping(value = "/file/{bookId}/{type}")
    public List<GdBookRescource> getFileList(@PathVariable("bookId") Integer bookId,@PathVariable("type") String type) {
    	GdBookRescource gdBookRescource = new GdBookRescource();
    	gdBookRescource.setBookId(bookId);
        gdBookRescource.setFileType(type);
    	return  gdBookService.getFileList(bookId, type);
    }
    

    /**
     * 根据图书编号删除图书
     *@param bookId 图书编号
     */
    @RequestMapping(value = "/remove/{bookId}")
    public void bookRemove(@PathVariable("bookId") Integer bookId) {
    	String path = "";
    	//删除图书信息和删除数据库文件记录
    	gdBookService.deleteBook(bookId);
    	path = Constants.WEB_PROJECT_PATH+Constants.BOOK_TXT_PATH+bookId+"/";
		//删除文本文件
		UploadFile.deleteFile(path);
		//删除语音文件
		path = Constants.WEB_PROJECT_PATH+Constants.BOOK_VOICE_PATH+bookId+"/";
		UploadFile.deleteFile(path);
		 
    	
    }
    
    /**
     * 删除单个图书上传的文件
     *@param bookId 图书编号
     */
    @RequestMapping(value = "/file/remove")
    public String fileRemove(@RequestParam("fileId") Integer fileId, @RequestParam("bookId") Integer bookId,@RequestParam("fileType") String fileType) {
    	//删除数据库文件数据
    	String filePathName =gdBookService.deleteFile(bookId, fileId);
    	
    	//删除文件或文件夹
    	String path = "";
    	if("01".equals(fileType)){
    		path = Constants.WEB_PROJECT_PATH+Constants.BOOK_TXT_PATH;
    	}else if("02".equals(fileType)){
    		path = Constants.WEB_PROJECT_PATH+ Constants.BOOK_VOICE_PATH;
    	}
    	if(!StringUtil.isEmpty(fileId)){
    		UploadFile.deleteFile(filePathName);
    	}else{
    		UploadFile.deleteFile(path+bookId+"/");
    	}
    	
    	return SUCCESS;
    }
    /**
     * 图书文件上传
     *@param file 文件
     */
    @RequestMapping(value = "/file/upload")
    public Integer fileUpload(@RequestParam("file") MultipartFile file , @RequestParam("bookId") Integer bookId,@RequestParam("fileType") String fileType,
    		@RequestParam("bookChapter") String bookChapter,@ModelAttribute(Constants.SESSION_USER) UserInfo user) {
    	String path = "";
    	if("01".equals(fileType)){
    		path =Constants.BOOK_TXT_PATH;
    	}else if("02".equals(fileType)){
    		path = Constants.BOOK_VOICE_PATH;
    	}else{
    		new AppException(ConstantErr.errorMap.get("IMBK002"));
    	}
    	String fileName = "";
		try{
	    	fileName = UploadFile.updateFileName(file.getOriginalFilename());
			UploadFile.SaveFileFromInputStream( file.getInputStream(),  Constants.WEB_PROJECT_PATH+path+bookId, bookChapter+fileName);
		}catch(Exception e){
			new AppException(ConstantErr.errorMap.get("IMBK002"));
		}
		//组装文件信息
		GdBookRescource gdBookRescource = new GdBookRescource();
    	gdBookRescource.setBookChapter(bookChapter);
    	gdBookRescource.setBookId(bookId);
    	gdBookRescource.setCreateTime(new Date());
    	gdBookRescource.setCreateUserId(user.getId());
    	gdBookRescource.setFileName(bookChapter+fileName);
    	gdBookRescource.setFilePath(path+bookId+'/');
    	gdBookRescource.setFileState("0");
    	gdBookRescource.setFileType(fileType);
    	gdBookRescource.setFileSize(file.getSize());
    	return gdBookService.addFile(gdBookRescource);
    }
    
    

    /**
     * 图书标签智能查询
     *@param tagName 标签名称 用户模糊匹配
     *@return 
     *@throw:
     */
    @RequestMapping(value = "/file/tag")
    public RetMessageVo getFileTag(@RequestParam("tagName") String tagName) {
    	 RetMessageVo retMessageVo = new RetMessageVo();
         return retMessageVo;
    }

    
   
    
}
