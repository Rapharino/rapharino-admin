package org.rapharino.web.base.admin.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.rapharino.web.base.admin.model.User;
import org.rapharino.web.base.admin.service.UserService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Rapharino on 2016/12/25.
 *
 *  权限认证
 */
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;
    /**
     * 认证回调函数,登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

        User user = userService.find(token.getUsername());

        if (user == null) {
            throw new UnknownAccountException();// 没找到帐号
        }

        if (Boolean.TRUE.equals(user.getLocked())) {
            throw new LockedAccountException(); // 帐号锁定
        }
        // 盐值 加密
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getUserpwd(),
                ByteSource.Util.bytes(user.getSalt()),
                getName()
        );
        return authenticationInfo;
    }

    /**
     * 授权查询回调函数, 进行鉴权 [但缓存中无用户的授权信息] 时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<String> rolestr = userService.findRoles(user.getUsername());
        info.addRoles(rolestr);
        List<String> permissionstr = userService.findPermissions(user.getUsername());
        info.addStringPermissions(permissionstr);
        return info;
    }


    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}