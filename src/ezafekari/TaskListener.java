package ezafekari;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by nedaja on 29/01/2018.
 */
public class TaskListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        Alarm alarm = new Alarm();
        new UpdateEzfTask().startTask();
        System.out.println("hi script ezafekar loaded...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
