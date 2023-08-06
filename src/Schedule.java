import java.util.*;

public class Schedule {
    TreeSet<Event> scheduleSet = new TreeSet<>();
    TreeMap<Integer, Integer> scheduleMap = new TreeMap<>();

    void addEvent (int start, int end, String name){
        Event event = new Event();
        event.start = start;
        event.end = end;
        event.title = name;

        scheduleSet.add(event);

        scheduleMap.put(start, scheduleMap.getOrDefault(start,0) + 1);
        scheduleMap.put(end, scheduleMap.getOrDefault(end,0) - 1);
    }

    List<Event> getNext3 (int time){
        Event o = new Event();
        o.start = time;

        NavigableSet<Event> tailSet = scheduleSet.tailSet(o, true);
        List<Event> result = new ArrayList<>(3);
        for (int i = 0; i < 3; i++){
            if (!tailSet.isEmpty()){
                result.add(tailSet.pollFirst());
            }
        }
        return result;
    }

    boolean hasOverlaps(){
        int count = 0;
        for (Integer key: scheduleMap.keySet()){
            count += scheduleMap.get(key);
            if (count > 1) {
                return true;
            }

        }
        return false;

    }
    public static void main(String[] args) {
        Schedule schedule = new Schedule();
        schedule.addEvent(9,  10, "Daily meeting");
        schedule.addEvent(11, 12, "1:1 with Ivan");
        schedule.addEvent(15, 16, "Sync Big Bet project");
        schedule.addEvent(17, 18, "Java community meeting");
        schedule.addEvent(19, 21, "Private appointment");

        System.out.println(schedule.getNext3(9));
        System.out.println(schedule.hasOverlaps());

    }
}

class Event implements Comparable <Event>{
    int start;
    int end;
    String title;

    @Override
    public int compareTo(Event o) {
        if (start == o.start) {
            return Integer.compare(end, o.end);
        } else return Integer.compare(start, o.start);
    }

    @Override
    public String toString() {
        return "[ " + start + " ]" +  "[ " + end + " ]: " + title;
    }
}
