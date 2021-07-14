package com.atguigu.scw.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TProjectImages;
import com.atguigu.scw.project.bean.TProjectImagesExample;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.bean.TReturnExample;
import com.atguigu.scw.project.bean.TTag;
import com.atguigu.scw.project.bean.TType;
import com.atguigu.scw.project.mapper.TProjectImagesMapper;
import com.atguigu.scw.project.mapper.TProjectMapper;
import com.atguigu.scw.project.mapper.TReturnMapper;
import com.atguigu.scw.project.mapper.TTagMapper;
import com.atguigu.scw.project.mapper.TTypeMapper;
import com.atguigu.scw.project.service.ProjectInfoService;

@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {
	
	
	@Autowired
	TProjectMapper projectMapper;
	
	@Autowired
	TProjectImagesMapper projectImagesMapper;
	
	@Autowired
	TReturnMapper returnMapper;
	
	@Autowired
	TTypeMapper typeMapper;
	
	@Autowired
	TTagMapper tageMapper;
	

	@Override
	public TProject getProjectInfo(Integer projectId) {
		
		return projectMapper.selectByPrimaryKey(projectId);
	}


	@Override
	public List<TProjectImages> getProjectImages(Integer id) {
		// 根据projectId查出项目的头图和一些细节图
		TProjectImagesExample example = new TProjectImagesExample();
		example.createCriteria().andProjectidEqualTo(id);
		
		return projectImagesMapper.selectByExample(example);
	}


	@Override
	public List<TReturn> getProjectReturns(Integer id) {
		TReturnExample example = new TReturnExample();
		example.createCriteria().andProjectidEqualTo(id);
		// 查出一个集合
		return returnMapper.selectByExample(example);
	}


	@Override
	public TReturn getProjectReturnById(Integer returnId) {
		
		return returnMapper.selectByPrimaryKey(returnId);
	}


	@Override
	public List<TType> getProjectTypes() {
		// 查出类型表中的所有类型
		return typeMapper.selectByExample(null);
	}


	@Override
	public List<TTag> getProjectTags() {
		// 查出标签表中的所有标签
		return tageMapper.selectByExample(null);
	}


	@Override
	public List<TProject> getAllProject() {
		
		return projectMapper.selectByExample(null);
	}

}
