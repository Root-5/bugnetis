package a.shshelokov.Master;

import a.shshelokov.Message.Message;
import a.shshelokov.Message.MessageType;
import a.shshelokov.Packet;
import a.shshelokov.TreeNode;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class InputMaster implements Runnable {
    Scanner inputReader;
    TreeNode node;

    public InputMaster(TreeNode node) {
        this.node = node;
        inputReader = new Scanner(System.in);
    }

    @Override
    public void run() {
        ConcurrentLinkedQueue<InetSocketAddress> children = node.getChildren();
        ConcurrentLinkedQueue<Packet> packetsToSend = node.getPacketsToSend();
        while (true) {
            String  input = inputReader.nextLine();
            Message msg;
           //// System.out.println("(from this node)" + msg.getGUID() + "///" + msg.getMessageType() + "///" + msg.getName() +": " +  msg.getMessageText());
            for (InetSocketAddress sendAddr : children) {
                msg = new Message(MessageType.CHAT_MESSAGE, node.getName(), input, UUID.randomUUID());

                packetsToSend.add(new Packet(sendAddr, msg,Packet.CHAT_MESSAGE_TTL));
            }
            if(node.hasParent()){
                msg = new Message(MessageType.CHAT_MESSAGE, node.getName(), input, UUID.randomUUID());
                packetsToSend.add(new Packet(node.getParent(),msg,Packet.CHAT_MESSAGE_TTL));
            }
        }


    }
}
