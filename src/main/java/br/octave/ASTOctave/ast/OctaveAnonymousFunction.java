package br.octave.ASTOctave.ast;

import java.util.ArrayList;
import java.util.List;

public class OctaveAnonymousFunction extends ElementoOctave{
    
	private List<ElementoOctave> parametros;
	private OctaveExpressao expressao;

	public void adicionaParametro(ElementoOctave parametro){
		if(this.parametros == null)
			this.parametros = new ArrayList<ElementoOctave>();
		this.parametros.add(parametro);
	}

	public List<ElementoOctave> getParametros(){
		return this.parametros;
	}

	public OctaveExpressao getExpressao() {
		return expressao;
	}

	public void setExpressao(OctaveExpressao expressao) {
		this.expressao = expressao;
	}
}