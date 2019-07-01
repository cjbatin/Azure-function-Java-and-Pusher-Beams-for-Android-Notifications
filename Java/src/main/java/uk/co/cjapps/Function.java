package uk.co.cjapps;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.pusher.pushnotifications.PushNotifications;
import com.microsoft.azure.functions.*;

public class Function {
    @FunctionName("HttpTrigger-Java")
    public HttpResponseMessage run(@HttpTrigger(name = "req", methods = { HttpMethod.GET,
            HttpMethod.POST }, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<Notification>> request,
            final ExecutionContext context) {
        String instanceId = "YOUR_INSTANCE_ID";
        String secretKey = "YOUR_SECRET_KEY";
        final Notification body = request.getBody().get();
        if (body == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
            .body("Please pass a string or in the request body").build();
        }
        PushNotifications beamsClient = new PushNotifications(instanceId, secretKey);
        List<String> interests = Arrays.asList("hello");

        Map<String, Map> publishRequest = new HashMap();
        Map<String, String> fcmNotification = new HashMap();
        fcmNotification.put("title", body.getTitle());
        fcmNotification.put("body", body.getMessage());
        Map<String, Map> fcm = new HashMap();
        fcm.put("notification", fcmNotification);
        publishRequest.put("fcm", fcm);

        try {
            beamsClient.publishToInterests(interests, publishRequest);
            return request.createResponseBuilder(HttpStatus.OK).body("Push Sent").build();
        } catch (IOException e) {
            e.printStackTrace();
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Push Failed").build();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Push Failed").build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Push Failed").build();
        }
    }
}
