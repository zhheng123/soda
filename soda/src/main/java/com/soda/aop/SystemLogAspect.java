package com.soda.aop;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.soda.bean.Log;
import com.soda.bean.User;
import com.soda.services.LogService;
@Aspect  
@Component
public class SystemLogAspect {
	 //注入Service用于把日志保存数据库  
    @Resource  
    private LogService logService;  
    //本地异常日志记录对象  
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);  

    //Controller层切点  
    @Pointcut("execution (* com.soda.Controller..*.*(..))")    
    public void controllerAspect() {  
    }  
                                      
    /** 
     * 前置通知 用于拦截Controller层记录用户的操作 
     * 
     * @param joinPoint 切点 
     */  
    @Before("controllerAspect()")  
    public void doBefore(JoinPoint joinPoint) {  
    	 System.out.println("==========执行controller前置通知===============");
    	       if(logger.isInfoEnabled()){
    	             logger.info("before " + joinPoint);
         }
    }  

  //配置controller环绕通知,使用在方法aspect()上注册的切入点
/*    @Around("controllerAspect()")
    public void around(JoinPoint joinPoint){
        System.out.println("==========开始执行controller环绕通知===============");
        long start = System.currentTimeMillis();
        try {
            long end = System.currentTimeMillis();
            if(logger.isInfoEnabled()){
                logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
            }
            System.out.println("==========结束执行controller环绕通知===============");
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            if(logger.isInfoEnabled()){
                logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
            }
        }
    }*/
  
  /** 
   * 后置通知 用于拦截Controller层记录用户的操作 
   * 
   * @param joinPoint 切点 
   */  
  @After("controllerAspect()")  
  public  void after(JoinPoint joinPoint) {  

     /* HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();  
      HttpSession session = request.getSession();  */
      //读取session中的用户  
     // User user = (User) session.getAttribute("user");  
      //请求的IP  
      //String ip = request.getRemoteAddr();
      User user = new User();
      user.setName("张三");
      String ip = "127.0.0.1";
       try {  
          
          String targetName = joinPoint.getTarget().getClass().getName();  
          String methodName = joinPoint.getSignature().getName();  
          Object[] arguments = joinPoint.getArgs();  
          Class targetClass = Class.forName(targetName);  
          Method[] methods = targetClass.getMethods();
          String operationType = "";
          String operationName = "";
           for (Method method : methods) {  
               if (method.getName().equals(methodName)) {  
                  Class[] clazzs = method.getParameterTypes();  
                   if (clazzs.length == arguments.length) {  
                       operationType = method.getAnnotation(SystemControllerLog.class).description();
                       operationName = method.getAnnotation(SystemControllerLog.class).description();
                       break;  
                  }  
              }  
          }
          //*========控制台输出=========*//  
          System.out.println("=====controller后置通知开始=====");  
          System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType);  
          System.out.println("方法描述:" + operationName);  
          System.out.println("请求人:" + user.getName());  
          System.out.println("请求IP:" + ip);  
          Log log=new Log();
          log.setOpertype(operationName);
          logService.add(log);
//          //*========数据库日志=========*//  
//          SystemLog log = new SystemLog();  
//          log.setId(UUID.randomUUID().toString());
//          log.setDescription(operationName);  
//          log.setMethod((joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType);  
//          log.setLogType((long)0);  
//          log.setRequestIp(ip);  
//          log.setExceptioncode( null);  
//          log.setExceptionDetail( null);  
//          log.setParams( null);  
//          log.setCreateBy(user.getName());  
//          log.setCreateDate(new Date());  
//          //保存数据库  
//          systemLogService.insert(log);  
          System.out.println("=====controller后置通知结束=====");  
      }  catch (Exception e) {  
          //记录本地异常日志  
          logger.error("==后置通知异常==");  
          logger.error("异常信息:{}", e.getMessage());  
      }  
  } 
  
  //配置后置返回通知,使用在方法aspect()上注册的切入点
    @AfterReturning("controllerAspect()")
    public void afterReturn(JoinPoint joinPoint){
        System.out.println("=====执行controller后置返回通知=====");  
            if(logger.isInfoEnabled()){
                logger.info("afterReturn " + joinPoint);
            }
    }
  
  /** 
   * 异常通知 用于拦截记录异常日志 
   * 
   * @param joinPoint 
   * @param e 
   */  
   @AfterThrowing(pointcut="controllerAspect()", throwing="e")  
   public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {  
      /*HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();  
      HttpSession session = request.getSession();  
      //读取session中的用户  
      User user = (User) session.getAttribute(WebConstants.CURRENT_USER);  
      //获取请求ip  
      String ip = request.getRemoteAddr(); */ 
      //获取用户请求方法的参数并序列化为JSON格式字符串  
      System.out.println("异常=============================");
      User user = new User();
      user.setName("张三");
      String ip = "127.0.0.1";
       try {  
          
          String targetName = joinPoint.getTarget().getClass().getName();  
          String methodName = joinPoint.getSignature().getName();  
          Object[] arguments = joinPoint.getArgs();  
          Class targetClass = Class.forName(targetName);  
          Method[] methods = targetClass.getMethods();
          String operationType = "";
          String operationName = "";
           for (Method method : methods) {  
               if (method.getName().equals(methodName)) {  
                  Class[] clazzs = method.getParameterTypes();  
                   if (clazzs.length == arguments.length) {  
                       operationType = method.getAnnotation(SystemControllerLog.class).description();
                       operationName = method.getAnnotation(SystemControllerLog.class).description();
                       break;  
                  }  
              }  
          }
          //*========控制台输出=========*//  
          System.out.println("=====controller异常开始=====");  
          System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()")+"."+operationType);  
          System.out.println("方法描述:" + operationName);  
          System.out.println("请求人:" + user.getName());  
          System.out.println("请求IP:" + ip);  
      }  catch (Exception ex) {  
          //记录本地异常日志  
          logger.error("==异常通知异常==");  
          logger.error("异常信息:{}", ex.getMessage());  
      }  
       /*==========记录本地异常日志==========*/  

  }  

    /** 
     * 获取注解中对方法的描述信息 用于Controller层注解 
     * 
     * @param joinPoint 切点 
     * @return 方法描述 
     * @throws Exception 
     */  
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {  
        String targetName = joinPoint.getTarget().getClass().getName();  
        String methodName = joinPoint.getSignature().getName();  
        Object[] arguments = joinPoint.getArgs();  
        Class targetClass = Class.forName(targetName);  
        Method[] methods = targetClass.getMethods();  
        String description = "";  
        for (Method method : methods) {  
            if (method.getName().equals(methodName)) {  
                Class[] clazzs = method.getParameterTypes();  
                if (clazzs.length == arguments.length) {  
                    description = method.getAnnotation(SystemControllerLog.class).description();  
                    break;  
                }  
            }  
        }  
        return description;  
    }  
}
