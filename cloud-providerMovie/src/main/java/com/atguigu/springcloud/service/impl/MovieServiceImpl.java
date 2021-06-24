package com.atguigu.springcloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.springcloud.bean.Movie;
import com.atguigu.springcloud.dao.MovieDao;
import com.atguigu.springcloud.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	MovieDao movieDao ;

	@Override
	public Movie getMovieById(Integer id) {
		return movieDao.getMovieById(id);
	}
	
}
