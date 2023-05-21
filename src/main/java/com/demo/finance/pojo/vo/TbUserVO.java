package com.demo.finance.pojo.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class TbUserVO {

    @ExcelIgnore
    private Integer id;
    @ExcelProperty(value = "姓名", index = 1)
    private String userName;
    @ExcelProperty(value = "用户名", index = 0)
    private String loginName;
    @ExcelIgnore
    private String password;
    @ExcelIgnore
    private Integer type;
    @ExcelProperty(value = "身份证号", index = 3)
    private String idNo;
    @ExcelProperty(value = "手机号", index = 2)
    private String phoneNo;
}
