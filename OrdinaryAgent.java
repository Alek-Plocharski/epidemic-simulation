import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class OrdinaryAgent extends Agent {

    public OrdinaryAgent(int id) {

        super(id);
    }

    @Override
    public Queue<Meeting> arrangeMeetings(int currentDay, int numberOfDays, float meetingProbability, Random rand) {

        Queue<Meeting> meetings = new LinkedList<>();

        if (this.friends.size() > 0 && (numberOfDays - currentDay) > 0) {

            while (rand.nextFloat() <= this.state.getMeetingProbability(meetingProbability)) {

                Iterator<Agent> friendIterator = this.friends.iterator();
                Agent friend = null;
                int index = rand.nextInt(this.friends.size());

                for (int i = 0; i <= index; i++)
                    friend = friendIterator.next();

                Meeting meeting = new Meeting(this, friend, rand.nextInt(numberOfDays - currentDay) + currentDay + 1);
                ((LinkedList<Meeting>) meetings).addLast(meeting);
            }
        }

        return meetings;
    }

    @Override
    public String toString() {
        return this.id + this.state.toString() + " zwykły";
    }
}
