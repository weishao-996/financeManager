package com.demo.finance.pojo.qo;


import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class PageQO {

    @NotNull(message = "页面大小不能为空！")
    public Integer pageSize;
    @NotNull(message = "当前页不能为空！")
    public Integer currentPage;

}
