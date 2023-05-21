package com.demo.finance.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class TbFinanceHistoryVO {
    List<String> dateList;

    List<String> balance;
}
