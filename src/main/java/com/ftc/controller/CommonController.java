package com.ftc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ftc.bean.Stu;
import com.ftc.service.CommonService;
import com.ftc.service.RedisUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by MWL on 2017/11/25.
 */
@Controller
public class CommonController {
    @Autowired
    public CommonService commonservice;
    @Autowired
    RedisUtil redisUtil;
//    @Autowired
//    AppConfig app;
    //用户登陆
    @RequestMapping(value = "/loginPage", method = {RequestMethod.POST, RequestMethod.GET})
    public String login(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //System.out.println(app.getUsername());
    	
    	redisUtil.set("admin", "admin");
    	redisUtil.expire("admin", 10);
    	System.out.println(redisUtil.get("admin"));
    	Map m = new HashMap<String,Object>();
    	Stu s= new  Stu(1,"a","a","a","a","a","a"); 
    	m.put("a", "test");
    	m.put("b", 2);
    	m.put("user", s);
    	boolean f=redisUtil.hmset("maps", m);
    	//boolean f2=redisUtil.lSet("lis1", "list1");
    	System.out.println(f);
    	//System.out.println(redisUtil.hget("maps", "b"));
    	//System.out.println(redisUtil.lGetIndex("lis1", 0));
    	
    	String sno = request.getParameter("sno");
        String password = request.getParameter("password");
        String tname = commonservice.login(sno, password);
        String  no=commonservice.getsno(sno, password);
        List<Stu> stuinfo=commonservice.userinfor(sno);
        for (int i = 0; i < stuinfo.size(); i++) {
			if (sno.equals(stuinfo.get(i).getSno())) {
				request.getSession().setAttribute("sno",no);
				request.getSession().setAttribute("tname", tname);
			}
		}
        if (tname == null) {
        	//response.sendRedirect(request.getContextPath()+"/login.html?msg=error");
            return "redirect:/login.html";//失败跳转到登陆页面
        } else {
        	//response.sendRedirect(request.getContextPath()+"/index");
        	//request.getRequestDispatcher("/login/index").forward(request, response);
            return "redirect:/index";//成功 重定向跳转index
        }
    }

    //用户登陆成功主页面
    @RequestMapping(value = "/index", method = {RequestMethod.POST, RequestMethod.GET})
    public String loginindex(HttpSession session) {

        return "/login/index";
    }
    
//  //用户登陆失败重新登陆
//    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
//    public String loginError(HttpSession session) {
//    	session.setAttribute("msg", "用户名或密码错误！");
//        return "/login/login";
//    }

    //用户信息管理
    @RequestMapping(value = "/stuinfomation", method = {RequestMethod.POST, RequestMethod.GET})
    public String stuinfo(HttpSession session) {

        return "/common/information";
    }


    /*
       用户信息列表
    */
    @RequestMapping(value = "/stuinforlist",method= {RequestMethod.POST})
    @ResponseBody
    public Map getStuinforList(HttpServletRequest request){
    	//当前页
        int page=Integer.parseInt(request.getParameter("page"));
        //每页显示数量
        int pageSzie=Integer.parseInt(request.getParameter("rows"));//pageSzie
        //下一页从第几条开始显示
        int startRecord=(page-1)*pageSzie+1;
        int total=commonservice.gettstunumber();
        List<Stu>  stuinforlist=commonservice.stuinfo(startRecord-1,pageSzie);
        Map resultMap=new HashMap();
        resultMap.put("total",total);
        resultMap.put("rows",stuinforlist);
        return resultMap;
    }






    /*
    进入用户信息界面
    */
    @GetMapping(value="/info")
    public String stuinfor(){
        return "/common/information";
    }
    
  /**
   * 保存新增用户和修改的用户信息
   * @param s
   * @param session
   * @param request
   * @return
   */
    @PostMapping(value="/save_users")
    @ResponseBody
    public Map<String,String> saveUsers(Stu s,HttpSession session,HttpServletRequest request){

        Map<String,String> map=new HashMap<>();
        String sno=(String) session.getAttribute("sno");
        if(session.getAttribute("sno").equals(s.getSno())){
            map.put("msg","违法操作！不能修改自己的信息！");
            return map;
        }
        String id=request.getParameter("id");
        try{
            if("0".equals(id)){
                commonservice.addusers(s.getSno(),s.getSname(),s.getPassword(),s.getTno(),s.getTname(),s.getTgrade());
            }else{
                commonservice.updateusers(s.getId(),s.getSno(),s.getSname(),s.getPassword(),s.getTno(),s.getTname(),s.getTgrade());
            }
            map.put("success","true");
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg","请核对信息确保登录名唯一");
        }

        return map;
    }


    @PostMapping(value = "/remove_users")//删除用户
    @ResponseBody
    public Map<String,String> removeUsers(@RequestParam("id") Integer id,HttpSession session){


        Map<String,String> result = new HashMap<>();
//        if(session.getAttribute("id").equals(id)){
//            result.put("msg","违法操作！不能删除自己！");
//            return result;
//        }
        try{
            commonservice.removeUsers(id);
            result.put("success","true");
            System.out.println("删除Id: "+id);
        }catch(Exception e) {
            e.printStackTrace();
            result.put("msg","Some errors occured.");
        }
        return result;
    }

}
