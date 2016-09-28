package tt.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TtTestApplication {

	public static void main(String[] args) {
		//		System.setProperty("com.sun.management.jmxremote.port", "9999");
		//		System.setProperty("com.sun.management.jmxremote.ssl", "false");
		//		System.setProperty("com.sun.management.jmxremote.authenticate", "false");
		System.out.println("START SPRING");
		SpringApplication.run(TtTestApplication.class, args);
	}
}
