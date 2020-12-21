package com.eventoapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
		/*Método responsável por cadastrar um evento*/
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique se todos os campos foram preenchidos corretamente!");
			return "redirect:/cadastrarEvento";
		}
		er.save(evento);
		attributes.addFlashAttribute("mensagem", "Evento cadastrado com sucesso!!!");
		
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
		
		Iterable<Convidado> convidados = cr.findByEvento(evento);
		mav.addObject("convidados", convidados);
		
		return mav;
	}
	
	@RequestMapping(value = "/deletar")
	public String deletarEvento(long codigo) {
		/*Método responsavel por deletar um evento recebendo o codigo do mesmo*/
		Evento evento = er.findByCodigo(codigo);
		er.delete(evento);
		return "redirect:/eventos";
	}
	
	@RequestMapping(value = "deletarConvidado")
	public String deletarConvidado(String rg) {
		/*Método resposavel por deletar convidados da lista, e retornar a lista de convidados atualizada*/
		Convidado convidado = cr.findByRg(rg);
		
		Evento evento = convidado.getEvento();
		long codLong = evento.getCodigo();
		String codigo = "" + codLong;
		cr.delete(convidado);
		return "redirect:/" + codigo;
	}
	
	@RequestMapping(value="/{codigo}", method=RequestMethod.POST)
	public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {
		/*Método responsavel por cadastrar um novo convidado ao evento
		 * Esse método relaciona o evento ao convidado
		 * Verifica se o usuário preencheu todos os campo*/
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique se os campos estão preenchidos corretamente!");
			return "redirect:/{codigo}";
		}
		
		Evento evento = er.findByCodigo(codigo);
		convidado.setEvento(evento);
		cr.save(convidado);
		attributes.addFlashAttribute("mensagem", "Convidado cadastrado com sucesso!!!");
		
		return "redirect:/{codigo}";
	}
}
