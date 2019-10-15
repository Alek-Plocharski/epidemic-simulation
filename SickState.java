import java.util.Random;

public class SickState extends State {

    @Override
    public boolean willDieFromSickness(float deathProbability, Random rand) {

        return (deathProbability >= rand.nextFloat());
    }

    @Override
    public boolean willBeCured(float cureProbability, Random rand) {

        return (cureProbability >= rand.nextFloat());
    }

    @Override
    public void addToCounter(Simulation.Counter healthy, Simulation.Counter sick, Simulation.Counter immune) {

        sick.addOne();
    }

    @Override
    public float getMeetingProbability(float meetingProbability) {

        return (meetingProbability / 2);
    }

    @Override
    public boolean isSick() {

        return true;
    }

    @Override
    public boolean canBeInfected() {

        return false;
    }

    @Override
    public boolean infect(float infectionProbability, Random rand) {

        return (rand.nextFloat() <= infectionProbability);
    }

    @Override
    public String toString() {

        return "*";
    }
}
