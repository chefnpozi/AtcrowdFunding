package com.atguigu.springcloud.service.exp;

import org.springframework.stereotype.Component;

import com.atguigu.springcloud.bean.Movie;
import com.atguigu.springcloud.service.MovieServiceFeign;

@Component
public class MovieServiceFeignExceptionHandler implements MovieServiceFeign {

	// 这样的一个好处是，熔断方法不与Controller写在一个类中，这样Controller就不会很臃肿，比较优雅
	@Override
	public Movie getMovieById(Integer id) {
		// 在这里写兜底操作
		Movie movie = new Movie();
		movie.setId(-100);
		movie.setName("无此电影");
		
		return movie;
	}

}
