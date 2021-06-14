package com.atguigu.atcrowdfunding.bean;

import java.util.ArrayList;
import java.util.List;

public class TPermission {
    private Integer id;

    private String name;

    private String title;

    private String icon;

    private Integer pid;
    
    // 迭代父对象，然后通过这个属性，拿到它的孩子
    private List<TPermission> children = new ArrayList<TPermission>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

	public List<TPermission> getChildren() {
		return children;
	}

	public void setChildren(List<TPermission> children) {
		this.children = children;
	}
}