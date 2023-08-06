import org.w3c.dom.events.Event;

import java.util.*;

public class Schedule {
    TreeSet<Even> scheduleSet = new TreeSet<>();
    TreeMap<Integer, Integer> scheduleMap = new TreeMap<>();

    void addEvent (int start, int end, String name){
        Even even = new Even();
        even.start = start;
        even.end = end;
        even.title = name;

        scheduleSet.add(even);

        scheduleMap.put(start, scheduleMap.getOrDefault(start,0) + 1);
        scheduleMap.put(end, scheduleMap.getOrDefault(end,0) - 1);
    }

    List<Even> getNext3 (int time){
        Even o = new Even();
        o.start = time;

        NavigableSet<Even> tailSet = scheduleSet.tailSet(o, true);
        List<Even> result = new ArrayList<>(3);
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

class Even implements Comparable <Even>{
    int start;
    int end;
    String title;

    @Override
    public int compareTo(Even o) {
        if (start == o.start) {
            return Integer.compare(end, o.end);
        } else return Integer.compare(start, o.start);
    }

    @Override
    public String toString() {
        return "[ " + start + " ]" +  "[ " + end + " ]: " + title;
    }
}
