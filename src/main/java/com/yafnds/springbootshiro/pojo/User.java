package com.yafnds.springbootshiro.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 包名称：com.yafnds.springbootshiro.pojo
 * 类名称：User
 * 类描述：用户表
 * 创建人：@author y19991
 * 创建时间：2021/4/25 17:56
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    /** 主键 */
    private int userId;

    /** 用户名 */
    private String name;

    /** 密码 */
    private String pwd;

    /** 权限 */
    private String perms;

    /** 盐值 */
    private String salt;

}
