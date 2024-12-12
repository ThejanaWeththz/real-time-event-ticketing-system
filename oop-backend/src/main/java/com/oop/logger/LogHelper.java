package com.oop.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class LogHelper {
    private static LogHelper instance;
    private final Logger logger;
    private Sinks.Many<String> logsSink;

    private LogHelper() {
        this.logger = LogManager.getLogger("GLOBAL");
        this.logsSink = Sinks.many().replay().limit(1);
    }

    public static LogHelper getInstance() {
        if (instance == null) {
            instance = new LogHelper();
        }
        return instance;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public synchronized void addLog(String log) {
        logsSink.tryEmitNext(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(new Date()) + log);
        logger.info(log);
        System.out.println();
    }

    public Flux<String> getStream() {
        return this.logsSink.asFlux()
                .doOnCancel(() -> logger.info("Client disconnected from log stream"))
                .doOnError(error -> logger.error("Error in log stream: {}", error.getMessage()));
    }
}
