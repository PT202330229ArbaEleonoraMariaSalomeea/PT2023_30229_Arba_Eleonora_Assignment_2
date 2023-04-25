package BusinessLogic;

import model.Client;
import model.Server;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeStrategy implements Strategy{
    @Override
    public void addTask(List<Server> servers, Client client) {
        AtomicInteger minim= new AtomicInteger(2147483647);
        Server correctServer = null;

        for(Server server:  servers){
             if ( server.getWaitingTime().get() < minim.get()){
                    minim.set(server.getWaitingTime().get());
                    correctServer=server;
             }
             correctServer.addClient(client);
        }


    }

}
