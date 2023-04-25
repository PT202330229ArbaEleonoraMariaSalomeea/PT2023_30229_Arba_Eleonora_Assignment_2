package BusinessLogic;

import model.Client;
import model.Server;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class SimulationManager implements Runnable {
    private int numberOfQueues;
    private int numberOfClients;
    private int lowArrival;
    private int highArrival;
    private int lowService;
    private int highService;
    Scheduler scheduler;
    private int timeLimit;
    private SelectionPolicy strategyPolicy = SelectionPolicy.SHORTEST_QUEUE;
    private ArrayList<Client> clientsList = new ArrayList<>();
    private AtomicInteger peak = new AtomicInteger(0);
    private AtomicInteger nrAux = new AtomicInteger(0);
    private StringBuilder rezultat = new StringBuilder();
    public SimulationManager(int numberOfQueues, int numberOfClients, int lowArrival, int highArrival, int lowService, int highService, int timeLimit) {
        this.numberOfQueues = numberOfQueues;
        this.numberOfClients = numberOfClients;
        this.lowArrival = lowArrival;
        this.highArrival = highArrival;
        this.lowService = lowService;
        this.highService = highService;
        this.timeLimit=timeLimit;
        scheduler = new Scheduler(numberOfQueues);
        clientsList = new ArrayList<>();
        scheduler.changeStrategy(strategyPolicy);
        generateRandomTasks();

    }

    public int getNumberOfQueues() {
        return numberOfQueues;
    }

    public void setNumberOfQueues(int numberOfQueues) {
        this.numberOfQueues = numberOfQueues;
    }

    public int getNumberOfClients() {
        return numberOfClients;
    }

    public void setNumberOfClients(int numberOfClients) {
        this.numberOfClients = numberOfClients;
    }

    public int getLowArrival() {
        return lowArrival;
    }

    public void setLowArrival(int lowArrival) {
        this.lowArrival = lowArrival;
    }

    public int getHighArrival() {
        return highArrival;
    }

    public void setHighArrival(int highArrival) {
        this.highArrival = highArrival;
    }

    public int getLowService() {
        return lowService;
    }

    public void setLowService(int lowService) {
        this.lowService = lowService;
    }

    public int getHighService() {
        return highService;
    }

    public void setHighService(int highService) {
        this.highService = highService;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


    public void generateRandomTasks(){
        for(int i=1; i<=numberOfClients; i++){
            Client newClient= new Client(i,lowArrival,highArrival,lowService,highService);
            clientsList.add(newClient);
        }

        clientsList.sort(new Comparator<Client>() {
            @Override
            public int compare(Client o1, Client o2) {
                if(o1.getArrivalTime()> o2.getArrivalTime())
                    return 1;
                else if(o1.getArrivalTime()< o2.getArrivalTime())
                    return -1;
                else
                return 0;
            }
        });

        //ArrayList<Server> servers = scheduler.getStore();

      //  servers.get(0).addClient(clientsList.get(1));
        //servers.get(1).addClient(clientsList.get(2));
        //servers.get(0).addClient(clientsList.get(3));
    }

    @Override
    public void run() {
        float averageWaitingTime=0;
        float averageServiceTime=0;
        ArrayList<Client> clientToDelete= new ArrayList<>();
        int currentTime = 0;
        while (currentTime < timeLimit) {
            for (Iterator<Client> iterator = clientsList.iterator(); iterator.hasNext(); ) {
                Client client = iterator.next();
                if (client.getArrivalTime() == currentTime) {
                    averageServiceTime+=client.getServiceTime();
                    scheduler.dispatchTask(client);
                    clientToDelete.add(client);
                    iterator.remove();
                }
            }
           // System.out.println("\n" +"TIME" + currentTime + "\n");
              rezultat.append('\n').append("TIME").append(currentTime).append('\n');

            for (int i = 1; i <= numberOfQueues; i++) {
                rezultat.append("Queue ").append(i).append(":").append('\n');
                if (scheduler.getStore().get(i - 1).getQueue().isEmpty())
                   rezultat.append("closed").append('\n');
                else
                    for (Client task : scheduler.getStore().get(i - 1).getQueue()) {
                       rezultat.append(task).append(",").append('\n');
                    }


            }
            for(Client  clintDelete: clientToDelete){
                clientsList.remove(clintDelete);
            }
            //System.out.println("\nWaiting clients: ");
            rezultat.append('\n').append("Waiting clients: ");
            for(Client client: clientsList){
               rezultat.append(client).append('\n');
            }

           int aux=0;
           for(Server s: scheduler.getStore()){
               aux+=s.getQueue().size();
               }
            if(aux>nrAux.get()){
                nrAux.set(aux);
                peak.set(currentTime);
            }

            currentTime++;
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }


            for(Server s: scheduler.getStore()){
                averageWaitingTime+=s.getAvgWaitingTime().get();
            }

        rezultat.append('\n').append("Average service time: ").append((float) averageServiceTime / numberOfClients).append('\n');
        rezultat.append("Average waiting time: ").append((float) averageWaitingTime / numberOfClients).append('\n');
        rezultat.append("Peak time:").append(peak.get()).append('\n');
//
//        try {
//            FileWriter fileWriter = new FileWriter("test3.txt");
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//
//            bufferedWriter.write(String.valueOf(rezultat));
//            bufferedWriter.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        // System.out.println(rezultat);
    }

    public StringBuilder getRezultat() {

        return rezultat;
    }
}
