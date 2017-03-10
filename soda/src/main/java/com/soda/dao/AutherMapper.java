package com.soda.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.soda.bean.Auther;
@Repository
public interface AutherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Auther record);

    int insertSelective(Auther record);

    Auther selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Auther record);

    int updateByPrimaryKey(Auther record);
    
    List<Auther>findAll();
}