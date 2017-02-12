package br.octave.ASTOctave.ast;

public class OctaveExpressaoAtribuicao extends OctaveExpressao{

	private ElementoOctave var;
	private ElementoOctave valorAtribuido;
	
	public OctaveExpressaoAtribuicao(ElementoOctave var, ElementoOctave valorAtribuido){
		this.var = var;
		this.valorAtribuido = valorAtribuido;
	}
	
	public ElementoOctave getVar(){
		return this.var;
	}
	public ElementoOctave getValorAtribuido(){
		return this.valorAtribuido;
	}
}