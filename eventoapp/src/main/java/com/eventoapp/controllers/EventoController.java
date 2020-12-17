package com.eventoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eventoapp.models.Evento;
import com.eventoapp.repository.EventoRepository;

@SpringBootApplication
@Controller
public class EventoController {

	@Autowired
	private EventoRepository er;
	
	@RequestMapping(value = "/cadastrarEvento", method=RequestMethod.GET)
	public String form(){
		return "evento/formEvento";
	}
	
	@RequestMapping(value = "/cadastrarEvento", method=RequestMethod.POST)
	public String form(Evento evento) {
		er.save(evento);
		
		return "redirect:/cadastrarEvento";
	}
	
	@RequestMapping(value = "/eventos", method = RequestMethod.GET)
	public ModelAndView listaEventos() {
		ModelAndView mav = new ModelAndView("index");//a instancia está referenciando o arquivo index.html
		Iterable<Evento> eventos = er.findAll();
		mav.addObject("eventos", eventos);//O primeiro arg é referente a div com o ${evento} 
		return mav;
	}
}
