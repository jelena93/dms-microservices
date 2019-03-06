package process.startup;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import process.domain.Process;
import process.service.ProcessService;

@Component
public class ApplicationStartup implements InitializingBean {

    private final ProcessService processService;

    @Autowired
    public ApplicationStartup(ProcessService processService) {
        this.processService = processService;
    }

    @Override
    public void afterPropertiesSet() {
        Process prodaja = new Process(1, "Prodaja", null, false, 1);
        Process nabavka = new Process(2, "Nabavka", null, false, 1);
        Process skladistenje = new Process(3, "Skladistenje i otprema", null, false, 1);
        Process finansije = new Process(4, "Regulisanje finansija", null, false, 1);

        prodaja = processService.save(prodaja);
        nabavka = processService.save(nabavka);
        skladistenje = processService.save(skladistenje);
        finansije = processService.save(finansije);

        Process katalog = new Process(5, "Formiranje i slanje kataloga", prodaja, true, 1);
        processService.save(katalog);

        Process narudzbenica = new Process(6, "Prijem narudzbenice", prodaja, false, 1);
        processService.save(narudzbenica);

        Process otprema = new Process(7, "Formiranje naloga za otpremu", prodaja, false, 1);
        processService.save(otprema);

        Process profaktura = new Process(8, "Formiranje profakture", prodaja, true, 1);
        processService.save(profaktura);

        Process katalogP = new Process(9, "Prijem kataloga", nabavka, false, 1);
        processService.save(katalogP);

        Process narucivanje = new Process(10, "Narucivanje", nabavka, false, 1);
        processService.save(narucivanje);

        Process profakturaP = new Process(11, "Prijem profakture", nabavka, true, 1);
        processService.save(profakturaP);

        Process zalihe = new Process(12, "Izvestavanje o stanju na zalihama", skladistenje, true, 1);
        processService.save(zalihe);

        Process otpremanje = new Process(13, "Otpremanje robe", skladistenje, true, 1);
        processService.save(otpremanje);

        Process prijem = new Process(14, "Projem robe", skladistenje, true, 1);
        processService.save(prijem);

        Process nalogZaNabavku = new Process(15, "Formiranje naloga za nabavku", skladistenje, false, 1);
        processService.save(nalogZaNabavku);

        Process obrada = new Process(16, "Obrada izvoda stanja na racunu", finansije, true, 1);
        processService.save(obrada);

        Process uplata = new Process(17, "Uplate profakture dobavljaca", finansije, true, 1);
        processService.save(uplata);
    }
}
