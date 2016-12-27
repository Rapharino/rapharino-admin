package org.rapharino.web.base.admin.service.impl;

import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.rapharino.web.base.admin.dao.UserMapper;
import org.rapharino.web.base.admin.model.User;
import org.rapharino.web.base.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Rapharino on 2016/12/25.
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User find(String name) {
        if (StringUtils.isNotBlank(name))
            return userMapper.selectByName(name);
        return null;
    }

    @Override
    public List<String> findRoles(String name) {
        return null;
    }

    @Override
    public List<String> findPermissions(String name) {
        return null;
    }
}
