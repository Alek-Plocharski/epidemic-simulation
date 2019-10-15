import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Day {

    private Queue<Meeting> meetings;
    private Random rand;
    private int dayNumber;

    public Day(int day, Random rand) {

        this.meetings = new LinkedList<>();
        this.rand = rand;
        this.dayNumber = day;
    }

    public void addMeeting(Meeting meeting) {

        this.meetings.add(meeting);
    }

    /*
     * Simulates the process of dying from sickness and getting cured form sickness for every agent for a given day.
     */
    public void simulateDyingAndCuring(float deathProbability, float cureProbability, Graph agentGraph) {

        for (Agent agent : agentGraph.getAgents()) {

            agent.dieFromSickness(deathProbability, rand);
            agent.getCuredFromSickness(cureProbability, rand);
        }
    }

    /*
     * Simulates the process of arranging meetings for every agent for a given day.
     * Return a collection af all meetings planed.
     */
    public Queue<Meeting> simulateArrangingMeetings(float meetingProbability, Graph agentGraph, int numberOfDays) {

        Queue<Meeting> meetings = new LinkedList<>();

        for (Agent agent : agentGraph.getAgents())
            meetings.addAll(agent.arrangeMeetings(this.dayNumber, numberOfDays, meetingProbability, this.rand));

        return meetings;
    }

    /*
     * Simulates all meetings planed to take place on a given day.
     */
    public void simulateMeetings(float infectionProbability) {

        while (!this.meetings.isEmpty()) {

            Meeting meeting = this.meetings.poll();
            meeting.simulateMeeting(infectionProbability, this.rand);
        }
    }
}
