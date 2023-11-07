package Francesco.Iscrizioni.Pugilistica.Fabrianese.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Francesco.Iscrizioni.Pugilistica.Fabrianese.service.WhatsappService;

@CrossOrigin(origins = "https://iscrizioni-palestra-front-end.vercel.app")
@RestController
@RequestMapping("/whatsapp")
public class WhatsAppController {

	private final WhatsappService whatsappService;

	public WhatsAppController(WhatsappService whatsappService) {
		this.whatsappService = whatsappService;
	}

	@PostMapping("/send")
	public ResponseEntity<String> sendWhatsAppMessage(@RequestParam String to, @RequestParam String message) {
		whatsappService.sendWhatsappMessage(to, message);
		return ResponseEntity.ok("Messaggio inviato con successo");
	}
}
