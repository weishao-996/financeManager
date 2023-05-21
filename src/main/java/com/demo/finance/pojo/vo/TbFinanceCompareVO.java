package com.demo.finance.pojo.vo;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TbFinanceCompareVO {

    private String income;

    private String expenses;

    private List<String> dateList;
}
