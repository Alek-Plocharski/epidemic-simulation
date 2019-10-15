import java.util.*;

public class SociableAgent extends Agent {

    public SociableAgent(int id) {

        super(id);
    }

    private boolean canMeetFriendsOfFriends() {

        return this.state.isSick();
    }

    @Override
    public Queue<Meeting> arrangeMeetings(int currentDay, int numberOfDays, float meetingProbability, Random rand) {

        Queue<Meeting> meetings = new LinkedList<>();
        HashSet<Agent> meetingCandidates = new HashSet<>(this.friends);

        if (this.friends.size() > 0 && (numberOfDays - currentDay) > 0) {

            if (this.canMeetFriendsOfFriends()) {

                for (Agent agent : this.friends)
                    meetingCandidates.addAll(agent.getFriends());

                meetingCandidates.remove(this);
            }


            while (rand.nextFloat() <= meetingProbability) {

                Iterator<Agent> friendIterator = meetingCandidates.iterator();
                Agent friend = null;
                int index = rand.nextInt(meetingCandidates.size());

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
        return this.id + this.state.toString() + " towarzyski";
    }
}
