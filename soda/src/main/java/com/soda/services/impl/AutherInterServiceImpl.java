package com.soda.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soda.bean.Auther;
import com.soda.dao.AutherMapper;
import com.soda.services.AutherInterService;
@Service
public class AutherInterServiceImpl implements AutherInterService {
	
	@Autowired
	AutherMapper  auMapper;
	
	public List<Auther> findAll() {
		
		return auMapper.findAll();
	}
}
