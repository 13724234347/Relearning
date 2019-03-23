package Introduction;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Introduction_shiro {
//    private static final transient Logger log = LoggerFactory.getLogger(Introduction_shiro.class);
//
//
//    public static void main(String[] args) {
//
//        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro_filter.ini");
//        SecurityManager securityManager = factory.getInstance();
//
//        SecurityUtils.setSecurityManager(securityManager);
//
//        //获取当前用户.
//        Subject currentUser = SecurityUtils.getSubject();
//
//        Session session = currentUser.getSession();
//        session.setAttribute("someKey", "aValue");
//        String value = (String) session.getAttribute("someKey");
//        if (value.equals("aValue")) {
//            log.info("检索到正确的值！ [" + value + "]");
//        }
//
//        //判断当前用户是否经过了验证.
//        if (!currentUser.isAuthenticated()) {
//            //使用 UsernamePasswordToken 来封装用户名和密码.
//            UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
//            token.setRememberMe(true);
//            try {
//                //执行认证操作.
//                currentUser.login(token);
//            } catch (UnknownAccountException uae) {
//                log.info("没有用户名为" + token.getPrincipal() + "的用户");
//            } catch (IncorrectCredentialsException ice) {
//                log.info("帐户密码 " + token.getPrincipal() + "是不正确的！");
//            } catch (LockedAccountException lae) {
//                log.info("用户名的帐户 " + token.getPrincipal() + " 被锁定。 " +
//                        "请与管理员联系以将其解锁。");
//            } catch (AuthenticationException ae) {
//            }
//        }
//
//        log.info("用户 [" + currentUser.getPrincipal() + "] 已成功登录。");
//        //检查用户是否有某个权限.
//        if (currentUser.hasRole("schwartz")) {
//            log.info("该用户正常拥有schwartz权限");
//        } else {
//            log.info("Hello, mere mortal.");
//        }
//
//        //检查用户是否可以具体进行的某一个操作.
//        //配置时使用的是如下字符串: schwartz = lightsaber:* 这表示当前用户可以对lightsaber实体进行任何操作.
//        if (currentUser.isPermitted("lightsaber:weild")) {
//            log.info("你可以正常的操作weild");
//        } else {
//            log.info("对不起！该用户所拥有的lightsaber权限没有weild操作.");
//        }
//
//        //检查用户是否可以具体进行的某一个操作.
//        //配置时使用的是如下字符串: goodguy = winnebago:drive:eagle5 这表示当前用户可以对winnebage实体的lightsaber实例做drive操作.
//        if (currentUser.isPermitted("winnebago:drive:eagle5")) {
//            log.info("该用户正常可以对winnebage实体的lightsaber实例做drive操作");
//        } else {
//            log.info("该用户所拥有的winnebago实体的eagle5实例被限制做drive操作");
//        }
//        //登出
//        currentUser.logout();
//        System.exit(0);
//    }
}
