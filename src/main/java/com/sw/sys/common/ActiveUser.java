package com.sw.sys.common;

import com.sw.sys.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @description:
 * @author: sw
 * @time: 2020/2/10 10:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveUser {
    private User user;
    private List<String> roles;
    private List<String> permissions;
}
