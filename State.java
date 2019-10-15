import java.util.Random;

public abstract class State {

    /*
     * Determines if state should be changed to dead because of dying from sickness.
     */
    public abstract boolean willDieFromSickness(float deathProbability, Random rand);

    /*
     * Determines (with given probability) if state should be changed to immune because of getting cured from sickness.
     */
    public abstract boolean willBeCured(float cureProbability, Random rand);

    /*
     * Add one to an adequate counter depending on the state.
     */
    public abstract void addToCounter(Simulation.Counter healthy, Simulation.Counter sick, Simulation.Counter immune);

    /*
     * Return meetingProbability adequate to the state.
     */
    public abstract float getMeetingProbability(float meetingProbability);

    /*
     * Returns true if this state represents a sick state.
     */
    public abstract boolean isSick();

    /*
     * Returns true if this state represents a healthy state
     */
    public abstract boolean canBeInfected();

    /*
     * Determines (with given probability) if state should be changed to sick because of getting infected.
     */
    public abstract boolean infect(float infectionProbability, Random rand);
}
