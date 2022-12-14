package cutiedev.webserver.web.model;

import cutiedev.webserver.web.exceptions.ConnectionClosedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.SocketException;

public class RestResponse
{
    private OutputStream outputStream;
    public RestResponse(OutputStream stream) throws ConnectionClosedException {

        try {
            this.outputStream = stream;
            setHeader();
            setBody();
        }
        catch (SocketException e)
        {
            throw new ConnectionClosedException();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void setHeader() throws IOException, ConnectionClosedException {
        try {
            this.outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
            //this.outputStream.write("Main: OneServer 0.1\r\n".getBytes());
            this.outputStream.write("Content-Length: 22\r\n".getBytes()); // if text/plain the length is required
            this.outputStream.write("Content-Type: text/plain\r\n".getBytes());
            //this.outputStream.write("Connection: close\r\n".getBytes());

            this.outputStream.write("\r\n".getBytes());
        } catch (SocketException exception)
        {
            throw new ConnectionClosedException();
        }
    }
    private void setBody() throws IOException, ConnectionClosedException {

        BufferedReader bufferedReader = new BufferedReader(new StringReader("A Message from server."));
        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                outputStream.write(line.getBytes());
                outputStream.write("\r\n".getBytes());
            }

        }
        catch (SocketException exception)
        {
            throw new ConnectionClosedException();
        }
        bufferedReader.close();
    }
    public void sendResponse()
    {
        try {
            this.outputStream.flush();
            this.outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
