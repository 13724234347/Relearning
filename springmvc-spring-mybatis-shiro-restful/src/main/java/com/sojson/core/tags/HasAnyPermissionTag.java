package com.sojson.core.tags;

import com.sojson.core.statics.Constant;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.tags.PermissionTag;

public class HasAnyPermissionTag extends PermissionTag {
	private static final long serialVersionUID = 1L;
	public HasAnyPermissionTag() {
	}
	@Override
	protected boolean showTagBody(String permissions) {
		boolean hasAnyPermission = false;
		Subject subject = getSubject();
		if (subject != null) {
			//根据PERMISSION_SPLIT常量来拆分页面传进来的字符串，并使用subject来进行验证
			for (String permission : permissions.split(Constant.PERMISSION_SPLIT)) {
				if (subject.isPermitted(permission.trim())) {
					hasAnyPermission = true;
					break;
				}
			}
		}
		return hasAnyPermission;
	}
}