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

import org.springframework.beans.BeanUtils;
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
     * 根据ID查询图书详情
     *@param id
     *@return
     *@throw:
     */
    public GdBook getById(Integer id) {
        return gdBookMapper.selectByPrimaryKey(id);
    }
    
}
