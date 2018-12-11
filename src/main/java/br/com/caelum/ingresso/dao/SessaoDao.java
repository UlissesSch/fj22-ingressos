package br.com.caelum.ingresso.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

@Repository
public class SessaoDao {

	@PersistenceContext
	private EntityManager manager;

	public Sessao findOne(Integer id) {
		return manager.find(Sessao.class, id);
	}
	
	public Sessao findOneComSalas(Integer id) {
		return manager
				.createQuery("select s from Sessao s join fetch s.sala sala left join fetch sala.lugares where s.id = :id",
						Sessao.class).setParameter("id", id)
				.getSingleResult();
	}

	public void save(Sessao sessao) {
		manager.persist(sessao);
	}

	public List<Sessao> buscaSessoesDaSala(Sala sala) {
		return manager
				.createQuery("select s from Sessao s where s.sala = :sala",
						Sessao.class).setParameter("sala", sala)
				.getResultList();
	}

	public List<Sessao> BuscaSessoesDoFilme(Filme filme) {
		return manager
				.createQuery("select s from Sessao s where s.filme = :filme",
						Sessao.class).setParameter("filme", filme)
				.getResultList();
	}

}
