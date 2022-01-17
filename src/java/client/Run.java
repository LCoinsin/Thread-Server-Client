package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import utils.MapperMessage;
import utils.Message;
import org.apache.commons.lang3.RandomStringUtils;

public class Run {

    static MapperMessage mapperMessage;
    static String userId;
    static String groupeId;
    static Scanner scanner = new Scanner(System.in);
    static PrintWriter out;

    /***************************************/

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 5000)) {
            BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            mapperMessage = new MapperMessage();
            ClientThread clientThread = new ClientThread(socket);
            clientThread.start();

            userId = userIdConnection();
            out.println(mapperMessage.objectToJson(new Message(userId, 0, "Auth")));

            String event = "";
            do {
                event = scanner.nextLine();
                if (event.equals("exit"))
                    break;
                choiveEvent(event);
            } while (!event.equals("exit"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***************************************/

    public static void choiveEvent(String event) {
        switch (event) {
            case "message":
                System.out.print("Text : ");
                String toSend = scanner.nextLine();
                out.println(mapperMessage.objectToJson(new Message(userId, 1, userId, "String", toSend)));
                //out.println(mapperMessage.objectToJson(new Message(userId, new Cast(0), new MString("test", toSend))));
                break;
            case "broadcast":
                break;
            case "multicast":
                System.out.println("Message : ");
                String ms = scanner.nextLine();
                out.println(mapperMessage.objectToJson(new Message(userId, 2, groupeId, "multicastTest", ms)));
                break;
            case "updateGroupe":
                System.out.println("groupe : ");
                String group = scanner.nextLine();
                groupeId = group;
                out.println(mapperMessage.objectToJson(new Message(userId, 0, userId, "updateGroupId", group)));
                break;
            default:
                System.out.println("Type inconnu");
                break;
        }
    }

    /***************************************/

    public static Message stringToMessage(String jsonString) {
        return mapperMessage.jsonToObject(jsonString);
    }

    /***************************************/

    public static String userIdConnection() {
        int length = 25;
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
}