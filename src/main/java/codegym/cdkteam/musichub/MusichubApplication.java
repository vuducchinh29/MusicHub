package codegym.cdkteam.musichub;

import codegym.cdkteam.musichub.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MusichubApplication implements CommandLineRunner {
	@Autowired
	private StorageService storageService;
	public static void main(String[] args) {
		SpringApplication.run(MusichubApplication.class, args);
	}
	@Override
	public void run(String... arg) throws Exception {
		storageService.init();
	}
}
