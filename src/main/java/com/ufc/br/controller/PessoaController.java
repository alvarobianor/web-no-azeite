package com.ufc.br.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ufc.br.model.GampPegarID;
import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Prato;
import com.ufc.br.model.TEMP_Pedido;
import com.ufc.br.repository.PessoaRepository;
import com.ufc.br.service.PessoaService;

@Controller
public class PessoaController {
	
	
	@Autowired
	private PessoaService pessoaService;
	
	/*
	 * @RequestMapping("/olamundo") public String olaMundo() { return "OlaMundo"; }
	 */
	
	
	@RequestMapping("/pessoa/formulario")
	public ModelAndView retornarForm() {
		ModelAndView mv = new ModelAndView("Formulario");
		mv.addObject(new Pessoa());
		return mv;
	}
	
	@PostMapping("/pessoa/cadastrar")
	public ModelAndView cadastrar(@Validated Pessoa pessoa, BindingResult result 
			,@RequestParam(value= "imagem") MultipartFile imagem) {
		
		ModelAndView mv = new ModelAndView("Formulario");
		
		if(result.hasErrors()) {
			return mv; //retorno pra mesma página, nem tento salvar pessoa
		}
		
		pessoaService.salvar(pessoa,imagem);
		
		mv.addObject("mensagem", "Título cadastrado com sucesso!");
		
		return mv;
	}
	
	
	@GetMapping("/pessoa/listar")
	public ModelAndView listar() {
		List<Pessoa> pessoas = pessoaService.listarPessoas();
		ModelAndView mv = new ModelAndView("Pagina-Listagem");
		mv.addObject("listaPessoas", pessoas);
		return mv;
	}
	
	@RequestMapping("/pessoa/excluir/{codigo}")
	public ModelAndView excluir(@PathVariable Long codigo) {
		pessoaService.excluir(codigo);
		ModelAndView mv = new ModelAndView("redirect:/pessoa/listar");
		return mv;
		
	}
	
	@RequestMapping("/pessoa/atualizar/{codigo}")
	public ModelAndView atualizar(@PathVariable Long codigo) {
		Pessoa pessoa = pessoaService.buscarPorId(codigo);
		ModelAndView mv = new ModelAndView("Formulario");
		mv.addObject("pessoa", pessoa);
		return mv;
	}
	

	@RequestMapping("/pessoa/logar")
	public ModelAndView logar() {
		ModelAndView mv = new ModelAndView("Login");
		return mv;
	}
	
	
	@RequestMapping("/")
	public ModelAndView pagInicial() {
		List<Prato> pratos = pessoaService.listarPratos();
		TEMP_Pedido carrinho = pessoaService.buscarCarrinho();
		double valor = pessoaService.somaTudo();
		ModelAndView mv = new ModelAndView("/pedidoEcarrinho");
		mv.addObject("list", pratos);
		mv.addObject("carrinho", carrinho);
		mv.addObject("conta", valor);
		return mv;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////
	@GetMapping("/historico/{codigo}")
	public ModelAndView historico(@PathVariable long codigo) {
		Pessoa pessoa = pessoaService.buscarPorId(codigo);
		
		ModelAndView mv = new ModelAndView("historico");
		mv.addObject("cliente", pessoa);
		return mv;
	}
	
	@GetMapping("clientes/pratos/{id}")
	public ModelAndView historico(@PathVariable Integer id) {
		List<Prato> pratos =  pessoaService.buscarPratos(id);
		
		ModelAndView mv = new ModelAndView("historicoPratos");
		mv.addObject("pratos", pratos);
		return mv;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////
	@GetMapping("/gerentes")
	public ModelAndView homeGerente() {
		
		ModelAndView mv = new ModelAndView("cadastrarPratos");
		return mv;
		
	}
	
	@PostMapping("/gerentes")
	public ModelAndView cadastrar(Prato prato, @RequestParam(value= "imagem") MultipartFile imagem) {
		// Prato prato = new Prato(nome, preco);
		prato = pessoaService.cadastrar(prato, imagem);
		ModelAndView mv = new ModelAndView("cadastrarPratos");

		return mv;
	}

	@RequestMapping("/gerentes/deletar/{id}")
	public ModelAndView excluir(@PathVariable Integer id) {		
		pessoaService.excluir(id);
		ModelAndView mv = new ModelAndView("redirect:/gerentes/listarPratos");
		return mv;
	}
	

	@GetMapping("/gerentes/listarPratos")//Só gerente
	public ModelAndView listarTodos() {

		List<Prato> pratos = pessoaService.listarPratos();
		ModelAndView mv = new ModelAndView("listarPratos");
		mv.addObject("list", pratos);
		return mv;

	}
	///////////////////////////////////////////////////////////////////////////////////////////////////

	@PostMapping("/pedidos")
	public ModelAndView cadastrar(GampPegarID id) {

		TEMP_Pedido temp = pessoaService.buscar();
		pessoaService.cadastrarTEMP_Pedido(id.getId(), temp);

		ModelAndView mv = new ModelAndView("redirect:/");
		mv.addObject("aidi", id);
		return mv;

	}
	
	@GetMapping("/pedidos/atualizarCarrinho/{id}")
	public ModelAndView editar(@PathVariable Integer id) {
		
		pessoaService.deletarPrato(id);
		ModelAndView mv = new ModelAndView("redirect:/");
		return mv;
	}
	
	@GetMapping("/pedidos/fecharCarrinho/{cpf}")
	public ModelAndView feharCarrinho(@PathVariable String cpf) {
		
		pessoaService.fecharCarrinho(cpf);
		ModelAndView mv = new ModelAndView("redirect:/");
		return mv;
	}
}
