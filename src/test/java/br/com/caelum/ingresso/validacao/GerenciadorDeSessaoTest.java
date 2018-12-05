package br.com.caelum.ingresso.validacao;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import javax.validation.constraints.AssertFalse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

import com.mysql.fabric.xmlrpc.base.Array;

public class GerenciadorDeSessaoTest {

	private Filme rogueOne;
	private Sala sala3d;
	private Sessao sessaoDastreze;
	private Sessao sessaoDasDez;
	private Sessao sessaoDasDezoito;

	@Before
	public void preparaSessoes() {

		this.rogueOne = new Filme("Rogue One", Duration.ofMinutes(120),
				"SCI-FI");
		this.sala3d = new Sala("Sala 3d");

		this.sessaoDasDez = new Sessao(LocalTime.parse("10:00:00"), rogueOne,
				sala3d);
		this.sessaoDastreze = new Sessao(LocalTime.parse("13:00:00"), rogueOne,
				sala3d);
		this.sessaoDasDezoito = new Sessao(LocalTime.parse("18:00:00"),
				rogueOne, sala3d);

	}

	@Test
	public void garanteQueNaoDevePermitirSessaoNoMesmoHorario() {

		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(sessaoDasDez));
	}

	@Test
	public void garanteQueNaoDevePermitirSessoesTerminandoDentroDoHorarioDeUmaSessaoJaExistente() {
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		Sessao sessao = new Sessao(sessaoDasDez.getHorario().minusHours(1),
				rogueOne, sala3d);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertFalse(gerenciador.cabe(sessao));

	}

	@Test
	public void garanteQueNaoDevePermitirSessoesIniciandoDentroDoHorarioDeUmaSessaoJaExiste() {
		List<Sessao> sessoesDaSala = Arrays.asList(sessaoDasDez);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoesDaSala);
		Sessao sessao = new Sessao(sessaoDasDez.getHorario().plusHours(1),
				rogueOne, sala3d);
		Assert.assertFalse(gerenciador.cabe(sessao));
	}

	@Test
	public void garanteQueDevePermitirUmaInsercaoEntreDoisFilmes() {
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez, sessaoDasDezoito);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		Assert.assertTrue(gerenciador.cabe(sessaoDastreze));
	}
}
