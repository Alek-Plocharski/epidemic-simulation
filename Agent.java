import java.util.HashSet;
import java.util.Queue;
import java.util.Random;

public abstract class Agent {

    protected int id;
    protected State state;
    protected HashSet<Agent> friends;

    public Agent(int id) {

        this.friends = new HashSet<>();
        this.id = id;
        this.state = new HealthyState();
    }

    public void addFriend(Agent agent) {

        friends.add(agent);
    }

    public void deleteFriend(Agent agent) {

        friends.remove(agent);
    }

    public HashSet<Agent> getFriends() {

        return this.friends;
    }

    public State getState() {

        return this.state;
    }

    public void setState(State state) {

        this.state = state;
    }

    public int id(){

        return this.id;
    }

    /*
     * Determines (with given probability) if agent should die from sickness.
     * If yes the function sets the agent's state to dead.
     */
    public void dieFromSickness(float deathProbability, Random rand) {

        if (this.state.willDieFromSickness(deathProbability, rand)) {

            for (Agent agent : this.friends)
                agent.deleteFriend(this);

            this.state = new DeadState();
        }
    }

    /*
     * Determines (with given probability) if agent should be cured from sickness.
     * If yes the function sets the agent's state to immune.
     */
    public void getCuredFromSickness(float cureProbability, Random rand) {

        if (this.state.willBeCured(cureProbability, rand))
            this.state = new ImmuneState();
    }

    /*
     * Adds one to the adequate counter depending on agent's state.
     */
    public void addToCounter(Simulation.Counter healthy, Simulation.Counter sick, Simulation.Counter immune) {

        this.state.addToCounter(healthy, sick, immune);
    }

    /*
     * Returns a string containing all of agent's friends ids.
     */
    public String friendsIds() {

        StringBuilder string = new StringBuilder();

        boolean pierwszy = true;

        for(Agent agent : this.friends) {

            if (pierwszy) {

                string.append(agent.id());
                pierwszy = false;
            }

            else {

                string.append(" ");
                string.append(agent.id());
            }
        }

        return string.toString();
    }

    /*
     * Simulates a meeting between two agents.
     * The simulation is one sided as only the agent passed as a parameter is effected.
     */
    public void meet(Agent agent, float infectionProbability, Random rand) {

        if (agent.getState().canBeInfected()) {

            if (this.state.infect(infectionProbability, rand))
                agent.state = new SickState();
        }
    }

    /*
     * Function arranges meetings for an agent.
     * (simulates a single day of arranging for an agent)
     */
    public abstract Queue<Meeting> arrangeMeetings(int currentDay, int numberOfDays, float meetingProbability, Random rand);
}
