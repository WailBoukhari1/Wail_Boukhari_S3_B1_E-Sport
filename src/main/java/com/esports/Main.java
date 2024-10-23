package com.esports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.esports.ui.ConsoleInterface;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        LOGGER.info("Starting E-Sports Tournament Management System");

        // Get the H2 web server and log its URL
        org.h2.tools.Server h2WebServer = context.getBean("h2WebServer", org.h2.tools.Server.class);
        String h2ConsoleUrl = h2WebServer.getURL();
        LOGGER.info("H2 Console URL: {}", h2ConsoleUrl);

        ConsoleInterface consoleInterface = new ConsoleInterface();

        // // Schedule tournament status updates
        // ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        // scheduler.scheduleAtFixedRate(() -> {
        //     LOGGER.info("Scheduled update of tournament statuses");
        //     consoleInterface.updateTournamentStatuses();
        // }, 1, 1, TimeUnit.MINUTES);

        // Start the console interface
        consoleInterface.start();

        // // Shutdown the scheduler when the application terminates
        // Runtime.getRuntime().addShutdownHook(new Thread(scheduler::shutdown));

        LOGGER.info("E-Sports Tournament Management System terminated");
    }
}
