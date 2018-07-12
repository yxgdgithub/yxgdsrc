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

package com.mj.mmanage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.entity.Example;

import com.github.pagehelper.PageHelper;
import com.mj.mmanage.core.AppException;
import com.mj.mmanage.mapper.GdBookMapper;
import com.mj.mmanage.mapper.GdBookRescourceMapper;
import com.mj.mmanage.model.GdBook;
import com.mj.mmanage.model.GdBookRescource;
import com.mj.mmanage.util.ConstantErr;
import com.mj.mmanage.util.RetMessageVo;
import com.mj.mmanage.util.StringUtil;

/**
 * <p> Description:图书维护的服务类 </p>
 * <p> Copyright: Copyright (c) 2017 </p>
 * <p> Create Date: 2017-12-26</p>
 * <p> Company: YUSYS </p> 
 *@author:YX-LiAnDong
 *@version:GdBookService,v1.0 YX-LiAnDong
 */
@Service
public class GdBookService {
	
	@Autowired
    private GdBookMapper gdBookMapper;

	@Autowired
    private GdBookRescourceMapper gdBookRescourceMapper;
	/**
	 * 图书列表查询
	 *@param gdBook 查询条件
	 *@return 图书列表
	 */
    public void getAll(GdBook gdBook,RetMessageVo retMessageVo) {
    	Example example = new Example(GdBook.class);
    	
    	if(!StringUtil.isEmpty(gdBook.getBookName())){
    		example.createCriteria().andLike("bookName","%"+gdBook.getBookName()+"%");
    	} 
    	
    	if(!StringUtil.isEmpty(gdBook.getBookAuthor())){
    		example.createCriteria().andLike("bookAuthor","%"+gdBook.getBookAuthor()+"%");
    	}
    	
    	int count = gdBookMapper.selectCountByExample(example);
        if (gdBook.getPage() != null && gdBook.getRows() != null) {
            PageHelper.startPage(gdBook.getPage(), gdBook.getRows());
        }
        example.setOrderByClause("createTime DESC");
        List<GdBook> list = gdBookMapper.selectByExample(example);
        retMessageVo.setCount(count);
        retMessageVo.setContent(list);
    }
    
    /**
     * 根据ID查询图书详情
     *@param id
     *@return
     *@throw:
     */
    public GdBook getById(Integer id) {
        return gdBookMapper.selectByPrimaryKey(id);
    }
    
    /**
     * 新增或修改图书信息
     *@param gdBook 图书信息
     *@throw:
     */
    public Integer saveOrUpdate(GdBook gdBook) {
	    if (gdBook.getBookId() != null) { 
	    	gdBookMapper.updateByPrimaryKey(gdBook);
	     } else {
	    	 gdBookMapper.insert(gdBook);
	     }
	    return gdBook.getBookId();
	 }
    /**
     * 根据图书id修改图书说明
     *@param summary 图书说明
     *@param bootId  图书编号
     *@throw:
     */
    public void summaryModify(String summary , Integer bookId) {
    	if(bookId != null){
    		new AppException(ConstantErr.errorMap.get("IMBK002"));
    	}
    	GdBook gdBook = new GdBook();
    	gdBook.setBookSummary(summary);
    	Example example = new Example(GdBookRescource.class);
    	example.createCriteria().andEqualTo("bookId",bookId);
    	gdBookMapper.updateByExampleSelective(gdBook, example);
	    
	 }
    
     
    /**
     * 现在图书上传文件信息
     *@param gdBookRescource 图书对象   主要用图书编号和文件类型查询
     *@return 图书文件列表 
     */
    public Integer addFile(GdBookRescource gdBookRescource){
    	//记录上传文件
    	 gdBookRescourceMapper.insert(gdBookRescource);
    	//查询图书信息
    	 GdBook gdBook = gdBookMapper.selectByPrimaryKey(gdBookRescource.getBookId()); 
    	//更新图书章节数
    	 int chapterSum = gdBook.getChapterSum()==null? 0:gdBook.getChapterSum();
     	 gdBook.setChapterSum(chapterSum + 1);
    	 Example example1 = new Example(GdBookRescource.class);
     	 example1.createCriteria().andEqualTo("bookId",gdBookRescource.getBookId());
     	 gdBookMapper.updateByExampleSelective(gdBook, example1);
     	 
    	 return gdBookRescource.getFileId();
    }
    
    /**
     * 根据图书编号和类型查询图书信息
     *@param gdBookRescource 图书对象   主要用图书编号和文件类型查询
     *@return 图书文件列表 
     */
    public List<GdBookRescource> getFileList(Integer bookId, String fileType){
    	Example example = new Example(GdBookRescource.class);
    	example.createCriteria().andEqualTo("bookId",bookId).andEqualTo("fileType",fileType);
    	example.setOrderByClause("bookChapter");
    	return gdBookRescourceMapper.selectByExample(example);
    }
    
    
    /**
     * 删除图书信息
     *@param bookId
     *@throw:
     */
    public void deleteBook(Integer bookId) {
    	//删除图书信息
    	gdBookMapper.deleteByPrimaryKey(bookId);
    	//删除文件信息 先更新文件状态
    	GdBookRescource gdBookRescource = new GdBookRescource();
    	//更新后状态
    	Example example = new Example(GdBookRescource.class);
    	example.createCriteria().andEqualTo("bookId",bookId);
    	gdBookRescourceMapper.deleteByExample(example);
    
    }
    
    /**
     * 删除图书信息
     *@param bookId
     *@throw:
     */
    public String deleteFile(Integer bookId,Integer fileId) {
    	String filePathName = "";
    	GdBook gdBook = gdBookMapper.selectByPrimaryKey(bookId);
    	
        //批量删除
    	if(StringUtil.isEmpty(fileId)){
    		Example example = new Example(GdBookRescource.class);
        	example.createCriteria().andEqualTo("bookId",bookId);
        	gdBookRescourceMapper.deleteByExample(example);
        	gdBook.setChapterSum(0);
        	
    	}else{
    		GdBookRescource gdBookRescource = gdBookRescourceMapper.selectByPrimaryKey(fileId);
        	gdBookRescourceMapper.deleteByPrimaryKey(fileId);
        	int chapterSum = gdBook.getChapterSum()==null? 0:gdBook.getChapterSum();
        	gdBook.setChapterSum(chapterSum -1);
        	filePathName = gdBookRescource.getFilePath()+"/"+gdBookRescource.getFileName();
    	}
     
    	//更新图书章节数
    	Example example1 = new Example(GdBookRescource.class);
    	example1.createCriteria().andEqualTo("bookId",bookId);
    	gdBookMapper.updateByExampleSelective(gdBook, example1);
    	
    	return filePathName;
    }
}
