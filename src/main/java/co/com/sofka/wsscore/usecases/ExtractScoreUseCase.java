package co.com.sofka.wsscore.usecases;

import co.com.sofka.wsscore.domain.generic.DomainEvent;
import co.com.sofka.wsscore.domain.generic.EventStoreRepository;
import co.com.sofka.wsscore.domain.program.command.AssignScoreCommand;
import co.com.sofka.wsscore.domain.program.Program;
import co.com.sofka.wsscore.infra.DataResponse;
import com.google.gson.Gson;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Dependent
public class ExtractScoreUseCase implements Function<AssignScoreCommand, List<DomainEvent>> {
    private static final String URL_BASE = "https://campus.sofka.com.co";
    private final ProcessLogin processLogin;
    private final EventStoreRepository eventStoreRepository;

    public ExtractScoreUseCase(ProcessLogin processLogin, EventStoreRepository eventStoreRepository){
        this.processLogin = processLogin;
        this.eventStoreRepository = eventStoreRepository;
    }

    @Override
    public List<DomainEvent> apply(AssignScoreCommand command) {
        processLogin.login();
        var program = Program.from(command.getProgramId(),
                eventStoreRepository.getEventsBy("program", command.getProgramId())
        );
        try {
            Connection.Response response = getResponse(command.getPath());
            processLogin.logout();
            new Gson().fromJson(response.body(), DataResponse.class).getData().stream()
                    .filter(d -> d.get(5).contains("Terminado"))
                    .forEach(data -> program.assignScore(html2text(data.get(0)), data.get(6), new Date()));
            return program.getUncommittedChanges();
        } catch (IOException e) {
           throw new ExtractScoreException();
        }
    }

    private Connection.Response getResponse(String path) throws IOException {
        return Jsoup.connect(URL_BASE+"/reports/"+path)
                        .userAgent("Mozilla/5.0")
                        .timeout(10 * 1000)
                        .cookies(processLogin.cookies())
                        .method(Connection.Method.POST)
                        .followRedirects(true)
                        .execute();
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}
