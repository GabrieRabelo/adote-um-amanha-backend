package br.com.ages.adoteumamanha.utils;

import com.mailjet.client.MailjetRequest;
import com.mailjet.client.resource.Emailv31;
import org.json.JSONArray;
import org.json.JSONObject;

public class MailMessage {
    public static MailjetRequest build(String to, String subject, String body) {
        return new MailjetRequest(Emailv31.resource)
            .property(Emailv31.MESSAGES, new JSONArray()
                .put(new JSONObject()
                    .put(Emailv31.Message.FROM, new JSONObject()
                            .put("Email", "adoteumamanha@gmail.com")
                            .put("Name", "adote"))
                    .put(Emailv31.Message.TO, new JSONArray()
                            .put(new JSONObject()
                                    .put("Email", to)
                                    .put("Name", "recipient")))
                    .put(Emailv31.Message.SUBJECT, subject)
                    .put(Emailv31.Message.TEXTPART, "adote um amanha info")
                    .put(Emailv31.Message.HTMLPART, body)
                    .put(Emailv31.Message.CUSTOMID, "AppGettingStartedTest")
                )
            );
    }
}
