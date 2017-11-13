package rav;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rav.util.BrowserCommandsSender;

@SpringBootApplication
public class App implements CommandLineRunner
{
    @Value("${my.server.port}")
    private String port;
    @Value("${my.index.url}")
    private String indexUrl;

    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        BrowserCommandsSender.openUrl("http://localhost:" + port + "/" + indexUrl);
    }
}
