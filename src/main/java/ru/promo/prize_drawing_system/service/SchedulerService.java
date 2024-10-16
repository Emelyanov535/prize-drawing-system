package ru.promo.prize_drawing_system.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import ru.promo.prize_drawing_system.persistence.model.EventEntity;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class SchedulerService {

    private final TaskScheduler taskScheduler;
    private final RafflesService rafflesService;
    private final MailSenderService mailSenderService;

    public void scheduleEventExecution(EventEntity eventEntity) {
        LocalDateTime drawTime = eventEntity.getDateOfTheDraw();
        Runnable task = () -> runEvent(eventEntity.getId());

        ZoneId zoneId = ZoneId.systemDefault();
        var date = Date.from(drawTime.atZone(zoneId).toInstant());
        taskScheduler.schedule(task, date.toInstant());
    }

    private void runEvent(Long eventId) {
        log.info("Начался розыграш: {}", eventId);

        final Map<String, String> prizes = rafflesService.drawPrizesForEvent(eventId);

        for (Map.Entry<String, String> entry : prizes.entrySet()) {
            mailSenderService.sendMail(entry.getKey(), "Ты выйграл приз!!",
                    "Поздравляю, ты выиграл: " + entry.getValue());
        }
    }
}
