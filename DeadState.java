import java.util.Random;

public class DeadState extends State {

    @Override
    public boolean willDieFromSickness(float deathProbability, Random rand) {

        return false;
    }

    @Override
    public boolean willBeCured(float cureProbability, Random rand) {

        return false;
    }

    @Override
    public void addToCounter(Simulation.Counter healthy, Simulation.Counter sick, Simulation.Counter immune) {

    }

    @Override
    public float getMeetingProbability(float meetingProbability) {

        return 0;
    }

    @Override
    public boolean isSick() {

        return false;
    }

    @Override
    public boolean canBeInfected() {

        return false;
    }

    @Override
    public boolean infect(float infectionProbability, Random rand) {

        return false;
    }

    @Override
    public String toString() {
        return "";
    }
}
