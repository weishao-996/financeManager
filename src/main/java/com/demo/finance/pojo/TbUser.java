package com.demo.finance.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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
@TableName("tb_user")
@ToString
public class TbUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户 id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 登录名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户名
     */
    @TableField("login_name")
    private String loginName;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 1 为管理员用户，2 为普通用户
     */
    @TableField("type")
    private Integer type;

    /**
     * 身份证号
     */
    @TableField("id_no")
    private String idNo;

    /**
     * 手机号
     */
    @TableField("phone_no")
    private String phoneNo;


}
