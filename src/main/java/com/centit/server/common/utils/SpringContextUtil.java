package com.centit.server.common.utils;

import java.util.Locale;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <p>获取bean的工具类，可用于在线程里面获取bean<p>
 * @version 1.0
 * @author li_hao
 * @date 2017年2月6日
 */
@SuppressWarnings({"unchecked","static-access"})
public class SpringContextUtil implements ApplicationContextAware{

	private static ApplicationContext context = null;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
	
	public static <T> T getBean(String beanName){
		return (T) context.getBean(beanName);
	}

	public static String getMessage(String key){
		return context.getMessage(key, null,Locale.getDefault());
		
	}
}
