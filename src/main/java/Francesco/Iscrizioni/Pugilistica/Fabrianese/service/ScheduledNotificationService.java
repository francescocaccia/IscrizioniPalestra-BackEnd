package Francesco.Iscrizioni.Pugilistica.Fabrianese.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import Francesco.Iscrizioni.Pugilistica.Fabrianese.entities.Abbonamento;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.entities.Atleta;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.entities.VisitaMedica;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.repository.AbbonamentoRepository;
import Francesco.Iscrizioni.Pugilistica.Fabrianese.repository.VisitaMedicaRepository;

@Service
public class ScheduledNotificationService {

	@Autowired
	private AbbonamentoRepository abbonamentoRepository;

	@Autowired
	private VisitaMedicaRepository visitaMedicaRepository; // Assumendo che tu abbia questa repository.

	@Autowired
	private WhatsappService whatsappService;

	// Questo metodo verrà eseguito ogni giorno alle 8 AM
	@Scheduled(cron = "0 * * * * ?")
	public void sendExpirationAlerts() {
		LocalDate today = LocalDate.now();
		LocalDate inUnMese = today.plusMonths(1);
		List<Abbonamento> abbonamentiInScadenza = abbonamentoRepository.findAllByDataScadenzaBefore(today.plusDays(1));
		for (Abbonamento abbonamento : abbonamentiInScadenza) {
			String messageBody = "L'abbonamento di " + abbonamento.getAtleta().getNome() + " sta per scadere.";
			whatsappService.sendWhatsappMessage("whatsapp:" + abbonamento.getAtleta().getTelefono(), messageBody);
		}

		// Logica per le visite mediche in scadenza
		List<VisitaMedica> visiteMedicheInScadenza = visitaMedicaRepository.findAllByDataScadenzaBetween(inUnMese,
				inUnMese.plusDays(1));
		for (VisitaMedica visitaMedica : visiteMedicheInScadenza) {
			Atleta atleta = visitaMedica.getAtleta();
			String messageBody = "La visita medica di " + atleta.getNome() + " scadrà il "
					+ visitaMedica.getDataScadenzaVisitaMedica() + ". Si prega di rinnovarla.";
			whatsappService.sendWhatsappMessage("whatsapp:" + atleta.getTelefono(), messageBody);
		}
	}
}
