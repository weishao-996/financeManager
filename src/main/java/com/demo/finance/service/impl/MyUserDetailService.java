package com.demo.finance.service.impl;

import com.demo.finance.dao.TbUserMapper;
import com.demo.finance.pojo.TbUser;
import com.demo.finance.utils.UserContextUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

import static com.demo.finance.enums.userRole.ADMIN;
import static com.demo.finance.enums.userRole.COMMON;

/**
 * @file: MyUserDetailService
 * @version: 1.0.0
 * @Description:
 * 用户详情服务类
 * MyUserDetailService实现了UserDetailsService接口，用于根据用户名加载用户详情信息。
 * @Author: XU WEI
 * @Date: 2023-5-10
 */
@Service("userDetailService")
public class MyUserDetailService implements UserDetailsService {

    @Resource
    private TbUserMapper tbUserMapper;
    /**
     * 根据用户名加载用户详情信息
     *
     * @param username 用户名
     * @return UserDetails 用户详情对象
     * @throws UsernameNotFoundException 如果用户名不存在，则抛出UsernameNotFoundException异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TbUser tbUser = tbUserMapper.selectOneByUsername(username);
        List<GrantedAuthority> auths=null;
        if (tbUser==null){
            throw new UsernameNotFoundException("用户名不存在！");
        }else {
            UserContextUtil.set(tbUser);//将用户信息存入环境变量中
            if (tbUser.getType().equals(ADMIN.getCode())){
                auths = AuthorityUtils.commaSeparatedStringToAuthorityList(ADMIN.getName());
            }else {
                auths = AuthorityUtils.commaSeparatedStringToAuthorityList(COMMON.getName());
            }
        }
        return new User(tbUser.getLoginName(),new BCryptPasswordEncoder().encode(tbUser.getPassword()),auths);
    }
}
