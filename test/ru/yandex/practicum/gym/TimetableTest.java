package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Map<TimeOfDay, List<TrainingSession>> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondaySessions.size());
        Assertions.assertTrue(mondaySessions.containsKey(new TimeOfDay(13, 0)));
        Assertions.assertEquals(1, mondaySessions.get(new TimeOfDay(13, 0)).size());
        Assertions.assertEquals(singleTrainingSession, mondaySessions.get(new TimeOfDay(13, 0)).get(0));

        Map<TimeOfDay, List<TrainingSession>> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Map<TimeOfDay, List<TrainingSession>> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondaySessions.size());
        Assertions.assertTrue(mondaySessions.containsKey(new TimeOfDay(13, 0)));
        Assertions.assertEquals(1, mondaySessions.get(new TimeOfDay(13, 0)).size());

        Map<TimeOfDay, List<TrainingSession>> thursdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        Assertions.assertEquals(2, thursdaySessions.size());
        List<TimeOfDay> sortedTimes = new ArrayList<>(thursdaySessions.keySet());
        Collections.sort(sortedTimes);
        Assertions.assertEquals(new TimeOfDay(13, 0), sortedTimes.get(0));
        Assertions.assertEquals(new TimeOfDay(20, 0), sortedTimes.get(1));

        Map<TimeOfDay, List<TrainingSession>> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(tuesdaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> monday13Sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        Assertions.assertEquals(1, monday13Sessions.size());
        Assertions.assertEquals(singleTrainingSession, monday13Sessions.get(0));

        List<TrainingSession> monday14Sessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Assertions.assertTrue(monday14Sessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayEmptyTimetable() {
        Timetable timetable = new Timetable();

        Map<TimeOfDay, List<TrainingSession>> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertTrue(mondaySessions.isEmpty());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessionsSameTime() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Петр", "Петрович");
        Group group1 = new Group("Йога", Age.ADULT, 60);
        Group group2 = new Group("Пилатес", Age.ADULT, 60);
        TimeOfDay time = new TimeOfDay(18, 0);
        DayOfWeek day = DayOfWeek.WEDNESDAY;

        TrainingSession session1 = new TrainingSession(group1, coach1, day, time);
        TrainingSession session2 = new TrainingSession(group2, coach2, day, time);

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        Map<TimeOfDay, List<TrainingSession>> sessions = timetable.getTrainingSessionsForDay(day);
        Assertions.assertEquals(1, sessions.size());
        Assertions.assertTrue(sessions.containsKey(time));
        Assertions.assertEquals(2, sessions.get(time).size());
        Assertions.assertTrue(sessions.get(time).contains(session1));
        Assertions.assertTrue(sessions.get(time).contains(session2));
    }

    @Test
    void testGetTrainingSessionsForDayDifferentDays() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Сидоров", "Сидор", "Сидорович");
        Group group = new Group("Бокс", Age.ADULT, 90);

        TrainingSession mondaySession = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(19, 0));
        TrainingSession wednesdaySession = new TrainingSession(group, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(19, 0));
        TrainingSession fridaySession = new TrainingSession(group, coach, DayOfWeek.FRIDAY, new TimeOfDay(19, 0));

        timetable.addNewTrainingSession(mondaySession);
        timetable.addNewTrainingSession(wednesdaySession);
        timetable.addNewTrainingSession(fridaySession);

        Map<TimeOfDay, List<TrainingSession>> mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, mondaySessions.size());
        Assertions.assertTrue(mondaySessions.containsKey(new TimeOfDay(19, 0)));
        Assertions.assertEquals(1, mondaySessions.get(new TimeOfDay(19, 0)).size());

        Map<TimeOfDay, List<TrainingSession>> tuesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        Assertions.assertTrue(tuesdaySessions.isEmpty());

        Map<TimeOfDay, List<TrainingSession>> wednesdaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY);
        Assertions.assertEquals(1, wednesdaySessions.size());
        Assertions.assertTrue(wednesdaySessions.containsKey(new TimeOfDay(19, 0)));
        Assertions.assertEquals(1, wednesdaySessions.get(new TimeOfDay(19, 0)).size());
    }

    @Test
    void testGetCountByCoachesSingleCoachSingleSession() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Group group = new Group("Акробатика", Age.CHILD, 60);
        TrainingSession session = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(session);

        List<Map.Entry<Coach, Integer>> result = timetable.getCountByCoaches();
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(coach, result.get(0).getKey());
        Assertions.assertEquals(1, (int) result.get(0).getValue());
    }

    @Test
    void testGetCountByCoachesMultipleCoachesDifferentCounts() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович"); // 3 занятия
        Coach coach2 = new Coach("Петров", "Петр", "Петрович"); // 1 занятие
        Coach coach3 = new Coach("Сидоров", "Сидор", "Сидорович"); // 2 занятия

        Group group = new Group("Фитнес", Age.ADULT, 60);

        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(10, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.FRIDAY, new TimeOfDay(10, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.TUESDAY, new TimeOfDay(11, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach3, DayOfWeek.THURSDAY, new TimeOfDay(12, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach3, DayOfWeek.SATURDAY, new TimeOfDay(12, 0)));

        List<Map.Entry<Coach, Integer>> result = timetable.getCountByCoaches();
        Assertions.assertEquals(3, result.size());

        Assertions.assertEquals(coach1, result.get(0).getKey());
        Assertions.assertEquals(3, (int) result.get(0).getValue());

        Assertions.assertEquals(coach3, result.get(1).getKey());
        Assertions.assertEquals(2, (int) result.get(1).getValue());

        Assertions.assertEquals(coach2, result.get(2).getKey());
        Assertions.assertEquals(1, (int) result.get(2).getValue());
    }

    @Test
    void testGetCountByCoachesEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<Map.Entry<Coach, Integer>> result = timetable.getCountByCoaches();
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetCountByCoachesSingleCoachMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Кузнецов", "Алексей", "Алексеевич");
        Group group1 = new Group("Йога", Age.ADULT, 60);
        Group group2 = new Group("Пилатес", Age.ADULT, 60);

        timetable.addNewTrainingSession(new TrainingSession(group1, coach, DayOfWeek.MONDAY, new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group2, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(9, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group1, coach, DayOfWeek.FRIDAY, new TimeOfDay(9, 0)));

        List<Map.Entry<Coach, Integer>> result = timetable.getCountByCoaches();
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(coach, result.get(0).getKey());
        Assertions.assertEquals(3, (int) result.get(0).getValue());
    }

    @Test
    void testGetCountByCoachesMultipleCoachesSameCount() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Смирнов", "Сергей", "Сергеевич");
        Coach coach2 = new Coach("Михайлов", "Михаил", "Михайлович");
        Group group = new Group("Танцы", Age.CHILD, 45);

        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.MONDAY, new TimeOfDay(16, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach1, DayOfWeek.WEDNESDAY, new TimeOfDay(16, 0)));

        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.TUESDAY, new TimeOfDay(16, 0)));
        timetable.addNewTrainingSession(new TrainingSession(group, coach2, DayOfWeek.THURSDAY, new TimeOfDay(16, 0)));

        List<Map.Entry<Coach, Integer>> result = timetable.getCountByCoaches();
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(2, (int) result.get(0).getValue());
        Assertions.assertEquals(2, (int) result.get(1).getValue());
    }
}
