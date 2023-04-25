package BusinessLogic;

import model.Client;
import model.Server;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {
    @Override
    public void addTask(List<Server> servers, Client client) {
        int minim=2147483647;
        Server correctServer = null;
        for(Server server:  servers){
               if(minim > server.getQueue().size()) {
                   minim = server.getQueue().size();
                   correctServer=server;

               }

            }
        correctServer.addClient(client);
    }
}
