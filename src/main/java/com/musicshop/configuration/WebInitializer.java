package com.musicshop.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		AnnotationConfigWebApplicationContext root=new AnnotationConfigWebApplicationContext();
		root.register(RootContextConfiguration.class);
		
		servletContext.addListener(new ContextLoaderListener(root));
		
		AnnotationConfigWebApplicationContext webContext = new AnnotationConfigWebApplicationContext();
        webContext.setParent(root);
        
		Dynamic dispatcher=servletContext.addServlet("dispatcher", new DispatcherServlet(webContext));
		dispatcher.addMapping("/");
		dispatcher.setLoadOnStartup(1);
	}

}
