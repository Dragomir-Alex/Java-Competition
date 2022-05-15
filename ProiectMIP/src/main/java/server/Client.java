package server;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static final int PORT = 6543;

    public void start() throws Exception {
        Socket socket = null;
        boolean isClose = false;
        socket = new Socket("localhost", PORT);

        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

        Scanner scanner = new Scanner(System.in);

        while (!isClose) {
            String message = scanner.nextLine();
            Packet packet = new Packet(message);
            outputStream.writeObject(packet);

            Packet receivePacket = (Packet) inputStream.readObject();
            System.out.println(receivePacket.message);
        }

        socket.close();
    }
}