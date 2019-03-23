package spring_shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

public class MyRealm extends AuthorizingRealm {

    //授权方法
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Object principal = principalCollection.getPrimaryPrincipal();
        System.out.println(principal);
        List<String> roles = new ArrayList<String>();
        roles.add("admin");
        roles.add("user");
        List<String> permission = new ArrayList<String>();
        permission.add("create");
        permission.add("delete");

//        if ("admin".equals(principal)){
//            simpleAuthorizationInfo.addRole("admin");
//            simpleAuthorizationInfo.addStringPermission("add");
//        }
//        if("user".equals(principal)){
//            simpleAuthorizationInfo.addRole("user");
//            simpleAuthorizationInfo.addStringPermission("delete");
//        }

        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    //认证方法
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        System.out.println(authenticationToken.getPrincipal());
        System.out.println(authenticationToken.getCredentials());

        //登录的主要信息: 从数据库中查询的结果,该结果必须和token中携带值一致！
        Object principal =authenticationToken.getPrincipal();
        //认证信息: 从数据库中查询出来的信息.
        String password = "123456";
        String credentials = getPassword(password);//给我们输入的密码加密,里面有个叫盐值加密的字符串

        //设置盐值:
        String source = "tzh";//根据这个来加密
        ByteSource credentialsSalt = new Md5Hash(source);
        //当前Realm的name
        String realmName=getName();
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(principal, credentials,credentialsSalt, realmName);
        return simpleAuthenticationInfo;
    }


    //@PostConstruct/: 相当于 bean 节点的 init-method 配置.
    @PostConstruct
    public void setCredentialMatcher(){
        HashedCredentialsMatcher  credentialsMatcher = new HashedCredentialsMatcher();

        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(1024);

        setCredentialsMatcher(credentialsMatcher);
    }

    public String getPassword(String password)
    {
        String saltSource = "tzh";
        String hashAlgorithmName = "MD5";
        String credentials = password;
        Object salt = new Md5Hash(saltSource);
        int hashIterations = 1024;
        String result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations).toString();
        return result;
    }
}
