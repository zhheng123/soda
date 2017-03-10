package com.soda.services.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.soda.bean.News;
import com.soda.dao.NewsMapper;
import com.soda.services.iface.NewsService;
@Service
public class NewServiceImpl implements NewsService {
	
	@Resource
	NewsMapper newMapper;

	public News findNewById(Integer newId) {
		return newMapper.selectByPrimaryKey(newId);
	}
}
