package com.atguigu.scw.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TProjectImages;
import com.atguigu.scw.project.bean.TProjectTag;
import com.atguigu.scw.project.bean.TProjectType;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.constant.ProjectConstant;
import com.atguigu.scw.project.mapper.TProjectImagesMapper;
import com.atguigu.scw.project.mapper.TProjectMapper;
import com.atguigu.scw.project.mapper.TProjectTagMapper;
import com.atguigu.scw.project.mapper.TProjectTypeMapper;
import com.atguigu.scw.project.mapper.TReturnMapper;
import com.atguigu.scw.project.service.TProjectService;
import com.atguigu.scw.project.vo.req.ProjectRedisStorageVo;
import com.atguigu.scw.utils.AppDateUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TProjectServiceImpl implements TProjectService {
	
	// 使用不同的mapper将不同的DO持久化到数据库
	@Autowired
	TProjectMapper projectMapper;

	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	TProjectImagesMapper projectImagesMapper;
	
	@Autowired
	TReturnMapper returnMapper;
	
	@Autowired
	TProjectTypeMapper projectTypeMapper;
	
	@Autowired
	TProjectTagMapper projectTagMapper;

	@Transactional
	@Override
	public void saveProject(String accessToken, String projectToken, byte code) {
		
		// 1.从redis中获取 bigVO 数据，然后通过不同的DO持久化到数据库中
		String bigStr = stringRedisTemplate.opsForValue().get(ProjectConstant.TEMP_PROJECT_PREFIX+projectToken);
		ProjectRedisStorageVo bigVO = JSON.parseObject(bigStr, ProjectRedisStorageVo.class);
		
		
		// 2.保存项目
		// 将bigVO中的内容保存到DO中，这一次没有使用对拷，也不知道为什么
		TProject project = new TProject(); // DO
		
		project.setName(bigVO.getName());
		project.setRemark(bigVO.getRemark());
		project.setDay(bigVO.getDay());
		int money = bigVO.getMoney();
		project.setMoney((long)money);
		
		project.setStatus(code+"");
		String memberId = stringRedisTemplate.opsForValue().get(accessToken);
		project.setMemberid(Integer.parseInt(memberId));
		project.setCreatedate(AppDateUtils.getFormatTime());
		
			// 主键回填回来,原先是没有值的，保存后，通过设置配置文件，使得有这个projectId,所以成为回填
			// useGeneratedKeys="true" keyProperty="id"
		projectMapper.insertSelective(project);
		// 这样获取的岂不是个空值？已回填自动生成的id值
		Integer projectId = project.getId();
		log.debug("保存项目id={}",projectId);
		
		// 3.保存图片
		// 先保存一张头图，再保存后面的细节图
		TProjectImages projectImage = new TProjectImages(); // 单张图片,DO
		projectImage.setProjectid(projectId);
		projectImage.setImgurl(bigVO.getHeaderImage());
		projectImage.setImgtype((byte)0); // 0 代表头图
		projectImagesMapper.insertSelective(projectImage);
		
		// 保存细节图,因为存放的是图片的url地址，所以是String类型
		// 拿出大VO中的细节图的url，保存到数据库
		List<String> detailsImage = bigVO.getDetailsImage();
		
		for (String imgPath : detailsImage) {
			TProjectImages pic = new TProjectImages();
			pic.setProjectid(projectId);
			pic.setImgurl(imgPath);
			pic.setImgtype((byte)1); // 1 代表细节图
			projectImagesMapper.insertSelective(pic);
		}
		
		// 4.保存回报
		// 从大VO中拿出回报
		List<TReturn> projectReturns = bigVO.getProjectReturns();
		for (TReturn tReturn : projectReturns) {
			// 为什么要记下这个id？因为要知道这个回报是属于哪一个项目，其他DO也是这个原因
			tReturn.setProjectid(projectId);
			// 挨个保存到数据库
			returnMapper.insertSelective(tReturn);
		}
		
		// 5.保存项目和分类关系
		List<Integer> typeids = bigVO.getTypeids();
		for (Integer typeid : typeids) {
			TProjectType projectType = new TProjectType(); // DO
			projectType.setProjectid(projectId);
			projectType.setTypeid(typeid);
			// 入库
			projectTypeMapper.insertSelective(projectType);
		}
		
		// 6.保存项目和标签关系
		List<Integer> tagids = bigVO.getTagids();
		for (Integer tagId : tagids) {
			TProjectTag projectTag = new TProjectTag();
			projectTag.setProjectid(projectId);
			projectTag.setTagid(tagId);
			// 入库
			projectTagMapper.insertSelective(projectTag);
		}
		
		// 7.保存发起人 （忽略）
		
		// 8.保存法人  	   （忽略）
		
		// 9.清理redis
		// 大VO中的数据分解为小DO，进行存储，完事后应清除缓存中的数据
		// stringRedisTemplate.delete(ProjectConstant.TEMP_PROJECT_PREFIX+projectToken);
	}
	

}
