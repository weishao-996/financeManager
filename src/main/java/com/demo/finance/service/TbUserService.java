package com.demo.finance.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.finance.pojo.TbUser;
import com.demo.finance.pojo.qo.UserPageQO;
import com.demo.finance.pojo.vo.TbUserVO;
import com.demo.finance.pojo.vo.UserSelectVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author XUWEI
 * @since 2023-03-11
 */
public interface TbUserService extends IService<TbUser> {


      Boolean checkUserExist(String userName);

    List<UserSelectVO> getUserSelectList();

    Page<TbUserVO> selectPage(Page<TbUserVO> page, UserPageQO userPageQO);

    Integer getUserIdByLoginName(String loginName);

    List<TbUserVO> download();
}
