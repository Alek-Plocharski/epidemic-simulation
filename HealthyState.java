import java.util.Random;

public class HealthyState extends State {

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

        healthy.addOne();
    }

    @Override
    public float getMeetingProbability(float meetingProbability) {

        return meetingProbability;
    }

    @Override
    public boolean isSick() {

        return false;
    }

    @Override
    public boolean canBeInfected() {

        return true;
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
