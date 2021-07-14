package com.atguigu.scw.project.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.atguigu.scw.enums.ProjectStatusEnume;
import com.atguigu.scw.project.bean.TReturn;
import com.atguigu.scw.project.component.OssTemplate;
import com.atguigu.scw.project.constant.ProjectConstant;
import com.atguigu.scw.project.service.TProjectService;
import com.atguigu.scw.project.vo.req.ProjectBaseInfoVo;
import com.atguigu.scw.project.vo.req.ProjectRedisStorageVo;
import com.atguigu.scw.project.vo.req.ProjectReturnVo;
import com.atguigu.scw.vo.req.BaseVo;
import com.atguigu.scw.vo.resp.AppResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "项目发起模块")
@RequestMapping("/project/create")
@RestController
public class ProjectCreateController {

	@Autowired
	OssTemplate ossTemplate;

	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	TProjectService projectService;

	@ApiOperation(value = "1-项目初始创建")
	@PostMapping("/init")
	public AppResponse<Object> init(BaseVo vo) {

		try {

			String accessToken = vo.getAccessToken();

			if (StringUtils.isEmpty(accessToken)) {
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("请求必须提供令牌值");
				return resp;
			}
			// 不为空，去redis中取出token
			String memberId = stringRedisTemplate.opsForValue().get(accessToken);

			if (StringUtils.isEmpty(memberId)) {
				// 时间太久，token过期了，请用户再去登录
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("请用户去登录");
				return resp;
			}

			// new 一个 bigVo
			ProjectRedisStorageVo bigVO = new ProjectRedisStorageVo();
			// 对拷
			BeanUtils.copyProperties(vo, bigVO);
			// 声明一个唯一标志符
			String projectToken = UUID.randomUUID().toString().replaceAll("-", "");
			bigVO.setProjectToken(projectToken);
			// 使用 fastJSON 序列化为 json 字符串
			String bigStr = JSON.toJSONString(bigVO);
			// 存入缓存区
			stringRedisTemplate.opsForValue().set(ProjectConstant.TEMP_PROJECT_PREFIX + projectToken, bigStr);

			log.debug("大VO数据：{}", bigVO);

			// 使用 jackson 组件把 bigVO 序列化为 json串
			return AppResponse.ok(bigVO);
		} catch (BeansException e) {

			e.printStackTrace();
			log.debug("失败，抛出异常！");
			return AppResponse.fail(null);
		}
	}

	@ApiOperation(value = "2-项目基本信息")
	@PostMapping("/baseInfo")
	public AppResponse<Object> baseInfo(ProjectBaseInfoVo vo) {

		try {
			// 先校验
			String accessToken = vo.getAccessToken();

			if (StringUtils.isEmpty(accessToken)) {
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("请求必须提供令牌值");
				return resp;
			}
			// 不为空，去redis中取出token
			String memberId = stringRedisTemplate.opsForValue().get(accessToken);

			if (StringUtils.isEmpty(memberId)) {
				// 时间太久，token过期了，请用户再去登录
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("请用户去登录");
				return resp;
			}

			// 从redis中获取大VO,进行对拷
			String projectToken = vo.getProjectToken(); // 通过projectToken知道是哪一个大VO
			// 通过前缀 project.create.temp + projectToken 获取 redis 中的大VO
			projectToken = ProjectConstant.TEMP_PROJECT_PREFIX + projectToken;
			String bigStr = stringRedisTemplate.opsForValue().get(projectToken);
			// 通过 fastJSON 将字符串反序列化为 VO对象
			ProjectRedisStorageVo bigVO = JSON.parseObject(bigStr, ProjectRedisStorageVo.class);
			// 对拷
			BeanUtils.copyProperties(vo, bigVO);
			// 再通过加了前缀的projectToken存入redis
			bigStr = JSON.toJSONString(bigVO);
			stringRedisTemplate.opsForValue().set(projectToken, bigStr);

			log.debug("大VO数据：{}", bigVO);

			// 通过 jackson 组件进行序列化
			return AppResponse.ok(bigVO);
		} catch (Exception e) {

			e.printStackTrace();
			log.error("表单处理失败：{}", e.getMessage());
			return AppResponse.fail(null);
		}

	}

	@ApiOperation(value = "3-添加项目回报档位")
	@PostMapping("/return")
	public AppResponse<Object> returnDetail(@RequestBody List<ProjectReturnVo> vos) {

		try {
			// 先校验
			String accessToken = vos.get(0).getAccessToken();

			if (StringUtils.isEmpty(accessToken)) {
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("请求必须提供令牌值");
				return resp;
			}
			// 不为空，去redis中取出token
			String memberId = stringRedisTemplate.opsForValue().get(accessToken);

			if (StringUtils.isEmpty(memberId)) {
				// 时间太久，token过期了，请用户再去登录
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("请用户去登录");
				return resp;
			}

			// 获取大vo，准备循环对拷
			String projectToken = vos.get(0).getProjectToken();
			projectToken = ProjectConstant.TEMP_PROJECT_PREFIX + projectToken; // 加上数据库的前缀
			String bigStr = stringRedisTemplate.opsForValue().get(projectToken);
			// 通过JSON组件将字符串转为大VO
			ProjectRedisStorageVo bigVO = JSON.parseObject(bigStr, ProjectRedisStorageVo.class);
			// 拿出bigVO中的 回报 数组
			List<TReturn> projectReturns = bigVO.getProjectReturns();
			// 将vos中的 回报小vo 装入 bigVO的回报数组
			for (ProjectReturnVo vo : vos) {
				TReturn returnObj = new TReturn();
				// 循环拷入 小 vo
				BeanUtils.copyProperties(vo, returnObj);
				projectReturns.add(returnObj);
			}
			// projectReturns是大VO的一个引用，此时数据已经在大VO中了
			bigStr = JSON.toJSONString(bigVO); // 转为JSON串
			stringRedisTemplate.opsForValue().set(projectToken, bigStr); // 放入redis，此时的projectToken是加了前缀的

			log.debug("大VO数据：{}", bigVO);
			return AppResponse.ok(bigVO);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("表单处理失败", e.getMessage());
			return AppResponse.fail(null);
		}
	}

//	@ApiOperation(value = "删除项目回报档位")
//	@DeleteMapping("/return")
//	public AppResponse<Object> deleteReturnDetail() {
//		return AppResponse.ok("ok");
//	}

	/**
	 * 文件上传表单要求： ①method="post" ②enctype="multipart/form-data" ③type="file"
	 * 
	 * SpringMVC框架集成commons-fileupload和commons-io组件，完成文件上传操作。 SpringMVC提供文件上传解析器。
	 * Controller处理文件上传时，通过 MultipartFile 接受文件。
	 */
	@ApiOperation(value = "上传图片")
	@PostMapping("/upload")
	public AppResponse<Object> upload(@RequestParam("uploadFile") MultipartFile[] files) {

		// MultipartFile类型的对象 封装了 文件的 名字 和 上传流 等 信息

		try {

			ArrayList<String> list = new ArrayList<String>();

			for (MultipartFile multipartFile : files) {
				String fileName = multipartFile.getOriginalFilename();
				// 生成动态的文件名
				fileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + fileName;

				// 拿到相应的文件流
				InputStream inputStream = multipartFile.getInputStream();
				String filePath = ossTemplate.upload(inputStream, fileName);

				list.add(filePath);

			}
			log.debug("上传文件路径={}", list);
			return AppResponse.ok(list);
		} catch (Exception e) {

			e.printStackTrace();
			log.error("上传文件出现异常");
			return AppResponse.fail(null);
		}
	}

//	@ApiOperation(value = "确认项目法人信息")
//	@PostMapping("/confirm/legal")
//	public AppResponse<Object> legal() {
//		return AppResponse.ok("ok");
//	}

//	@ApiOperation(value = "项目草稿保存")
//	@PostMapping("/tempsave")
//	public AppResponse<Object> tempsave() {
//		return AppResponse.ok("ok");
//	}

	@ApiOperation(value = "4 - 项目提交审核申请")
	@PostMapping("/submit")
	public AppResponse<Object> submit(String accessToken, String projectToken, String ops) {

		try {
			// 先校验
			if (StringUtils.isEmpty(accessToken)) {
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("请求必须提供令牌值");
				return resp;
			}
			// 不为空，去redis中取出token
			String memberId = stringRedisTemplate.opsForValue().get(accessToken);

			if (StringUtils.isEmpty(memberId)) {
				// 时间太久，token过期了，请用户再去登录
				AppResponse resp = AppResponse.fail(null);
				resp.setMsg("请用户去登录");
				return resp;
			}

			// 看ops的值是多少，决定是保存草稿，还是提交项目
			if ("0".equals(ops)) { // 保存草稿
				
				projectService.saveProject(accessToken, projectToken, ProjectStatusEnume.DRAFT.getCode());
				
				return AppResponse.ok("ok");
			} else if ("1".equals(ops)) { // 保存提交审核
				
				projectService.saveProject(accessToken, projectToken, ProjectStatusEnume.SUBMIT_AUTH.getCode());
				
				return AppResponse.ok("ok");
			} else {
				
				log.error("请求方式不支持");
				return AppResponse.fail("请求方式不支持");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("项目操作失败-{}",e.getMessage());
			return AppResponse.fail(null);
		}
	}

}
