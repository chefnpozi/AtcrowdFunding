package com.atguigu.scw.project.service;

import java.util.List;

import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TProjectImages;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.bean.TTag;
import com.atguigu.scw.project.bean.TType;

public interface ProjectInfoService {

	TProject getProjectInfo(Integer projectId);

	List<TProjectImages> getProjectImages(Integer id);

	List<TReturn> getProjectReturns(Integer id);

	TReturn getProjectReturnById(Integer returnId);

	List<TType> getProjectTypes();

	List<TTag> getProjectTags();

	List<TProject> getAllProject();

}
