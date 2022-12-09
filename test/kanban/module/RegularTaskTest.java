package kanban.module;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

class RegularTaskTest {
    private final ZoneId zone = ZoneId.of("Europe/Moscow");

    private final RegularTask rt0
            = new RegularTask(
            0
            , "0 RegularTask"
            , "RegularTask with id 0"
            , StatusName.IN_PROGRESS
            , TaskType.REGULAR_TASK
            , Optional.of(Duration.ofMinutes(60))
            , Optional.of(ZonedDateTime.of(
            LocalDateTime.of(2022, 12, 8, 22, 0)
            , zone))
    );
    
    @Test
    public void shouldReturnEndTime(){

        final ZonedDateTime startTimePlusOneHour
                = ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 8, 23,0)
                ,zone
        );

        Optional<ZonedDateTime> expected = Optional.of(startTimePlusOneHour);

        assertEquals(expected, rt0.getEndTime(), "Время не совпадает");
    }
    @Test
    public void shouldReturnTrue(){

        final RegularTask rtSame = new RegularTask(
                0
                , "0 RegularTask"
                , "RegularTask with id 0"
                , StatusName.IN_PROGRESS
                , TaskType.REGULAR_TASK
                , Optional.of(Duration.ofMinutes(60))
                , Optional.of(ZonedDateTime.of(
                LocalDateTime.of(2022, 12, 8, 22, 0)
                , zone))
        );

        System.out.println(rt0);
        assertEquals(rtSame,rt0, "Regular Task не равны");
    }
}