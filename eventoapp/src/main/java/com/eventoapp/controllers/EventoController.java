package com.eventoapp.controllers;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class EventoController {

	@RequestMapping("/cadastrarEvento")
	public String form(){
		return "evento/formEvento.html";
	}
}
