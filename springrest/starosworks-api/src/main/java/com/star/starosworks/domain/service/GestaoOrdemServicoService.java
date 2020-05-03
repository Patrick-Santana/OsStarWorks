package com.star.starosworks.domain.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.star.starosworks.api.model.Comentario;
import com.star.starosworks.domain.exception.NegocioException;
import com.star.starosworks.domain.model.Cliente;
import com.star.starosworks.domain.model.OrdemServico;
import com.star.starosworks.domain.model.StatusOrdemServico;
import com.star.starosworks.domain.repository.ClienteRepository;
import com.star.starosworks.domain.repository.ComentarioRepository;
import com.star.starosworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	
	public OrdemServico criar(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
			.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new NegocioException("Ordem de Serviço não encontrada"));
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
	}
	
	
}
