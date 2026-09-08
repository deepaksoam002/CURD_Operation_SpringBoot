package in.deepak.springBootCurdDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringBootCurdDemoApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootCurdDemoApplication.class, args);

		System.out.println("hello world");
	}

}
