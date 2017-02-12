package br.octave.astOctaveToKDM.octaveKDM;

import java.util.ArrayList;
import java.util.List;

import br.octave.ASTOctave.ast.ElementoOctave;

public class No{
	
	private ElementoOctave elemento = null;
	private List<No> filhos;
	
	public No(ElementoOctave elemento){
		this.elemento = elemento;
	}
	
	public ElementoOctave getElemento(){
		return this.elemento;
	}
	
	public void adicionaFilho(No filho){
		if(filhos == null)
			filhos = new ArrayList<No>();
		filhos.add(filho);
	}
	public List<No> getFilhos(){
		return this.filhos;
	}
}