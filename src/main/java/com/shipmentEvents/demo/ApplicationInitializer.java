package element.bst.elementexploration.config;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.EnumSet;

import javax.servlet.*;
import javax.servlet.FilterRegistration.Dynamic;

/**
 * 
 * @author Amit Patel
 *
 */
public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) {

        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(WebConfig.class);
        rootContext.setServletContext(container);
        rootContext.refresh();

        container.addListener(new ContextLoaderListener(rootContext));
        container.addListener(new RequestContextListener());
        Dynamic springSecurityFilterChain = container.addFilter("springSecurityFilterChain", new
                DelegatingFilterProxy());
        springSecurityFilterChain.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD)
                , false, "/*");
        springSecurityFilterChain.setAsyncSupported(true);
        
        
        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        dispatcher.setAsyncSupported(true);
    }
}