package com.esports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esports.ui.ConsoleInterface;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("Starting E-Sports Tournament Management System");
        ConsoleInterface consoleInterface = new ConsoleInterface();
        consoleInterface.start();
        LOGGER.info("E-Sports Tournament Management System terminated");
    }
}