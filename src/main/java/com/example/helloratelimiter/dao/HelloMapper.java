package com.example.helloratelimiter.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface HelloMapper {
    @Update("UPDATE pod_rate_limit " +
            "SET token = token - 1, last_updated = NOW() " +
            "WHERE id = 1;")
    public void update();
    @Select("SELECT token FROM pod_rate_limit WHERE id = 1")
    public Integer token();
}
