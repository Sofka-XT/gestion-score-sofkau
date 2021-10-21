package co.com.sofka.wsscore.usecases;

import co.com.sofka.wsscore.domain.Score;
import co.com.sofka.wsscore.infra.DataResponse;
import com.google.gson.Gson;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
public class ExtractScoreUseCase implements Function<String, List<Score>> {
    private static final String URL_BASE = "https://campus.sofka.com.co";
    private final ProcessLogin processLogin;


    public ExtractScoreUseCase(ProcessLogin processLogin){
        this.processLogin = processLogin;
    }

    @Override
    public List<Score> apply(String pathIdentity) {
        processLogin.login();
        try {
            Connection.Response response =
                    Jsoup.connect(URL_BASE+"/reports/"+pathIdentity+",group:,branch:,completion_status:")
                            .userAgent("Mozilla/5.0")
                            .timeout(10 * 1000)
                            .cookies(processLogin.cookies())
                            .method(Connection.Method.POST)
                            .followRedirects(true)
                            .execute();
            processLogin.logout();
           return new Gson().fromJson(response.body(), DataResponse.class).getData().stream()
                    .filter(d -> d.get(5).contains("Terminado"))
                    .map(d -> new Score(html2text(d.get(0)), d.get(6)))
                   .collect(Collectors.toList());
        } catch (IOException e) {
           throw new ExtractScoreException();
        }
    }

    public static String html2text(String html) {
        return Jsoup.parse(html).text();
    }
}
