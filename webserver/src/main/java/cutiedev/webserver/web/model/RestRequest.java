package cutiedev.webserver.web.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RestRequest
{

    private HTTPMethod HTTPMethod;
    private String path;
    private Protocol protocol;

    private List<HttpInfo> infos;
    private String payload;

    private BufferedReader inBufferReader;

    public RestRequest(InputStream stream)
    {
        try {
            setUp(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isEmpty()
    {
        if(HTTPMethod == null)
            return true;
        if(path == null || path.trim().isEmpty())
            return true;
        if(infos.isEmpty())
            return true;

        return false;
    }

    private void setUp(InputStream stream) throws IOException {

        this.inBufferReader = new BufferedReader(new InputStreamReader(stream));

        String line = this.inBufferReader.readLine();
        if(line == null)
            return;
        setUpRequestTypeInfo(line);
        setUpInfo(inBufferReader);
        setUpPayload(inBufferReader);

    }

    private void setUpRequestTypeInfo(String line)
    {
        String[] splited = line.split("\\s+");

        this.HTTPMethod =  HTTPMethod.valueOf(splited[0]);
        this.protocol = new Protocol(splited[2]);
        this.path = splited[1];
    }

    private void setUpInfo(BufferedReader inBufferReader) throws IOException {
        if(this.infos == null)
            this.infos = new ArrayList<>();
        String inputLine;

        while ((inputLine = inBufferReader.readLine()) != null && !inputLine.equals(""))
        {
            String[] info = inputLine.split(":");

            this.infos.add(new HttpInfo(info[0], info[1]));
        }

    }

    private void setUpPayload(BufferedReader inBufferReader) throws IOException {
        StringBuilder payload = new StringBuilder();
        while(inBufferReader.ready()){
            payload.append((char) inBufferReader.read());
        }

        this.payload = payload.toString();
    }

    public void close()
    {
        try {
            this.inBufferReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
