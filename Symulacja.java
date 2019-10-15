public class Symulacja {

    public static void main(String[] args) {

        Simulation simulation = new Parser().loadData();

        simulation.getWriter().println("# twoje wyniki powinny zawierać te komentarze");

        simulation.getWriter().println(simulation.toString());

        simulation.setAgentGraph(new Graph(simulation.createAgents(), simulation.getAverageFriendsNumber(), simulation.getRand()));

        simulation.getWriter().println("# agenci jako: id typ lub id* typ dla chorego");

        for (int i = 0; i < simulation.getNumberOfAgents(); i++)
            simulation.getWriter().println(simulation.getAgentGraph().getAgent(i + 1).toString());

        simulation.getWriter().println("\n# graf");

        simulation.getWriter().print(simulation.getAgentGraph().toString());

        simulation.getWriter().println("\n# liczność w kolejnych dniach");

        simulation.simulateEpidemy();

        simulation.getWriter().close();
    }
}
