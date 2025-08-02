package twitter.runner.impl;

import twitter.exception.ClientDisconnectedException;
import twitter.exception.UnknowCommandException;

import twitter.factory.CommandFactory;
import twitter.factory.CommandFactoryBuilder;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class TelnetClientHandler implements Runnable {

    private final Socket clientSocket;
    private final CommandFactoryBuilder commandFactoryBuilder;

    public TelnetClientHandler(final Socket clientSocket, final CommandFactoryBuilder commandFactoryBuilder) {
        this.clientSocket = clientSocket;
        this.commandFactoryBuilder = commandFactoryBuilder;
    }

    @Override
    public void run() {
        String command = "";
        String clientId = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
        System.out.println("Новый клиент подключен: " + clientId);
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            CommandFactory commandFactory = commandFactoryBuilder.buildCommandFactoryForUser(clientId, reader, writer);
            while (true){
                try {
                    writer.append("Для получения помощи по командам используйте команду help.").append("\n");
                    writer.append("Введите команду: ");
                    writer.flush();
                    command = reader.readLine();
                    commandFactory.getCommandHandler(command).handleCommand();
                } catch (UnknowCommandException ex) {
                    try {
                        writer.write("Команда не распознана. Проверьте список доступных команд и попробуйте снова.\n");
                        writer.flush();
                    } catch (IOException ex1) {
                        System.out.println(ex.getMessage());
                    }
                } catch (ClientDisconnectedException ex) {
                    System.out.println("Клиент с IP " + clientId + " отключился.");
                    return;
                }
            }
        } catch (IOException ex) {
            System.out.println("Произошла ошибка: " + ex.getMessage());
        } finally {
            try {
                if (Objects.nonNull(reader)) {
                    reader.close();
                }
                if (Objects.nonNull(writer)) {
                    writer.close();
                }
                clientSocket.close();
            } catch (IOException ex) {
                System.out.println("Ошибка при закрытии соединения: " + ex.getMessage());
            }
        }
    }
}
