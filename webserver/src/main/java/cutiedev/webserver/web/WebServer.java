package cutiedev.webserver.web;

import cutiedev.webserver.web.controller.Controller;
import cutiedev.webserver.web.data.DataType;
import cutiedev.webserver.web.exceptions.ConnectionClosedException;
import cutiedev.webserver.web.model.RestRequest;
import cutiedev.webserver.web.model.RestResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Set;

public class WebServer
{
    private ServerSocket serverSocket;
    private List<Controller> controllers;
    private int port;
    private boolean serverRun;

    public WebServer(ServerBuilder webServerBuilder)
    {
        this.port = webServerBuilder.getPort();
    }

    public void startServer() throws IOException
    {
        serverSocket = new ServerSocket(port);
        serverRun =true;
        try {
            while (serverRun) {

                Socket socket = serverSocket.accept();

                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream =socket.getOutputStream();

                RestRequest request = new RestRequest(inputStream);

                if(request.isEmpty())
                {
                    socket.close();
                    continue;
                }
                try {
                    RestResponse response = new RestResponse(outputStream);
                    response.sendResponse();
                    response.sendResponse();
                }
                catch (ConnectionClosedException exception)
                {
                    System.out.println(exception.getMessage());
                    socket.close();
                }
                request.close();
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
}
