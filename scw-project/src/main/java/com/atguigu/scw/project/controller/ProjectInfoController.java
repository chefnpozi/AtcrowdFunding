package com.atguigu.scw.project.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.scw.project.bean.TMember;
import com.atguigu.scw.project.bean.TProject;
import com.atguigu.scw.project.bean.TProjectImages;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.bean.TTag;
import com.atguigu.scw.project.bean.TType;
import com.atguigu.scw.project.component.OssTemplate;
import com.atguigu.scw.project.mapper.TReturnMapper;
import com.atguigu.scw.project.service.MemberServiceFeign;
import com.atguigu.scw.project.service.ProjectInfoService;
import com.atguigu.scw.project.vo.resp.ProjectDetailVo;
import com.atguigu.scw.project.vo.resp.ProjectVo;
import com.atguigu.scw.project.vo.resp.ReturnPayConfirmVo;
import com.atguigu.scw.vo.resp.AppResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "项目信息模块")
@RequestMapping("/project")
@RestController
@Slf4j
public class ProjectInfoController {
	
	
	@Autowired
	OssTemplate ossTemplate;
	
	@Autowired
	ProjectInfoService projectInfoService;
	
	@Autowired
	MemberServiceFeign memberServiceFeign;
	
	
	
	@ApiOperation(value="[+]确认回报信息") 
	@GetMapping("/confirm/returns/{projectId}/{returnId}")
	public AppResponse<ReturnPayConfirmVo> returnInfo(@PathVariable("projectId") Integer projectId,
			                                     @PathVariable("returnId") Integer returnId){
		
		ReturnPayConfirmVo vo = new ReturnPayConfirmVo();
		
		// 回报数据
		TReturn tReturn = projectInfoService.getProjectReturnById(returnId);
		// 将该项目该回报信息封装进vo中
		vo.setReturnId(tReturn.getId());
		vo.setReturnContent(tReturn.getContent());
		vo.setNum(1); // 目前回报只有一件
		vo.setPrice(tReturn.getSupportmoney());
		vo.setFreight(tReturn.getFreight());
		vo.setSignalpurchase(tReturn.getSignalpurchase());
		vo.setPurchase(tReturn.getPurchase());
		
		log.debug("回报数据： {}", tReturn);
		
		// 项目数据
		TProject project = projectInfoService.getProjectInfo(projectId);
		// 封装此项目信息
		vo.setProjectId(project.getId());			// 项目id
		vo.setProjectName(project.getName());		// 项目名字
		vo.setProjectRemark(project.getRemark());	// 项目简介
		
		log.debug("项目数据： {}", project);
		
		// 发起人数据
		Integer memberid = project.getMemberid();	// 拿到发起人的id
		log.debug("会员id： {}", memberid);
		// 通过远程调用用户服务，拿到会员信息，即发起人信息
		AppResponse<TMember> resp = memberServiceFeign.getMemberById(memberid);
		TMember member = resp.getData();
		
		log.debug("发起人数据： {}", member);
		
		vo.setMemberId(member.getId());
		vo.setMemberName(member.getLoginacct());
		// 计算总价格，支持数量*支持价格 + 运费
		vo.setTotalPrice(new BigDecimal(vo.getNum() * vo.getPrice() + vo.getFreight()));
		return AppResponse.ok(vo);
	} 
	
	@ApiOperation(value="[+]获取项目详情信息") 
	@GetMapping("/details/info/{projectId}")
	public AppResponse<ProjectDetailVo> detailsInfo(@PathVariable("projectId") Integer projectId){
		// 根据projectId查出该项目
		TProject project = projectInfoService.getProjectInfo(projectId);
		// 把项目封装到对应的详情VO中进行展示
		ProjectDetailVo projectVo = new ProjectDetailVo();
		// 1.一个项目会有一张头图和若干张细节图，查出来
		List<TProjectImages> projectImages = projectInfoService.getProjectImages(project.getId());
		for (TProjectImages image : projectImages) {
			if (image.getImgtype() == 0) {
				projectVo.setHeaderImage(image.getImgurl());
			} else {
				// 把细节图装入 projectVo 的 集合中去
				projectVo.getDetailsImage().add(image.getImgurl());
			}
		}
		
		// 2.封装项目的支持档位在VO之中
		List<TReturn> projectReturns = projectInfoService.getProjectReturns(project.getId());
		// 通过set方法装入projectVo，其余的属性对拷即可
		projectVo.setProjectReturns(projectReturns);
		
		// 对拷其他属性
		BeanUtils.copyProperties(project, projectVo);
		return AppResponse.ok(projectVo);
	}
	
	
	@ApiOperation(value="获取项目回报列表") 
	@GetMapping("/details/returns/{projectId}")
	public AppResponse<List<TReturn>> detailsReturn(@PathVariable("projectId") Integer projectId){
		
		List<TReturn> returns = projectInfoService.getProjectReturns(projectId);
		return AppResponse.ok(returns);
	} 
	
	
	@ApiOperation(value="[+]获取某个项目回报档位信息") 
	@GetMapping("/details/returns/info/{returnId}")
	public AppResponse<TReturn> returnInfo(@PathVariable("returnId") Integer returnId){
		
		TReturn tReturn = projectInfoService.getProjectReturnById(returnId);
		return AppResponse.ok(tReturn);
	} 	
	
	
	@ApiOperation(value="[+]获取系统所有分类信息") 
	@GetMapping("/types")
	public AppResponse<List<TType>> types(){
		// 查出所有类型
		List<TType> types = projectInfoService.getProjectTypes();
		return AppResponse.ok(types);
	} 
	
	@ApiOperation(value="[+]获取系统所有标签信息") 
	@GetMapping("/tags")
	public AppResponse<List<TTag>> tags(){
		// 查出所有标签
		List<TTag> tags = projectInfoService.getProjectTags();
		return AppResponse.ok(tags);
	} 
	
	
	
	@ApiOperation(value="[+]获取系统所有的项目") 
	@GetMapping("/all")
	public AppResponse<List<ProjectVo>> all(){
		
		// 获取数据库中的所有项目，封装成projectVo返回，记得多封装一轮头图和详情图
		List<ProjectVo> vos = new ArrayList<ProjectVo>();
		// 查出所有的项目
		List<TProject> prosList = projectInfoService.getAllProject();
		for (TProject project : prosList) {
			// 查到每个项目的图片集,封装进vo
			List<TProjectImages> images = projectInfoService.getProjectImages(project.getId());
			ProjectVo vo = new ProjectVo();
			for (TProjectImages image : images) {
				if (image.getImgtype() == 0) {
					// 头图
					vo.setHeaderImage(image.getImgurl());
					log.debug("存入头图的url为：{}",image.getImgurl());
				} else {
					// 装进详情图列表
					vo.getDetailsImage().add(image.getImgurl());
					log.debug("存入详情图的url为： {}",image.getImgurl());
				}
			}
			// 封装其他属性进vo
			BeanUtils.copyProperties(project, vo);
			vos.add(vo);
		}
		return AppResponse.ok(vos);
	} 
	
	
	
	// 文件上传功能 。。
	 
	
	
}
