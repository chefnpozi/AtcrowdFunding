package com.atguigu.springcloud.dao;

import org.springframework.stereotype.Repository;

import com.atguigu.springcloud.bean.Movie;

@Repository
public class MovieDao {

	public Movie getMovieById(Integer id) {
		Movie movie = new Movie();
		movie.setId(id);
		movie.setName("流浪地球-"+id);
		return movie;
	}

	
	
}
