package br.octave.ASTOctave.ast;

public class OctaveExpressaoTranspose extends OctaveExpressao{

	private ElementoOctave elemento;
	private String operacao;
	
	public OctaveExpressaoTranspose(ElementoOctave elemento, String operacao){
		this.elemento = elemento;
		this.operacao = operacao;
	}
	public ElementoOctave getIdentificador(){
		return this.elemento;
	}
	public String getOperacao(){
		return this.operacao;
	}
}