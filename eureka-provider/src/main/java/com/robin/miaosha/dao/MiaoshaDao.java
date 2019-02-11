package com.robin.miaosha.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.robin.miaosha.bean.Order;

/**
 * @Auther: Robin
 */
@Mapper
public interface MiaoshaDao {
    @Insert("insert into seckill values(#{uid},#{sordernum},#{gid},#{gnum},#{totalprice},#{state})")
    Integer saveMiaoshaOrder(MiaoshaOrder order);
}
