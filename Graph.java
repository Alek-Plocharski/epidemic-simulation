import java.util.ArrayList;
import java.util.Random;

public class Graph {

    private Agent[] vertices;

    private class AgentPair {

        private Agent agent1;
        private Agent agent2;

        public AgentPair(Agent agent1, Agent agent2) {

            this.agent1 = agent1;
            this.agent2 = agent2;
        }

        public Agent getAgent1() {

            return agent1;
        }

        public Agent getAgent2() {

            return agent2;
        }
    }

    /*
     * Construct a graph of agents according to the guidelines in the task.
     */
    public Graph(Agent[] vertices, int averageFriendsNumber, Random rand) {

        this.vertices = vertices;
        int numberOfEdges = Math.round((vertices.length * averageFriendsNumber) / 2);

        if (numberOfEdges >= (vertices.length * (vertices.length - 1) / 2)) {

            for (int i = 0; i < this.vertices.length; i++)
                for (int j = i + 1; j < this.vertices.length; j++)
                    this.addEdge(vertices[i], vertices[j]);
        }

        else {

            ArrayList<AgentPair> edges = new ArrayList<>();

            for (int i = 0; i < this.vertices.length; i++)
                for (int j = i + 1; j < this.vertices.length; j++)
                    edges.add(new AgentPair(vertices[i], vertices[j]));

            while (numberOfEdges > 0) {

                int index = rand.nextInt(edges.size());

                this.addEdge(edges.get(index).getAgent1(), edges.get(index).getAgent2());
                edges.remove(index);

                numberOfEdges--;
            }
        }
    }

    private void addEdge(Agent agent1, Agent agent2) {

        agent1.addFriend(agent2);
        agent2.addFriend(agent1);
    }

    public Agent getAgent(int id) {

        return vertices[id - 1];
    }

    public Agent[] getAgents() {

        return this.vertices;
    }

    @Override
    public String toString() {

        StringBuilder graph = new StringBuilder();

        for(Agent agent : this.vertices)
            graph.append(agent.id() + " " + agent.friendsIds() + "\n");

        return graph.toString();
    }
}
