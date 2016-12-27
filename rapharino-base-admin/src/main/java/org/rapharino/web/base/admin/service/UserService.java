package org.rapharino.web.base.admin.service;

import org.rapharino.web.base.admin.model.User;

import java.util.List;

/**
 * Created by Rapharino on 2016/12/25.
 */
public interface UserService {

    User find(String name);
    // 查找角色
    List<String> findRoles(String name);
    // 查找权限
    List<String> findPermissions(String name);
}
