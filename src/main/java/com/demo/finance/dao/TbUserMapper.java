package com.demo.finance.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.finance.pojo.TbUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.finance.pojo.qo.UserPageQO;
import com.demo.finance.pojo.vo.TbUserVO;
import com.demo.finance.pojo.vo.UserSelectVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author XUWEI
 * @since 2023-03-11
 */
@Mapper
public interface TbUserMapper extends BaseMapper<TbUser> {

    TbUser selectOneByUsername(@Param("username") String username);


    List<UserSelectVO> getUserSelectList();

    Page<TbUserVO> queryPage(Page<TbUserVO> page, @Param("pageQO")UserPageQO userPageQO);

    Integer getUserIdByLoginName(@Param("loginName")String loginName);

    List<TbUserVO> downloadByType(@Param("type")Integer type);

    Integer getAdminId(Integer code);
}
