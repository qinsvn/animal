package com.ts.common.utils;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Objects;
import com.ts.common.config.TsConfig;
import com.ts.system.domain.UserDO;

public class ShiroUtils {
	
	public final static ThreadLocal<UserDO> threadUser =   new ThreadLocal<UserDO>();
    @Autowired
    private static SessionDAO sessionDAO;

    public static Subject getSubjct() {
        return SecurityUtils.getSubject();
    }
    public static UserDO getUser() {
        Object object = getSubjct().getPrincipal();
        if (object==null) {
			return threadUser.get();
		}
        return (UserDO)object;
    }
    public static Long getUserId() {
        return getUser().getUserId();
    }
    public static boolean isMechanismOrg() {
    	if (ShiroUtils.getUser()!=null) {
    		if (Objects.equal(TsConfig.getProperty("ts.manage.maxOrg"), ShiroUtils.getUser().getDeptId().toString())) {
    			return true;	
    		}else{
    			return false;
    		}
		}else{
			return true;
		}
    }
    public static void logout() {
        getSubjct().logout();
    }

    public static List<Principal> getPrinciples() {
        List<Principal> principals = null;
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        return principals;
    }

	public static void setDeptId(long deptId) {
		if(threadUser.get()==null){
			threadUser.set(new UserDO());
		}
		threadUser.get().setDeptId(deptId);
	}
	public static void setUserId(long userId) {
		if(threadUser.get()==null){
			threadUser.set(new UserDO());
		}
		threadUser.get().setDeptId(userId);
	}
    
    
}
