package com.eventoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eventoapp.models.Convidado;
import com.eventoapp.models.Evento;
import com.eventoapp.repository.ConvidadoRepository;
import com.eventoapp.repository.EventoRepository;

@SpringBootApplication
@Controller
public class EventoController {

	@Autowired
	private EventoRepository er;
	
	@Autowired
	private ConvidadoRepository cr;
	
	@RequestMapping(value = "/cadastrarEvento", method=RequestMethod.GET)
	public String form(){
		return "evento/formEvento";
	}
	
	@RequestMapping(value = "/cadastrarEvento", method=RequestMethod.POST)
	public String form(Evento evento) {
		/*Método responsável por cadastrar um evento*/
		er.save(evento);
		
		return "redirect:/cadastrarEvento";
	}
	
	@RequestMapping(value = "/eventos", method = RequestMethod.GET)
	public ModelAndView listaEventos() {
		/*Método responsável por retornar todos os eventos cadastrados*/
		ModelAndView mav = new ModelAndView("index");//a instancia está referenciando o arquivo index.html
		Iterable<Evento> eventos = er.findAll();
		mav.addObject("eventos", eventos);//O primeiro arg é referente a div com o ${evento} 
		return mav;
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.GET)
	public ModelAndView detalhesEventos(@PathVariable("codigo") long codigo) {
		/*Método responsável por retornar um evento em especifico*/
		Evento evento = er.findByCodigo(codigo);//método anotado na interface EventoRepository
		ModelAndView mav = new ModelAndView("evento/detalhesEvento");
		mav.addObject("evento", evento);
		
		return mav;
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, Convidado convidado) {
		/*Método responsavel por cadastrar um novo convidado ao evento
		 * Esse método relaciona o evento ao convidado*/
		Evento evento = er.findByCodigo(codigo);
		convidado.setEvento(evento);
		cr.save(convidado);
		
		return "redirect:/{codigo}";
	}
}
