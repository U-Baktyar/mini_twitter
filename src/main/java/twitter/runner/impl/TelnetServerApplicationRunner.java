package twitter.runner.impl;

import twitter.configuration.Component;
import twitter.configuration.Injection;
import twitter.configuration.Value;
import twitter.factory.CommandFactoryBuilder;
import twitter.runner.ApplicationRunner;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TelnetServerApplicationRunner implements ApplicationRunner {

    @Value(kay =  "application.port")
    private Integer port;

    @Value(kay = "application.max_connections")
    private Integer maxConnections;


    private ExecutorService threadPool;
    private volatile boolean running;

    private final CommandFactoryBuilder commandFactoryBuilder;

    @Injection
    public TelnetServerApplicationRunner(CommandFactoryBuilder commandFactoryBuilder) {
        this.commandFactoryBuilder = commandFactoryBuilder;
    }

    @Override
    public void run() {
        this.threadPool = Executors.newFixedThreadPool(this.maxConnections);
        new Thread(() -> {
            running = true;
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("Телнет-сервер запущен на порту " + port);

                while (running) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        Runnable clientHandling = new TelnetClientHandler(clientSocket, commandFactoryBuilder);
                        threadPool.execute(clientHandling);
                    } catch (IOException e) {
                        if (!running) {
                            System.out.println("Сервер остановлен.");
                            break;
                        }
                        System.err.println("Ошибка при подключении клиента: " + e.getMessage());
                    }
                }
            } catch (IOException e) {
                System.err.println("Ошибка при запуске Телнет-сервера: " + e.getMessage());
            } finally {
                threadPool.shutdown();
            }
        }).start();
    }
}
