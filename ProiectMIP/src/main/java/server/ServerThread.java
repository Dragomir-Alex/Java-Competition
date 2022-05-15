package server;
import database.dao.EtapaDao;
import database.dao.ParticipaDao;
import database.dao.PersoanaDao;
import database.model.PersoanaEntity;
import database.model.enums.UserType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread{
    ClientData clientData = new ClientData();
    private Socket socket = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
        try {
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                try {
                    Packet recivePacket = (Packet) this.in.readObject();
                    System.out.println("Recived: " + recivePacket.message);
                    execute(recivePacket.message);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void execute(String message) {
        Packet packet = null;
        String[] messageSplit = message.split(" ", 2);
        PersoanaDao persoanaDao = new PersoanaDao();
        EtapaDao etapaDao = new EtapaDao();
        switch (clientData.clientStatus) {
            case NONE:
                switch (messageSplit[0]){
                    case "LOGIN":
                        if (messageSplit.length == 1) {
                            packet = new Packet("Login failed!");
                            break;
                        }
                        PersoanaEntity newPersoanaEntity = new PersoanaEntity();
                        newPersoanaEntity = persoanaDao.getByUsername(messageSplit[1]);
                        if (newPersoanaEntity != null) {
                            clientData.clientEntity = newPersoanaEntity;
                            if (clientData.clientEntity.getRol() == UserType.USER) {
                                clientData.clientStatus = ClientData.ClientStatus.LOGGED_USER;
                                packet = new Packet("You're logged in!\nType 'ADD {etapa} {punctaj}' to add your score.\nType 'LEADERBOARD {etapa}' to view the stage leaderboard.\nType 'LEADERBOARD_TEAMS {etapa}' to view the stage leaderboard for teams.\nType 'EXIT' to close the program.");
                            }
                            else {
                                clientData.clientStatus = ClientData.ClientStatus.LOGGED_ADMIN;
                                packet = new Packet("You're logged in! (You have administrator priviledges)\nType 'ADD_STAGE {etapa} {punctaj}' to add a new stage.\nType 'LEADERBOARD {etapa}' to view the stage leaderboard.\nType 'LEADERBOARD_TEAMS {etapa}' to view the stage leaderboard for teams.\nType 'EXIT' to close the program.");
                            }
                        }
                        else packet = new Packet("Login failed!");
                        break;
                    case "REGISTER":
                        if (messageSplit.length == 1) {
                            packet = new Packet("Invalid registration data.");
                            break;
                        }
                        String[] registerSplit = messageSplit[1].split(" ", 3);
                        if (registerSplit.length != 3) {
                            packet = new Packet("Invalid registration data.");
                        }
                        else {
                            if (persoanaDao.createByParameters(registerSplit[0], registerSplit[1], registerSplit[2]))
                                packet = new Packet("Registered successfully!");
                            else packet = new Packet("Registration faiiled!");
                        }

                        break;
                    case "EXIT":
                        packet = new Packet("Bye!");
                        break;
                    default:
                        packet = new Packet("Unknown command.");
                }
                break;
            case LOGGED_ADMIN:
            case LOGGED_USER:
                switch (messageSplit[0]) {
                    case "ADD":
                        if (messageSplit.length == 1) {
                            packet = new Packet("Bad command parameters.");
                            break;
                        }
                        String[] addSplit = messageSplit[1].split(" ", 2);
                        if (addSplit.length != 2) {
                            packet = new Packet("Bad command parameters.");
                            break;
                        }
                        if (!isNumeric(addSplit[1])) {
                            packet = new Packet("Bad command parameters.");
                            break;
                        }
                        ParticipaDao participaDao = new ParticipaDao();
                        if (participaDao.createByParameters(addSplit[0], clientData.clientEntity.getIdPersoana(), Integer.parseInt(addSplit[1])))
                            packet = new Packet("Score added successfully!");
                        else packet = new Packet("Score couldn't be added.");

                        break;
                    case "LEADERBOARD":
                        if (messageSplit.length == 1) {
                            packet = new Packet("Bad command parameters.");
                            break;
                        }
                        packet = new Packet(etapaDao.printLeaderboard(messageSplit[1]));
                        break;
                    case "LEADERBOARD_TEAMS":
                        if (messageSplit.length == 1) {
                            packet = new Packet("Bad command parameters.");
                            break;
                        }
                        packet = new Packet(etapaDao.printLeaderboardTeams(messageSplit[1]));
                        break;
                    case "EXIT":
                        packet = new Packet("Bye!");
                        break;
                    default:
                        packet = new Packet("Unknown command.");
                }
                break;
        }

        try {
            this.out.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}