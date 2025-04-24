package com.microservicios.matriculas;

import com.microservicios.matriculas.Model.Matricula;
import com.microservicios.matriculas.repository.MatriculaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.microservicios.matriculas.client")
@EnableJpaRepositories(basePackages = "com.microservicios.matriculas.repository")
public class MatriculasServiciosApplication {
	private final MatriculaRepository matriculaRepository;
	public MatriculasServiciosApplication(MatriculaRepository matriculaRepository) {
		this.matriculaRepository = matriculaRepository;
	}
	public static void main(String[] args) {
		SpringApplication.run(MatriculasServiciosApplication.class, args);
	}
}