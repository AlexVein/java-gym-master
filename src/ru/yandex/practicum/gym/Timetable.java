package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Timetable {

    private final HashMap<DayOfWeek, Map<TimeOfDay, List<TrainingSession>>> timetable;

    private static final Comparator<Map.Entry<Coach, Integer>> COACH_COUNT_COMPARATOR =
            Map.Entry.<Coach, Integer>comparingByValue().reversed();

    public Timetable() {
        timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        Map<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(day);
        if (daySchedule == null) {
            daySchedule = new TreeMap<>();
            timetable.put(day, daySchedule);
        }

        List<TrainingSession> sessions = daySchedule.get(time);
        if (sessions == null) {
            sessions = new ArrayList<>();
            daySchedule.put(time, sessions);
        }

        sessions.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        Map<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);
        if (daySchedule == null) {
            return new TreeMap<>();
        }
        return new TreeMap<>(daySchedule);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        Map<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);
        if (daySchedule == null) {
            return Collections.emptyList();
        }

        List<TrainingSession> sessions = daySchedule.get(timeOfDay);
        if (sessions == null) {
            return Collections.emptyList();
        }

        return sessions;
    }

    public List<Map.Entry<Coach, Integer>> getCountByCoaches() {
        Map<Coach, Integer> coachCounts = new HashMap<>();
        for (Map<TimeOfDay, List<TrainingSession>> daySchedule : timetable.values()) {
            for (List<TrainingSession> sessions : daySchedule.values()) {
                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    Integer count = coachCounts.get(coach);
                    if (count == null) {
                        coachCounts.put(coach, 1);
                    } else {
                        coachCounts.put(coach, count + 1);
                    }
                }
            }
        }

        List<Map.Entry<Coach, Integer>> sortedCoachCounts = new ArrayList<>(coachCounts.entrySet());
        Collections.sort(sortedCoachCounts, COACH_COUNT_COMPARATOR);

        return sortedCoachCounts;
    }
}
