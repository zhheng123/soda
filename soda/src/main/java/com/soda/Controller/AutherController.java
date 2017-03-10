package com.soda.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.soda.aop.SystemControllerLog;
import com.soda.bean.Auther;
import com.soda.services.AutherInterService;

@Controller
@RequestMapping("/index")
public class AutherController {
	
	@Resource
	AutherInterService aiService;
	
	@RequestMapping("/toJson")
	@SystemControllerLog(description = "删除用户")  
	public void  toJson(HttpServletResponse res,String id) throws Exception{
		res.setCharacterEncoding("utf-8");
		List<Auther>ls=new ArrayList<Auther>();
		String lsJson=null;
			ls=aiService.findAll();
			lsJson=JSON.toJSONString(ls);
			PrintWriter out = res.getWriter();
			out.write(lsJson);
			out.flush();
			out.close();
		
	} 
}
