package com.demo.finance.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.demo.finance.pojo.TbFinanceInfo;
import com.demo.finance.pojo.TbUser;
import com.demo.finance.pojo.qo.TbFinanceInfoInsertQO;
import com.demo.finance.pojo.qo.TbFinanceInfoPageQO;
import com.demo.finance.pojo.vo.TbFinanceCompareVO;
import com.demo.finance.pojo.vo.TbFinanceInfoVO;
import com.demo.finance.utils.UserContextUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TbFinanceInfoServiceImplTest {

    @Resource
    private  TbFinanceInfoServiceImpl tbFinanceInfoServiceImplUnderTest;

    @BeforeEach
    void setUp() {
        System.out.println("----初始化登录信息----");
        MockitoAnnotations.openMocks(this);
        TbUser tbUser = new TbUser();
        tbUser.setId(1);
        tbUser.setLoginName("admin");
        tbUser.setType(1);
        tbUser.setUserName("管理员");
        tbUser.setPassword("123456");
        UserContextUtil.set(tbUser);
    }

    @Test
    void testSelectPage() {
        // Setup
        final Page<TbFinanceInfo> page = new Page<>(0L, 10L, 0L, false);
        final TbFinanceInfoPageQO tbFinanceInfoPageQO = new TbFinanceInfoPageQO();
        tbFinanceInfoPageQO.setClaimerUserId(32);
        tbFinanceInfoPageQO.setStartTime(null);
        tbFinanceInfoPageQO.setEndTime(null);
        // Run the test
        Page<TbFinanceInfoVO> result = tbFinanceInfoServiceImplUnderTest.selectPage(page, tbFinanceInfoPageQO);
        List<TbFinanceInfoVO> tbFinanceInfoList=result.getRecords();
        tbFinanceInfoList.forEach(System.out::println);
    }

    @Test
    void testGetBalanceBySumAndUpdateTime() {
        // Setup
        final TbFinanceInfoInsertQO tbFinanceInfoInsertQO = new TbFinanceInfoInsertQO();
        tbFinanceInfoInsertQO.setClaimerUserId(1);
        tbFinanceInfoInsertQO.setReason("reason");
        tbFinanceInfoInsertQO.setSum("20");
        tbFinanceInfoInsertQO.setType(1);
        tbFinanceInfoInsertQO.setUpdateTime(LocalDateTime.of(2023, 5, 5, 0, 0, 0));

        // Run the test
        final String result = tbFinanceInfoServiceImplUnderTest.getBalanceBySumAndUpdateTime(tbFinanceInfoInsertQO);

        // Verify the results
        assertThat(result).isEqualTo("105");
    }


    @Test
    void testSelectHistory() {
        // Setup
        // Run the test
        final List<TbFinanceInfo> result = tbFinanceInfoServiceImplUnderTest.selectHistory(1);
        result.forEach(System.out::println);

    }

    @Test
    void testSelectRange() {
        // Setup
        // Run the test
        final TbFinanceCompareVO result = tbFinanceInfoServiceImplUnderTest.selectRange(1,
                LocalDateTime.of(2020, 1, 1, 0, 0, 0), LocalDateTime.of(2024, 1, 1, 0, 0, 0));

        // Verify the results
        System.out.println(result);
        assertThat(result.getIncome()).isEqualTo("170");
        assertThat(result.getExpenses()).isEqualTo("45");
    }


    @Test
    void testTbFinanceInfoAddress() {
        // Setup
        List<TbFinanceInfo> tbFinanceInfoList=tbFinanceInfoServiceImplUnderTest.selectHistory(32);
        // Run the test
        final List<TbFinanceInfo> result = tbFinanceInfoServiceImplUnderTest.tbFinanceInfoAddress(tbFinanceInfoList);
        // Verify the results
        result.forEach(System.out::println);
    }

    @Test
    void testGetBalanceList() {
        // Setup
        List<TbFinanceInfo> tbFinanceInfoList=tbFinanceInfoServiceImplUnderTest.selectHistory(32);
        final List<TbFinanceInfo> result = tbFinanceInfoServiceImplUnderTest.tbFinanceInfoAddress(tbFinanceInfoList);

        // Run the test
        final List<String> result1 = tbFinanceInfoServiceImplUnderTest.getBalanceList(result);

        // Verify the results
        result1.forEach(System.out::println);
    }

    @Test
    void testGetUpdateTimeList() {
        // Setup
        // Setup
        List<TbFinanceInfo> tbFinanceInfoList=tbFinanceInfoServiceImplUnderTest.selectHistory(32);
        final List<TbFinanceInfo> result = tbFinanceInfoServiceImplUnderTest.tbFinanceInfoAddress(tbFinanceInfoList);
        // Run the test
        final List<String> result1 = tbFinanceInfoServiceImplUnderTest.getUpdateTimeList(result);
        // Verify the results
        result1.forEach(System.out::println);
    }


    @Test
    void testConvertInsertQOtoPOJO() {
        // Setup
        final TbFinanceInfoInsertQO tbFinanceInfoInsertQO = new TbFinanceInfoInsertQO();
        tbFinanceInfoInsertQO.setClaimerUserId(0);
        tbFinanceInfoInsertQO.setReason("reason");
        tbFinanceInfoInsertQO.setSum("20");
        tbFinanceInfoInsertQO.setType(1);
        tbFinanceInfoInsertQO.setUpdateTime(LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        // Run the test
        final TbFinanceInfo result = tbFinanceInfoServiceImplUnderTest.convertInsertQOtoPOJO(tbFinanceInfoInsertQO);

        // Verify the results
        System.out.println(result);
    }

}
