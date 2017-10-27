package cc.oceanz.learn.rocketmq.goods;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import cc.oceanz.learn.rocketmq.constants.ProjectEnum;

/**
 * Created by lwz on 2017/10/18 12:49.
 */
public class Main {

    public static void main(String[] args) {
        ProjectEnum projectEnum = ProjectEnum.TRADE_GOODS;

        try {
            ServletContextHandler springMvcHandler = new ServletContextHandler();
            springMvcHandler.setContextPath(projectEnum.getCtxPath());

            XmlWebApplicationContext ctx = new XmlWebApplicationContext();
            ctx.setConfigLocation("classpath:spring.xml");

            DispatcherServlet dispatcherServlet = new DispatcherServlet(ctx);
            dispatcherServlet.setContextConfigLocation("classpath:springmvc.xml");

            springMvcHandler.addEventListener(new ContextLoaderListener(ctx));
            springMvcHandler.addServlet(new ServletHolder(dispatcherServlet), "/*");

            Server server = new Server(projectEnum.getPort());
            server.setHandler(springMvcHandler);
            server.start();

            System.out.println("================= jetty server started on " + projectEnum.getPort() + " ===================");

            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}