package com.eventoapp.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventoController {

	@RequestMapping(value="/cadastrarEvento")
	public String form(){
		return "evento/formEvento";
	}
}
