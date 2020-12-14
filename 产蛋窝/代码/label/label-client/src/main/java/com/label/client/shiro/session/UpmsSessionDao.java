package com.label.client.shiro.session;

import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.label.client.util.SerializableUtil;
import com.label.common.constant.RedisConstant;
import com.label.common.util.redis.UtilRedis;

/**
 * 基于redis的sessionDao，缓存共享session
 * @author jolley
 */
public class UpmsSessionDao extends CachingSessionDAO {

    private static Logger _log = LoggerFactory.getLogger(UpmsSessionDao.class);
    
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        UtilRedis.setex(RedisConstant.LABEL_UPMS_SHIRO_SESSION_ID_ + sessionId, (int) session.getTimeout() / 1000, SerializableUtil.serialize(session));
        _log.debug("jolley>> doCreate >>>>> sessionId={}", sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        String session = UtilRedis.get(RedisConstant.LABEL_UPMS_SHIRO_SESSION_ID_ + sessionId);
        _log.debug("jolley>> doReadSession >>>>> sessionId={}", sessionId);
        return SerializableUtil.deserialize(session);
    }

    @Override
    protected void doUpdate(Session session) {
        // 如果会话过期/停止 没必要再更新了
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
            return;
        }
        // 更新session的最后一次访问时间
        UpmsSession upmsSession = (UpmsSession) session;
        UpmsSession cacheUpmsSession = (UpmsSession) doReadSession(session.getId());
        if (null != cacheUpmsSession) {
            upmsSession.setStatus(cacheUpmsSession.getStatus());
        }
        UtilRedis.setex(RedisConstant.LABEL_UPMS_SHIRO_SESSION_ID_ + session.getId(), (int) session.getTimeout() / 1000, SerializableUtil.serialize(session));
        // 更新XIAOC_UPMS_SERVER_SESSION_ID、XIAOC_UPMS_SERVER_CODE过期时间 TODO
        _log.debug("jolley>> doUpdate >>>>> sessionId={}", session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        String sessionId = session.getId().toString();
        UtilRedis.del(RedisConstant.LABEL_UPMS_SHIRO_SESSION_ID_ + sessionId);
        _log.debug("jolley>> doDelete >>>>> sessionId={}", sessionId);
    }

    /**
     * 更改在线状态
     *
     * @param sessionId
     * @param onlineStatus
     */
    public void updateStatus(Serializable sessionId, UpmsSession.OnlineStatus onlineStatus) {
        UpmsSession session = (UpmsSession) doReadSession(sessionId);
        if (null == session) {
            return;
        }
        session.setStatus(onlineStatus);
        UtilRedis.setex(RedisConstant.LABEL_UPMS_SHIRO_SESSION_ID_ + session.getId(), (int) session.getTimeout() / 1000, SerializableUtil.serialize(session));
    }

}
