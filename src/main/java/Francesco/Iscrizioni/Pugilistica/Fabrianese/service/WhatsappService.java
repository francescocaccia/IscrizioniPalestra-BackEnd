package Francesco.Iscrizioni.Pugilistica.Fabrianese.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class WhatsappService {

	@Value("${twilio.account.sid}")
	private String accountSid;

	@Value("${twilio.auth.token}")
	private String authToken;

	private final String fromWhatsAppNumber = "whatsapp:+393207010150";

	public void sendWhatsappMessage(String toWhatsAppNumber, String messageBody) {
		Twilio.init(accountSid, authToken);
		Message message = Message.creator(new PhoneNumber("whatsapp:" + toWhatsAppNumber), // to WhatsApp number
				new PhoneNumber(fromWhatsAppNumber), // from WhatsApp number
				messageBody).create();

		System.out.println(message.getSid());
	}
}
