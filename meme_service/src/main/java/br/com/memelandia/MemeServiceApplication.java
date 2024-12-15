package br.com.memelandia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {
    "br.com.memelandia.entities",   
    "br.com.memelandia.repositori", 
    "br.com.memelandia.service" 
})
public class MemeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemeServiceApplication.class, args);
	}

}
