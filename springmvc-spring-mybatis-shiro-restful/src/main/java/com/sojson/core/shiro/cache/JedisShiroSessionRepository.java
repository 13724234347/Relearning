package com.sojson.core.shiro.cache;

import java.io.Serializable;
import java.util.Collection;

import org.apache.shiro.session.Session;

import com.sojson.common.utils.LoggerUtils;
import com.sojson.common.utils.SerializeUtil;
import com.sojson.core.shiro.session.CustomSessionManager;
import com.sojson.core.shiro.session.SessionStatus;
import com.sojson.core.shiro.session.ShiroSessionRepository;
/**
 * Session 绠＄悊
 * @author sojson.com
 *
 */
@SuppressWarnings("unchecked")
public class JedisShiroSessionRepository implements ShiroSessionRepository {
    public static final String REDIS_SHIRO_SESSION = "sojson-shiro-demo-session:";
    //杩欓噷鏈変釜灏廈UG锛屽洜涓篟edis浣跨敤搴忓垪鍖栧悗锛孠ey鍙嶅簭鍒楀寲鍥炴潵鍙戠幇鍓嶉潰鏈変竴娈典贡鐮侊紝瑙ｅ喅鐨勫姙娉曟槸瀛樺偍缂撳瓨涓嶅簭鍒楀寲
    public static final String REDIS_SHIRO_ALL = "*sojson-shiro-demo-session:*";
    private static final int SESSION_VAL_TIME_SPAN = 18000;
    private static final int DB_INDEX = 1;//这个是redis16个数据库

    private JedisManager jedisManager;

    public void saveSession(Session session) {
        if (session == null || session.getId() == null)
            throw new NullPointerException("session is empty");
        try {
            byte[] key = SerializeUtil.serialize(buildRedisSessionKey(session.getId()));
            
            
            //涓嶅瓨鍦ㄦ墠娣诲姞銆�
            if(null == session.getAttribute(CustomSessionManager.SESSION_STATUS)){
            	//Session 韪㈠嚭鑷瓨瀛樺偍銆�
            	SessionStatus sessionStatus = new SessionStatus();
            	session.setAttribute(CustomSessionManager.SESSION_STATUS, sessionStatus);
            }
            
            byte[] value = SerializeUtil.serialize(session);


            /**杩欓噷鏄垜鐘笅鐨勪竴涓弗閲嶉棶棰橈紝浣嗘槸涔熶笉浼氭槸鑷村懡锛�
             * 鎴戣绠椾簡涓嬶紝涔嬪墠涓婇潰涓嶅皬蹇冪粰鎴戝姞浜�0锛屼篃灏辨槸 18000 / 3600 = 5 涓皬鏃�
             * 鍙﹀锛宻ession璁剧疆鐨勬槸30鍒嗛挓鐨勮瘽锛屽苟涓斿姞浜嗕竴涓�(5 * 60)锛屼竴璧风畻涓嬫潵锛宻ession鐨勫け鏁堟椂闂存槸 5:35 鐨勬蹇垫墠浼氬け鏁�
             * 鎴戝師鏉ユ槸瀛樺偍session鐨勬湁鏁堟湡浼氭瘮session鐨勬湁鏁堟湡浼氶暱锛岃�屼笖鏈�缁坰ession鐨勬湁鏁堟湡鏄湪杩欓噷銆怱ESSION_VAL_TIME_SPAN銆戣缃殑銆�
             *
             * 杩欓噷娌℃湁璧般�恠hiro-config.properties銆戦厤缃枃浠讹紝闇�瑕佹敞鎰忕殑鏄�恠pring-shiro.xml銆� 涔熸槸鐩存帴閰嶇疆鐨勫�硷紝娌℃湁璧般�恠hiro-config.properties銆�
             *
             * PS  : 娉ㄦ剰锛� 杩欓噷鎴戜滑閰嶇疆 redis鐨凾TL鍗曚綅鏄锛岃�屻�恠pring-shiro.xml銆戦厤缃殑鏄渶瑕佸姞3涓�0锛堟绉掞級銆�
                long sessionTimeOut = session.getTimeout() / 1000;
                Long expireTime = sessionTimeOut + SESSION_VAL_TIME_SPAN + (5 * 60);
             */


            /*
            鐩存帴浣跨敤 (int) (session.getTimeout() / 1000) 鐨勮瘽锛宻ession澶辨晥鍜宺edis鐨凾TL 鍚屾椂鐢熸晥
             */
            getJedisManager().saveValueByKey(DB_INDEX, key, value, (int) (session.getTimeout() / 1000));
        } catch (Exception e) {
        	LoggerUtils.fmtError(getClass(), e, "save session error锛宨d:[%s]",session.getId());
        }
    }

    public void deleteSession(Serializable id) {
        if (id == null) {
            throw new NullPointerException("session id is empty");
        }
        try {
            getJedisManager().deleteByKey(DB_INDEX,
                    SerializeUtil.serialize(buildRedisSessionKey(id)));
        } catch (Exception e) {
        	LoggerUtils.fmtError(getClass(), e, "鍒犻櫎session鍑虹幇寮傚父锛宨d:[%s]",id);
        }
    }

   
    public Session getSession(Serializable id) {
        if (id == null)
        	 throw new NullPointerException("session id is empty");
        Session session = null;
        try {
            byte[] value = getJedisManager().getValueByKey(DB_INDEX, SerializeUtil
                    .serialize(buildRedisSessionKey(id)));
            session = SerializeUtil.deserialize(value, Session.class);
        } catch (Exception e) {
        	LoggerUtils.fmtError(getClass(), e, "鑾峰彇session寮傚父锛宨d:[%s]",id);
        }
        return session;
    }

    public Collection<Session> getAllSessions() {
    	Collection<Session> sessions = null;
		try {
			sessions = getJedisManager().AllSession(DB_INDEX,REDIS_SHIRO_SESSION);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "鑾峰彇鍏ㄩ儴session寮傚父");
		}
       
        return sessions;
    }

    private String buildRedisSessionKey(Serializable sessionId) {
        return REDIS_SHIRO_SESSION + sessionId;
    }

    public JedisManager getJedisManager() {
        return jedisManager;
    }

    public void setJedisManager(JedisManager jedisManager) {
        this.jedisManager = jedisManager;
    }
}
