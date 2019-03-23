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
			//����PERMISSION_SPLIT���������ҳ�洫�������ַ�������ʹ��subject��������֤
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