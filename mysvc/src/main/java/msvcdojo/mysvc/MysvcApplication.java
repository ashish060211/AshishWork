package msvcdojo.mysvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class MysvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysvcApplication.class, args);
	}

}

// http://localhost:8100/
@RestController
class HomeController {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }
}