package com.tzh.shiro.configure;

import com.tzh.entity.Permission;
import com.tzh.entity.Role;
import com.tzh.entity.User;
import com.tzh.service.Role.RoleService;
import com.tzh.service.login.LoginService;
import com.tzh.service.user.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName： ShiroRealm
 * @Author: tangzihao
 * @CreateDate: 2019/2/28 17:45
 * @Version: 1.0
 */
@DependsOn("shiroRealm")
public class ShiroRealm extends AuthorizingRealm {


    private static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    /**
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @author: tangzihao
     * @methodName： doGetAuthenticationInfo
     * @createDate: 2019/3/8 16:24
     * @param: [authenticationToken]
     * @version: 1.0
     * <p>
     * 认证方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("验证当前Subject时获取到token为：" + token.toString());
        //查出是否有此用户
        String username = token.getUsername();
        User user = loginService.getUserInformation(username);
        String credentials = getPassword(user.getPassword());//给我们输入的密码加密,里面有个叫盐值加密的字符串
        //设置盐值:
        String source = "tzh";//根据这个来加密
        ByteSource credentialsSalt = new Md5Hash(source);
        // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
        return new SimpleAuthenticationInfo(user, credentials, credentialsSalt, getName());
    }

    /**
     * @return org.apache.shiro_filter.authz.AuthorizationInfo
     * @author: tangzihao
     * @methodName： doGetAuthorizationInfo
     * @createDate: 2019/2/28 19:08
     * @param: [principalCollection]
     * @version: 1.0
     * 授权方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        User user = (User) super.getAvailablePrincipal(principalCollection);
        Set<Role> roleSet = userService.getRole(user.getUsername());
        for (Role role : roleSet){
            Set<Permission> permissionSet = roleService.getPermission(role.getRoleName());
            role.setPermissionSet(permissionSet);
        }
        user.setRoleSet(roleSet);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        //将数据存储到session中
        session.setAttribute("user", user);
        return null;
    }

    public static String getPassword(String password) {
        String saltSource = "tzh";
        String hashAlgorithmName = "MD5";
        String credentials = password;
        Object salt = new Md5Hash(saltSource);
        int hashIterations = 1024;
        String result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations).toString();
        return result;
    }
}
