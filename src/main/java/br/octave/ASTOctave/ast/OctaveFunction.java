package br.octave.ASTOctave.ast;

import java.util.Vector;

public class OctaveFunction extends ElementoOctave{

	private OctaveIdentificador identificadorFuncao;
	private String retorno;
	private Vector<ElementoOctave> parametros;
	
	public void adicionaParametros(ElementoOctave parametro){
		if(this.parametros == null)
			this.parametros = new Vector<ElementoOctave>();
		this.parametros.addElement(parametro);
	}
	
	public Vector<ElementoOctave> getParametros(){
		return this.parametros;
	}
	
	public OctaveIdentificador getIdentificador(){
		return this.identificadorFuncao;
	}
	
	public void setIdentificador(String nome){
		this.identificadorFuncao = new OctaveIdentificador(nome);
	}

	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}
}