package swa.meet.rabitmq.listener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import swa.meet.entities.Meeting;
import swa.meet.entities.Timeslot;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class MeetingNotificationCLI implements CommandLineRunner {
    private static final BlockingQueue<Meeting> meetingQueue = new LinkedBlockingQueue<>();

    public static void addMeeting(Meeting meeting) {
        meetingQueue.offer(meeting);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) {
            Meeting meeting = meetingQueue.take();
            System.out.println("Received meeting update:");
            System.out.println("Meeting ID: " + meeting.getId());
            System.out.println("Meeting Name: " + meeting.getScheduleName());
            System.out.println("Purpose: " + meeting.getDescription());
            for(Timeslot t : meeting.getTimeslots()){
                System.out.println("Start Time: " + t.getStart());
                System.out.println("Finish Time: " + t.getEnd());
                System.out.println("---------------------------------------------");
            }
            System.out.println("Created: " + meeting.getCreated());
            System.out.println("Valid Until: " + meeting.getValidUntil());

        }
    }
}
