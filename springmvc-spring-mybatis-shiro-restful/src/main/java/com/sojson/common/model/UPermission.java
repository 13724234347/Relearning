package com.sojson.common.model;

import java.io.Serializable;

import net.sf.json.JSONObject;

/**
 * 
 * 权限实体
 * 
 */
public class UPermission implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	/** 操作的url */
	private String url;
	/** 操作的名称 */
	private String name;
	private String method;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}