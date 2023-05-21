package com.demo.finance.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author XUWEI
 * @since 2023-03-11
 */
@Getter
@Setter
@AllArgsConstructor
public class TbFinanceInfoPageVO {


    private Long total;
    private Long pageCount;
    private List<TbFinanceInfoVO> record;


}
