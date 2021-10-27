import bean.Client;
import bean.Event;
import bean.EventType;
import database.DatabaseService;
import logger.EventLogger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.DateFormat;
import java.util.Map;
import java.util.Random;

public class App {

    private final Client client;

    private final EventLogger defaultLogger;
    private final Map<EventType, EventLogger> loggers;

    public App(Client client, EventLogger defaultLogger, Map<EventType, EventLogger> loggers) {
        this.client = client;
        this.defaultLogger = defaultLogger;
        this.loggers = loggers;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("spring.xml");
        App app = (App) applicationContext.getBean("app");

        app.logFewEvents(applicationContext);
        System.out.println(app.client.getFullName());

        applicationContext.close();
    }

    public void logEvent(EventType eventType, Event event, String message) {
        EventLogger logger = loggers.get(eventType);

        if (logger == null) {
            logger = defaultLogger;
        }

        event.setMsg(message);
        logger.logEvent(event);
    }

    public void logFewEvents(ConfigurableApplicationContext applicationContext) {
        for (int i = 0; i < 10; i++) {
            Random rnd = new Random();
            int randomNum = rnd.nextInt(10);
            String randomEventMessage = Integer.toString(rnd.nextInt(1000000));
            Event event = (Event) applicationContext.getBean("event");

            if (randomNum <= 2) {
                logEvent(EventType.INFO, event, "Info: " + randomEventMessage);
            } else if (randomNum <= 4) {
                logEvent(EventType.DEBUG, event, "Debug: " + randomEventMessage);
            } else if (randomNum <= 6) {
                logEvent(EventType.WARNING, event, "Warning: " + randomEventMessage);
            } else if (randomNum <= 8) {
                logEvent(EventType.ERROR, event, "Error: " + randomEventMessage);
            } else {
                logEvent(EventType.FATAL, event, "Fatal: " + randomEventMessage);
            }
        }
    }

}
