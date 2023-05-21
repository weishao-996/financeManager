package com.demo.finance.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class TbUserPageVO {

    private Long total;
    private Long pageCount;
    private List<TbUserVO> record;
}
