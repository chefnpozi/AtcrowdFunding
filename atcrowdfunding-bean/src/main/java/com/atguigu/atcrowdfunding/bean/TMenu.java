package com.atguigu.atcrowdfunding.bean;

import java.util.ArrayList;
import java.util.List;

public class TMenu {
    private Integer id;

    private Integer pid;

    private String name;

    private String icon;

    private String url;

    // 迭代父对象，然后通过这个属性，拿到它的孩子
    private List<TMenu> children = new ArrayList<TMenu>();
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

	public List<TMenu> getChildren() {
		return children;
	}

	public void setChildren(List<TMenu> children) {
		this.children = children;
	}
}