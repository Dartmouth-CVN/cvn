package model;

import java.util.LinkedList;

public class Schedule {

    long scheduleId;
    private LinkedList<Event> events;

    public Schedule() {
        this.events = new LinkedList<Event>();
    }

    public Schedule(LinkedList<Event> events) {
        this.events = events;
    }

    public long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(long scheduleId) {
        this.scheduleId = scheduleId;
    }

    // The setters
    public void addEvent(Event e) {
        if (!this.events.contains(e)) {
            this.events.add(e);
        }
    }

    public void removeEvent(Event e) {
        if (this.events.contains(e)) {
            this.events.remove(e);
        }
    }

    // The getters
    public LinkedList<Event> getEvents() {
        return this.events;
    }

    public void setEvents(LinkedList<Event> events) {
        this.events = events;
    }
}
