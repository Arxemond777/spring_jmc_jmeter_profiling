package example.cron;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class Cron {
    @Scheduled(fixedDelay = 500)
    public void scheduleFixedDelayTask() { // 120
        System.err.println("Fixed delay task - " + System.currentTimeMillis() / 1000);
    }
}
