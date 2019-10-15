import java.util.Random;

public class Meeting {

    private Agent agent1;
    private Agent agent2;
    private int day;

    public Meeting(Agent agent1, Agent agent2, int day) {

        this.agent1 = agent1;
        this.agent2 = agent2;
        this.day = day;
    }

    public int getDay() {

        return this.day;
    }

    /*
     * Simulates a single meeting.
     */
    public void simulateMeeting(float infectionProbability, Random rand) {

        this.agent1.meet(this.agent2, infectionProbability, rand);
        this.agent2.meet(this.agent1, infectionProbability, rand);
    }
}
