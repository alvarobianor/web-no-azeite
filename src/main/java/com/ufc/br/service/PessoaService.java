package com.ufc.br.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.ufc.br.model.Pedido;
import com.ufc.br.model.Pessoa;
import com.ufc.br.model.Prato;
import com.ufc.br.model.TEMP_Pedido;

import com.ufc.br.repository.PedidoRepository;
import com.ufc.br.repository.PessoaRepository;
import com.ufc.br.repository.PratoRepository;
import com.ufc.br.repository.TEMP_PedidoRepository;
import com.ufc.br.util.AulaFileUtils;


@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public void salvar(Pessoa pessoa, MultipartFile imagem) {
		pessoa.setSenha(new BCryptPasswordEncoder().encode(pessoa.getSenha()));
		
		String caminho = "images/" + pessoa.getNome() + ".png";
		AulaFileUtils.salvarImagem(caminho,imagem);
		
		pessoaRepository.save(pessoa);
	}
	
	public List<Pessoa> listarPessoas(){
		return pessoaRepository.findAll();
	}

	public void excluir(Long codigo) {
		pessoaRepository.deleteById(codigo);
		
	}
	
	public Pessoa buscarPorId(Long codigo) {
		return pessoaRepository.getOne(codigo);
	}

	////////////////////////////////////////////////////////////////////
	
	public List<Prato> buscarPratos(Integer id) {
		List<Prato> pratos = pedidoRepository.getOne(id).getPratos();
			
		return pratos;
	}
	
	
	@Autowired
	private PratoRepository repo2;
	

	public Prato cadastrar(Prato prato) {
		
		return repo2.save(prato);
	}


	public List<Prato> listarPratos() {
		// TODO Auto-generated method stub
		return repo2.findAll();
	}


	public void excluir(Integer id) {
		repo2.deleteById(id);
	}


	public Prato cadastrar(Prato prato, @RequestParam(value= "imagem") MultipartFile imagem) {
		String caminho = "images/" + prato.getNome() + ".png";
		AulaFileUtils.salvarImagem(caminho,imagem);
		return repo2.save(prato);
	}
	
	@Autowired
	TEMP_PedidoRepository repo;
	
	
	
	@Autowired
	PedidoRepository repo4;


	public TEMP_Pedido buscar() {

		if (repo.findAll().isEmpty()) {
			return new TEMP_Pedido();
		} else {
			TEMP_Pedido temp = repo.findAll().get(0);
			return temp;
		}

	}

	public void cadastrarTEMP_Pedido(Integer id, TEMP_Pedido temp) {

		Prato prato = repo2.getOne(id);
		temp.addPrato(prato);

		repo.save(temp);
	}

	public TEMP_Pedido buscarCarrinho() {
		return this.buscar();
	}

	public void deletarPrato(Integer id) {
		TEMP_Pedido temp = this.buscar();
		if(temp.deletePrato(id)) {
			repo.save(temp);
		}
	}

	public double somaTudo() {
		TEMP_Pedido temp = this.buscar();
		double conta=0;
		for(Prato p: temp.getPratos()) {
			conta += p.getPreco();
		}
		return conta;
	}

	public void fecharCarrinho(String cpf) {
		Pessoa cliente = pessoaRepository.findByCpf(cpf);
		TEMP_Pedido temp = this.buscar();
		Pedido pedido = new Pedido(temp, cliente);
		pedido.setTotal(this.somaTudo());
		repo4.save(pedido);
		
		cliente.getPedidos().add(pedido);
		pessoaRepository.save(cliente);
		
		temp.setPratos(new ArrayList<>());
		repo.deleteAll();
	}
}
