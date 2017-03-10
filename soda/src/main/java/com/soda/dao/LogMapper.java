package com.soda.dao;

import org.springframework.stereotype.Repository;

import com.soda.bean.Log;
@Repository
public interface LogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Log record);

    int insertSelective(Log record);

    Log selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);
}