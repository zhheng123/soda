package com.soda.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.soda.bean.News;
import com.soda.services.iface.NewsService;

@Controller
@RequestMapping("/test")
public class UserController {
	
	@Resource
	NewsService newService;
	
	@RequestMapping("/toIndex")
	public ModelAndView toIndex(){
		ModelAndView mav=new ModelAndView();
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping("/newToJson")
	public void toJson(HttpServletResponse res,Integer newId){
		res.setHeader("Content-type", "text/html;charset=UTF-8");
		res.setCharacterEncoding("utf-8");
		News news=newService.findNewById(newId);
		Gson gb=new GsonBuilder().disableHtmlEscaping().create();
		String newDetail=gb.toJson(news);
		try {
			PrintWriter out = res.getWriter();
			out.println(newDetail);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			gb=null;
			newDetail=null;
		}
	}
}
