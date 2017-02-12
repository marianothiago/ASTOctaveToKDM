package br.octave.ASTOctave.ast;

public class OctaveOperacaoComparacao extends OctaveExpressao{

	private ElementoOctave primeiroOperando;
	private ElementoOctave segundoOperando;
	private String operador;

	public OctaveOperacaoComparacao(ElementoOctave primeiroOperando, ElementoOctave segundoOperando, String operador){
		this.primeiroOperando = primeiroOperando;
		this.segundoOperando = segundoOperando;
		this.operador = operador;
	}
	
	public ElementoOctave getPrimeiroOperando() {
		return primeiroOperando;
	}
	public ElementoOctave getSegundoOperando() {
		return segundoOperando;
	}
	public String getOperador() {
		return operador;
	}
}