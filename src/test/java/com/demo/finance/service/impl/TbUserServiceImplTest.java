package com.demo.finance.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.finance.pojo.qo.UserPageQO;
import com.demo.finance.pojo.vo.TbUserVO;
import com.demo.finance.pojo.vo.UserSelectVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TbUserServiceImplTest {

    @Resource
    private TbUserServiceImpl tbUserService;



    @Test
    void testCheckUserExist() {
        // Setup
        // Run the test
        final Boolean result = tbUserService.checkUserExist("xuwei");
        // Verify the results
        assertThat(result).isTrue();
    }

    @Test
    void testGetUserSelectList() {
        // Run the test
        final List<UserSelectVO> result = tbUserService.getUserSelectList();
        // Verify the results
        result.forEach(System.out::println);
    }

    @Test
    void testSelectPage() {
        // Setup
        final Page<TbUserVO> page = new Page<>(0L, 10L, 0L, false);
        final UserPageQO userPageQO = new UserPageQO();
        // Run the test
        final Page<TbUserVO> result = tbUserService.selectPage(page, userPageQO);
        // Verify the results
        result.getRecords().forEach(System.out::println);
    }

    @Test
    void testGetUserIdByLoginName() {
        // Run the test
        final Integer result = tbUserService.getUserIdByLoginName("admin");
        // Verify the results
        assertThat(result).isEqualTo(1);
    }

}
