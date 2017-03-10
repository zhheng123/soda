package com.soda.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soda.bean.Log;
import com.soda.dao.LogMapper;
import com.soda.services.LogService;
@Service
public class LogServiceImpl implements LogService {
	
	@Autowired
	LogMapper logMapper;

	public void add(Log c) {
		logMapper.insertSelective(c);
	}
	

}
