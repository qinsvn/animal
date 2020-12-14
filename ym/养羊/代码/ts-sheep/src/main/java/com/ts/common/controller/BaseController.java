package com.ts.common.controller;

import com.ts.system.domain.UserToken;
import org.springframework.stereotype.Controller;
import com.ts.common.utils.ShiroUtils;
import com.ts.system.domain.UserDO;

@Controller
public class BaseController {
	public UserDO getUser() {
		return ShiroUtils.getUser();
	}

	public Long getUserId() {
		return getUser().getUserId();
	}

	public String getUsername() {
		return getUser().getUsername();
	}
}