package io.github.carolsimoes.crud.services;

import java.awt.print.Pageable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import io.github.carolsimoes.crud.data.vo.ProdutoVO;
import io.github.carolsimoes.crud.entity.Produto;
import io.github.carolsimoes.crud.repository.ProdutoRepository;
import io.github.carolsimoes.exception.ResourceNotFoundException;

@Service
public class ProdutoService {
	
	private final ProdutoRepository produtoRepository;

	@Autowired
	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}
	
	public ProdutoVO create(ProdutoVO produtoVO) {
		ProdutoVO proVORetorno = ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
		return proVORetorno;
	}
	
	public Page<ProdutoVO> findAll(org.springframework.data.domain.Pageable pageable) {
		var page = produtoRepository.findAll(pageable);
		return page.map(this::convertToProdutoVO);
	}
	
	private ProdutoVO convertToProdutoVO(Produto produto) {
		return ProdutoVO.create(produto);		
	}
	
	public ProdutoVO findById(Long id) {
		var entity = produtoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum registro para este ID"));
		return ProdutoVO.create(entity);
	}
	
	public ProdutoVO update(ProdutoVO produtoVO) {
		final Optional<Produto> optionalProduto = produtoRepository.findById(produtoVO.getId());
		
		if(!optionalProduto.isPresent()) {
			new ResourceNotFoundException("Não foi encontrado nenhum registro para este ID");
		}
		
		return ProdutoVO.create(produtoRepository.save(Produto.create(produtoVO)));
	}
	
	public void delete(Long id) {
		var entity = produtoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado nenhum registro para este ID"));
		produtoRepository.delete(entity);
	}
}
