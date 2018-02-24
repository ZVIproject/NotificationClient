package com.notification.client.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.client.services.LoggerServiceImpl;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.execchain.RequestAbortedException;

import java.io.IOException;
import java.net.URI;

public class PostRequestSender {

    private static final LoggerServiceImpl logger = new LoggerServiceImpl();

    private HttpClient httpClient;
    private ObjectMapper objectMapper;

    public PostRequestSender() {
        httpClient = HttpClients.createDefault();
        objectMapper = new ObjectMapper();
    }

    /**
     * Create and send HTTP POST request with application/json content type.
     * @param data - the data for JSON encode
     * @param uri - URI resource to which request will be sent
     * @throws java.io.IOException
     */
    public <E> void send(E data, URI uri) throws IOException {
        logger.logInfo("Sending data to " + uri.toString());
        String json = objectMapper.writeValueAsString(data);
        logger.logInfo("Data: " + json);
        HttpPost postRequest = new HttpPost(uri);
        postRequest.setEntity(new StringEntity(json));
        postRequest.setHeader("Content-type", "application/json");
        int status;
        if ((status = httpClient.execute(postRequest).getStatusLine().getStatusCode()) != 200) {
            logger.logError(
                    new RequestAbortedException("Incorrect URI"),
                    "Incorrect URI. Status " + status);
        }
    }

}
