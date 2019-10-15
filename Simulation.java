import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Simulation {

    private long seed;
    private int numberOfAgents;
    private float sociableProbability;
    private float meetingProbability;
    private float infectionProbability;
    private float cureProbability;
    private float deathRate;
    private int numberOfDays;
    private int averageFriendsNumber;
    private PrintWriter writer;
    private Random rand;
    private Graph agentGraph;
    private Day[] days;

    public Simulation(long seed, int numberOfAgents, float sociableProbability, float meetingProbability, float infectionProbability,
                     float cureProbability, float deathRate, int numberOfDays, int averageFriendsNumber, PrintWriter writer) {

        this.seed = seed;
        this.numberOfAgents = numberOfAgents;
        this.sociableProbability = sociableProbability;
        this.meetingProbability = meetingProbability;
        this.infectionProbability = infectionProbability;
        this.cureProbability = cureProbability;
        this.deathRate = deathRate;
        this.numberOfDays = numberOfDays;
        this.averageFriendsNumber = averageFriendsNumber;
        this.rand = new Random(seed);
        this.agentGraph = null;
        this.writer = writer;
    }

    public PrintWriter getWriter() {

        return this.writer;
    }

    public int getNumberOfAgents() {

        return numberOfAgents;
    }

    public int getAverageFriendsNumber() {

        return averageFriendsNumber;
    }

    public Random getRand() {

        return rand;
    }

    public Graph getAgentGraph() {

        return agentGraph;
    }

    public void setAgentGraph(Graph agentGraph) {

        this.agentGraph = agentGraph;
    }

    /*
     * Creates all agents that will be taking part in the simulation.
     */
    public Agent[] createAgents() {

        Agent[] agents = new Agent[this.numberOfAgents];

        for (int i = 0; i < this.numberOfAgents; i++) {

            if (rand.nextFloat() <= sociableProbability)
                agents[i] = new SociableAgent(i + 1);

            else
                agents[i] = new OrdinaryAgent(i + 1);
        }

        agents[this.rand.nextInt(numberOfAgents)].setState(new SickState());

        return agents;
    }

    public class Counter {

        private int number;

        public Counter() {

            this.number = 0;
        }

        public void addOne() {

            this.number++;
        }

        @Override
        public String toString() {

            return String.valueOf(this.number);
        }
    }

    /*
     * Returns a string with a day summary which contains number of healthy agents,
     * number of sick agents and number of immune agents.
     */
    private String daySummary() {

        Counter healthy = new Counter();
        Counter sick = new Counter();
        Counter immune = new Counter();

        for (Agent agent : this.agentGraph.getAgents())
            agent.addToCounter(healthy, sick, immune);

        return healthy.toString() + " " + sick.toString() + " " + immune.toString();
    }

    /*
     * Simulates arranging meeting for a given day and then adds them to proper days.
     */
    private void simulateArrangingMeetings(int day) {

        Queue<Meeting> meetings = new LinkedList<>(this.days[day].simulateArrangingMeetings(this.meetingProbability, this.agentGraph, this.numberOfDays));

        while (!meetings.isEmpty()) {

            Meeting meeting = meetings.poll();
            this.days[meeting.getDay() - 1].addMeeting(meeting);
        }
    }

    public void simulateEpidemy() {

        this.days = new Day[this.numberOfDays];

        for (int i = 0; i < this.numberOfDays; i++)
            this.days[i] = new Day(i + 1, rand);

        for (int i = 0 ; i < numberOfDays; i++) {

            days[i].simulateDyingAndCuring(this.deathRate, this.cureProbability, this.agentGraph);
            this.simulateArrangingMeetings(i);
            days[i].simulateMeetings(this.infectionProbability);

            this.writer.println(this.daySummary());
        }
    }

    @Override
    public String toString() {

        return ("seed = " + this.seed + "\n"
                + "numberOfAgents = " + this.numberOfAgents + "\n"
                + "sociableProbability = " + this.sociableProbability + "\n"
                + "meetingProbability = " + this.meetingProbability + "\n"
                + "infectionProbability = " + this.infectionProbability + "\n"
                + "cureProbability = " + this.cureProbability + "\n"
                + "deathRate = " + this.deathRate + "\n"
                + "numberOfDays = " + this.numberOfDays + "\n"
                + "averageFriendsNumber = " + this.averageFriendsNumber + "\n");
    }
}
