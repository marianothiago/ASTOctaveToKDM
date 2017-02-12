//package br.octave.astOctaveToKDM.octaveKDM;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Vector;
//
//import br.octave.ASTOctave.ast.Block;
//import br.octave.ASTOctave.ast.ElementoOctave;
//import br.octave.ASTOctave.ast.OctaveAnonymousFunction;
//import br.octave.ASTOctave.ast.OctaveArray;
//import br.octave.ASTOctave.ast.OctaveBreak;
//import br.octave.ASTOctave.ast.OctaveCaseSwitch;
//import br.octave.ASTOctave.ast.OctaveCatch;
//import br.octave.ASTOctave.ast.OctaveChamadaFuncao;
//import br.octave.ASTOctave.ast.OctaveConstante;
//import br.octave.ASTOctave.ast.OctaveContinue;
//import br.octave.ASTOctave.ast.OctaveDo;
//import br.octave.ASTOctave.ast.OctaveElse;
//import br.octave.ASTOctave.ast.OctaveElseIf;
//import br.octave.ASTOctave.ast.OctaveExpressao;
//import br.octave.ASTOctave.ast.OctaveExpressaoAtribuicao;
//import br.octave.ASTOctave.ast.OctaveExpressaoAutoDecremento;
//import br.octave.ASTOctave.ast.OctaveExpressaoAutoIncremento;
//import br.octave.ASTOctave.ast.OctaveExpressaoBinaria;
//import br.octave.ASTOctave.ast.OctaveExpressaoLooping;
//import br.octave.ASTOctave.ast.OctaveExpressaoTranspose;
//import br.octave.ASTOctave.ast.OctaveExpressaoUnaria;
//import br.octave.ASTOctave.ast.OctaveFor;
//import br.octave.ASTOctave.ast.OctaveFunction;
//import br.octave.ASTOctave.ast.OctaveGlobal;
//import br.octave.ASTOctave.ast.OctaveHandleFunction;
//import br.octave.ASTOctave.ast.OctaveIdentificador;
//import br.octave.ASTOctave.ast.OctaveIf;
//import br.octave.ASTOctave.ast.OctaveImport;
//import br.octave.ASTOctave.ast.OctaveMatriz;
//import br.octave.ASTOctave.ast.OctaveOperacaoComparacao;
//import br.octave.ASTOctave.ast.OctaveOperacaoLogica;
//import br.octave.ASTOctave.ast.OctaveOtherwiseSwitch;
//import br.octave.ASTOctave.ast.OctaveParFor;
//import br.octave.ASTOctave.ast.OctavePersistent;
//import br.octave.ASTOctave.ast.OctaveString;
//import br.octave.ASTOctave.ast.OctaveStructure;
//import br.octave.ASTOctave.ast.OctaveSwitch;
//import br.octave.ASTOctave.ast.OctaveTry;
//import br.octave.ASTOctave.ast.OctaveUnwindProtect;
//import br.octave.ASTOctave.ast.OctaveUnwindProtectCleanup;
//import br.octave.ASTOctave.ast.OctaveVariavel;
//import br.octave.ASTOctave.ast.OctaveVariavelStructure;
//import br.octave.ASTOctave.ast.OctaveWhile;
//
//public class OctaveAST {
//
//	private ArvoreOctave ast;
//	private String content;
//	private int indiceAtual = 0;
//	private char[] source;
//	private String token = "";
//	private char delimitadorEncontrado;
//	private List<No> elementosPendentes = new ArrayList<No>();
//	private Vector<String> delimitadores;
//	private Vector<String> palavrasReservadas;
//
//	public OctaveAST(){
//		setaDelimitadores();
//		setaPalavrasReservadas();
//	}
//	
//	private void setaDelimitadores(){
//		delimitadores = new Vector<String>();
//		delimitadores.add(",");
//		delimitadores.add(";");
//		delimitadores.add("\n");
//		delimitadores.add("\r");
//		delimitadores.add("=");
//		delimitadores.add("==");
//		delimitadores.add("+");
//		delimitadores.add("++");
//		delimitadores.add(".+");
//		delimitadores.add("-");
//		delimitadores.add("--");
//		delimitadores.add(".-");
//		delimitadores.add("*");
//		delimitadores.add(".*");
//		delimitadores.add(".**");
//		delimitadores.add("/");
//		delimitadores.add("./");
//		delimitadores.add("\\");
//		delimitadores.add(".\\");
//		delimitadores.add(">");
//		delimitadores.add(">=");
//		delimitadores.add("<");
//		delimitadores.add("<=");
//		delimitadores.add("(");
//		delimitadores.add(")");
//		delimitadores.add("[");
//		delimitadores.add("]");
//		delimitadores.add("{");
//		delimitadores.add("}");
//		delimitadores.add(":");
//		delimitadores.add("\'");
//		delimitadores.add(".\'");
//		delimitadores.add("\"");
//		delimitadores.add("&");
//		delimitadores.add("&&");
//		delimitadores.add("|");
//		delimitadores.add("||");
//		delimitadores.add("!");
//		delimitadores.add("!=");
//		delimitadores.add("~");
//		delimitadores.add("~=");
//		delimitadores.add("^");
//		delimitadores.add(".^");
////		delimitadores.add("?");
//	}
//	
//	private void setaPalavrasReservadas(){
//		palavrasReservadas = new Vector<String>();
//		palavrasReservadas.add("function");
//		palavrasReservadas.add("for");
//		palavrasReservadas.add("parfor");
//		palavrasReservadas.add("import");
//		palavrasReservadas.add("global");
//		palavrasReservadas.add("persistent");
//		palavrasReservadas.add("if");
//		palavrasReservadas.add("elseif");
//		palavrasReservadas.add("else");
//		palavrasReservadas.add("switch");
//		palavrasReservadas.add("case");
//		palavrasReservadas.add("otherwise");
//		palavrasReservadas.add("while");
//		palavrasReservadas.add("do");
//		palavrasReservadas.add("until");
//		palavrasReservadas.add("try");
//		palavrasReservadas.add("catch");
//		palavrasReservadas.add("unwind_protect");
//		palavrasReservadas.add("unwind_protect_cleanup");
//		palavrasReservadas.add("break");
//		palavrasReservadas.add("continue");
//		palavrasReservadas.add("function");
//		palavrasReservadas.add("function");
//		palavrasReservadas.add("function");
//		palavrasReservadas.add("function");
//		palavrasReservadas.add("function");
//
//	}
//	
//	public ArvoreOctave generateAST(String fileName) {
//		System.out.println(fileName);
//		streamFile(fileName);
//		this.source = this.content.toCharArray();
//		
//		Block blocoPrincipal = new Block("Bloco Principal");
//		No raiz = new No(blocoPrincipal);
//		this.elementosPendentes.add(raiz);
//		ast = new ArvoreOctave(raiz);
//		parse();
//		return this.ast;
//	}
//
//	private void parse(){
//		String anterior = "";
//		pegaToken(true);
//		while(this.indiceAtual != this.source.length){
//			boolean ehPalavraReservada = false;
//			while(this.token.equals("\n")){
//				pegaToken(true);
//			}
//			if(this.token.toLowerCase().equals("import")){
//				ehPalavraReservada = true;
//		    	String tokenTemp = montaStringExpressao();
//		    	No no = null;
//		    	OctaveImport importOctave = null;
//		    	importOctave = new OctaveImport(tokenTemp);
//		    	no = new No(importOctave);
//		    	((No)this.elementosPendentes.get(this.elementosPendentes.size()-1)).adicionaFilho(no);
//				pegaTokenProximaLinha();
//			}
//		    if(this.token.toLowerCase().equals("function")){
//		    	if(anterior.equals("return")){
//		    		this.elementosPendentes.remove(this.elementosPendentes.size() - 1);
//		    		anterior = "";
//		    	}
//		    	ehPalavraReservada = true;
//		    	OctaveFunction funcao = converteElementoFunction();
//		    	No no = new No(funcao);
//		    	((No)this.elementosPendentes.get(this.elementosPendentes.size()-1)).adicionaFilho(no);
//		    	if(this.source[this.indiceAtual-1] == ','){
//		    		String tempToken = "";
//		    		while((!this.token.equals("return"))&&(!isEnd(this.token.toLowerCase()))){
//		    			pegaToken(true);
//		    			tempToken = tempToken + this.token;
//		    			if(this.token.equals(";")){
//		    				OctaveExpressao expressao = null;
//		    		    	Vector<ElementoOctave> tokensExpressao;
//		    		    	tokensExpressao = separaTokens(tempToken);
//		    		    	tempToken = "";
//		    		    	expressao = montaOctaveExpressao(tokensExpressao);
//		    		    	no.adicionaFilho(new No(expressao));
//		    			}
//		    		}
//		    	}else{
//		    		this.elementosPendentes.add(no);
//		    	    pegaTokenProximaLinha();
//		    	}
//		    }
//		    if((this.token.toLowerCase().equals("for"))||(this.token.toLowerCase().equals("parfor"))){
//		    	ehPalavraReservada = true;
//		    	String tipoFor = this.token;
//		    	String tokenTemp = montaStringExpressao();
//		    	OctaveExpressao variavel = montaOctaveExpressao(separaTokens((tokenTemp.substring(0, tokenTemp.indexOf("="))).trim()));
//		    	tokenTemp = tokenTemp.substring(tokenTemp.indexOf("=")+1);
//		    	
//		    	OctaveExpressao expressao = null;
//		    	Vector<ElementoOctave> tokensExpressao;
//		    	
//		    	tokensExpressao = separaTokens(tokenTemp);
//		    	
//		    	List<Vector<ElementoOctave>> lista = separaExpressoes(tokensExpressao);
//		    	expressao = montaOctaveExpressao(lista.get(0));
//		    	
//		    	No no = null;
//		    	if(tipoFor.equals("for")){
//		    		OctaveFor repeticaoFor = null;
//		    		repeticaoFor = new OctaveFor(variavel, expressao);
//		    		no = new No(repeticaoFor);
//		    	}else{
//		    		OctaveParFor repeticaoParFor = null;
//		    		repeticaoParFor = new OctaveParFor(variavel, expressao);
//		    		no = new No(repeticaoParFor);
//		    	}		    	
//		    	((No)this.elementosPendentes.get(this.elementosPendentes.size()-1)).adicionaFilho(no);
//		    	
//		    	if(lista.size()>1){
//		    		expressao = montaOctaveExpressao(lista.get(1));
//		    		no.adicionaFilho(new No(expressao));
//		    	}else{
//		    		this.elementosPendentes.add(no);
//		    	}
//		    	pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("global")){
//		    	ehPalavraReservada = true;
//		    	String tokenTemp = montaStringExpressao();
//		    	OctaveExpressao expressao = null;
//		    	Vector<ElementoOctave> tokensExpressao;
//		    	
//		    	tokensExpressao = separaTokens(tokenTemp);
//		    	expressao = montaOctaveExpressao(tokensExpressao);
//		    	No no = null;
//		    	OctaveGlobal global = null;
//		    	global = new OctaveGlobal(expressao);
//		    	no = new No(global);
//		    	((No)this.elementosPendentes.get(this.elementosPendentes.size()-1)).adicionaFilho(no);
//				pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("persistent")){
//		    	ehPalavraReservada = true;
//		    	String tokenTemp = montaStringExpressao();
//		    	OctaveExpressao expressao = null;
//		    	Vector<ElementoOctave> tokensExpressao;
//		    	
//		    	tokensExpressao = separaTokens(tokenTemp);
//		    	expressao = montaOctaveExpressao(tokensExpressao);
//		    	No no = null;
//		    	OctavePersistent persistent = null;
//		    	persistent = new OctavePersistent(expressao);
//		    	no = new No(persistent);
//				((No)this.elementosPendentes.get(this.elementosPendentes.size()-1)).adicionaFilho(no);
//				pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("if")){
//		    	ehPalavraReservada = true;
//		    	String tokenTemp = montaStringExpressao();
//		    	OctaveExpressao expressao = null;
//		    	Vector<ElementoOctave> tokensExpressao;
//		    	
//		    	tokensExpressao = separaTokens(tokenTemp);
//		    	
//		    	No no = null;
//		    	OctaveIf ifOctave = null;
//		    	
//		    	List<Vector<ElementoOctave>> lista = separaExpressoes(tokensExpressao);
//		    	expressao = montaOctaveExpressao(lista.get(0));
//		    	
//		    	ifOctave = new OctaveIf(expressao);
//		    	no = new No(ifOctave);
//				((No)this.elementosPendentes.get(this.elementosPendentes.size()-1)).adicionaFilho(no);
//		    	
//				if(lista.size()>1){
//					expressao = montaOctaveExpressao(lista.get(1));
//					No noTemp = new No(expressao);
//					no.adicionaFilho(noTemp);
//					if(lista.size()>2){
//						if(((Token)lista.get(2).get(0)).getValor().equals("else")){
//							OctaveElse elseOctave = null;
//					    	elseOctave = new OctaveElse();
//							No noTemp2 = new No(elseOctave);
//							no.adicionaFilho(noTemp2);
//							this.elementosPendentes.add(noTemp2);
//						}
//					}
//				}else
//				    this.elementosPendentes.add(no);
//		    	
//				pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("elseif")){
//		    	ehPalavraReservada = true;
//		    	String tokenTemp = montaStringExpressao();
//		    	OctaveExpressao expressao = null;
//		    	Vector<ElementoOctave> tokensExpressao;
//		    	
//		    	tokensExpressao = separaTokens(tokenTemp);
//		    	List<Vector<ElementoOctave>> lista = separaExpressoes(tokensExpressao);
//		    	expressao = montaOctaveExpressao(lista.get(0));
//
//		    	No no = null;
//		    	OctaveElseIf elseIfOctave = null;
//		    	elseIfOctave = new OctaveElseIf(expressao);
//		    	no = new No(elseIfOctave);
//		    	
//				No ifNo = this.elementosPendentes.get(this.elementosPendentes.size() - 1);
//				ifNo.adicionaFilho(no);
//				this.elementosPendentes.remove(this.elementosPendentes.size() - 1);
//				
//				if(lista.size() > 1){
//					expressao = montaOctaveExpressao(lista.get(1));
//					no.adicionaFilho(new No(expressao));
//					if(lista.size()>2){
//						if(((Token)lista.get(2).get(0)).getValor().equals("else")){
//							OctaveElse elseOctave = null;
//					    	elseOctave = new OctaveElse();
//							No noTemp = new No(elseOctave);
//							no.adicionaFilho(noTemp);
//							this.elementosPendentes.add(noTemp);
//						}
//					}
//				}else{
//				    this.elementosPendentes.add(no);
//				}
//				pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("else")){
//		    	ehPalavraReservada = true;
//		    	while(!this.token.equals("\n")){
//		    		pegaToken(true);
//		    	}
//		    	No no = null;
//		    	OctaveElse elseOctave = null;
//		    	elseOctave = new OctaveElse();
//		    	no = new No(elseOctave);
//				No ifNo = this.elementosPendentes.get(this.elementosPendentes.size() - 1);
//				ifNo.adicionaFilho(no);
//				this.elementosPendentes.remove(this.elementosPendentes.size() - 1);
//				this.elementosPendentes.add(no);
//				pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("switch")){
//		    	ehPalavraReservada = true;
//		    	String tokenTemp = montaStringExpressao();
//		    	OctaveExpressao expressao = null;
//		    	Vector<ElementoOctave> tokensExpressao;
//		    	
//		    	tokensExpressao = separaTokens(tokenTemp);
//		    	expressao = montaOctaveExpressao(tokensExpressao);
//		    	
//		    	No no = null;
//		    	OctaveSwitch octSwitch = new OctaveSwitch(expressao);
//		    	no = new No(octSwitch);
//				((No)this.elementosPendentes.get(this.elementosPendentes.size()-1)).adicionaFilho(no);
//		    	this.elementosPendentes.add(no);
//		    	pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("case")){
//		    	ehPalavraReservada = true;
//		    	String tokenTemp = montaStringExpressao();
//		    	OctaveExpressao expressao = null;
//		    	Vector<ElementoOctave> tokensExpressao;
//		    	
//		    	tokensExpressao = separaTokens(tokenTemp);
//		    	expressao = montaOctaveExpressao(tokensExpressao);
//		    	
//		    	No no = null;
//		    	OctaveCaseSwitch octCaseSwitch = new OctaveCaseSwitch(expressao);
//		    	no = new No(octCaseSwitch);
//				
//				No switchNo = this.elementosPendentes.get(this.elementosPendentes.size() - 1);
//				
//				if(switchNo.getElemento() instanceof OctaveSwitch){
//					switchNo.adicionaFilho(no);
//				}else{
//					if(switchNo.getElemento() instanceof OctaveCaseSwitch){
//						this.elementosPendentes.remove(this.elementosPendentes.size() - 1);
//						switchNo = this.elementosPendentes.get(this.elementosPendentes.size() - 1);
//						switchNo.adicionaFilho(no);
//					}
//				}
//		    	this.elementosPendentes.add(no);
//		    	pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("otherwise")){
//		    	ehPalavraReservada = true;
//		    	while(!this.token.equals("\n")){
//		    		pegaToken(true);
//		    	}
//		    	
//		    	No no = null;
//		    	OctaveOtherwiseSwitch octOtherwiseSwitch = new OctaveOtherwiseSwitch();
//		    	no = new No(octOtherwiseSwitch);
//				
//				No switchNo = this.elementosPendentes.get(this.elementosPendentes.size() - 1);
//				
//				if(switchNo.getElemento() instanceof OctaveSwitch){
//					switchNo.adicionaFilho(no);
//				}else{
//					if(switchNo.getElemento() instanceof OctaveCaseSwitch){
//						this.elementosPendentes.remove(this.elementosPendentes.size() - 1);
//						switchNo = this.elementosPendentes.get(this.elementosPendentes.size() - 1);
//						switchNo.adicionaFilho(no);
//					}
//				}
//		    	this.elementosPendentes.add(no);
//		    	pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("while")){
//		    	ehPalavraReservada = true;
//		    	String tokenTemp = montaStringExpressao();
//		    	OctaveExpressao expressao = null;
//		    	Vector<ElementoOctave> tokensExpressao;
//		    	
//		    	tokensExpressao = separaTokens(tokenTemp);
//		    	List<Vector<ElementoOctave>> lista = separaExpressoes(tokensExpressao);
//		    	expressao = montaOctaveExpressao(lista.get(0));
//		    	
//		    	No no = null;
//		    	OctaveWhile whileOctave = null;
//		    	whileOctave = new OctaveWhile(expressao);
//		    	no = new No(whileOctave);
//				((No)this.elementosPendentes.get(this.elementosPendentes.size()-1)).adicionaFilho(no);
//				
//				if(lista.size()>1){
//					expressao = montaOctaveExpressao(lista.get(1));
//					no.adicionaFilho(new No(expressao));
//				}else{
//		    	    this.elementosPendentes.add(no);
//				}
//		    	pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("do")){
//		    	ehPalavraReservada = true;
//		    	while(!this.token.equals("\n")){
//		    		pegaToken(true);
//		    	}
//		    	No no = null;
//		    	OctaveDo doOctave = null;
//		    	doOctave = new OctaveDo();
//		    	no = new No(doOctave);
//				((No)this.elementosPendentes.get(this.elementosPendentes.size()-1)).adicionaFilho(no);
//		    	this.elementosPendentes.add(no);
//		    	pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("until")){
//		    	ehPalavraReservada = true;
//		    	String tokenTemp = montaStringExpressao();
//		    	OctaveExpressao expressao = null;
//		    	Vector<ElementoOctave> tokensExpressao;
//		    	
//		    	tokensExpressao = separaTokens(tokenTemp);
//		    	expressao = montaOctaveExpressao(tokensExpressao);
//		    	No noDoOctave = this.elementosPendentes.get(this.elementosPendentes.size()-1);
//		    	OctaveDo doOctave = (OctaveDo)noDoOctave.getElemento();
//		    	doOctave.setExpressao(expressao);
//		    	this.elementosPendentes.remove(this.elementosPendentes.size()-1);
//		    	pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("try")){
//		    	ehPalavraReservada = true;
//		    	while(!this.token.equals("\n")){
//		    		pegaToken(true);
//		    	}
//		    	No no = null;
//		    	OctaveTry tryOctave = null;
//		    	tryOctave = new OctaveTry();
//		    	no = new No(tryOctave);
//				((No)this.elementosPendentes.get(this.elementosPendentes.size()-1)).adicionaFilho(no);
//		    	this.elementosPendentes.add(no);
//		    	pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("catch")){
//		    	ehPalavraReservada = true;
//		    	while(!this.token.equals("\n")){
//		    		pegaToken(true);
//		    	}
//		    	No no = null;
//		    	OctaveCatch catchOctave = null;
//		    	catchOctave = new OctaveCatch();
//		    	No noTryOctave = this.elementosPendentes.get(this.elementosPendentes.size()-1);
//		    	this.elementosPendentes.remove(this.elementosPendentes.size()-1);
//		    	no = new No(catchOctave);
//		    	noTryOctave.adicionaFilho(no);
//		    	this.elementosPendentes.add(no);
//		    	pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("unwind_protect")){
//		    	ehPalavraReservada = true;
//		    	while(!this.token.equals("\n")){
//		    		pegaToken(true);
//		    	}
//		    	No no = null;
//		    	OctaveUnwindProtect unwindProtectOctave = null;
//		    	unwindProtectOctave = new OctaveUnwindProtect();
//		    	no = new No(unwindProtectOctave);
//				((No)this.elementosPendentes.get(this.elementosPendentes.size()-1)).adicionaFilho(no);
//		    	this.elementosPendentes.add(no);
//		    	pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("unwind_protect_cleanup")){
//		    	ehPalavraReservada = true;
//		    	while(!this.token.equals("\n")){
//		    		pegaToken(true);
//		    	}
//		    	No no = null;
//		    	OctaveUnwindProtectCleanup unwindProtectCleanupOctave = null;
//		    	unwindProtectCleanupOctave = new OctaveUnwindProtectCleanup();
//		    	No noUnwindProtectOctave = this.elementosPendentes.get(this.elementosPendentes.size()-1);
//		    	this.elementosPendentes.remove(this.elementosPendentes.size()-1);
//		    	no = new No(unwindProtectCleanupOctave);
//		    	noUnwindProtectOctave.adicionaFilho(no);
//		    	this.elementosPendentes.add(no);
//		    	pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("break")){
//		    	ehPalavraReservada = true;
//		    	while(!this.token.equals("\n")){
//		    		pegaToken(true);
//		    	}
//		    	No no = null;
//		    	OctaveBreak breakOctave = null;
//		    	breakOctave = new OctaveBreak();
//		    	no = new No(breakOctave);
//				((No)this.elementosPendentes.get(this.elementosPendentes.size()-1)).adicionaFilho(no);
//				pegaTokenProximaLinha();
//		    }
//		    if(this.token.toLowerCase().equals("continue")){
//		    	ehPalavraReservada = true;
//		    	while(!this.token.equals("\n")){
//		    		pegaToken(true);
//		    	}
//		    	No no = null;
//		    	OctaveContinue continueOctave = null;
//		    	continueOctave = new OctaveContinue();
//		    	no = new No(continueOctave);
//				((No)this.elementosPendentes.get(this.elementosPendentes.size()-1)).adicionaFilho(no);
//				pegaTokenProximaLinha();
//		    }
//		    if(isEnd(this.token.toLowerCase())){
//		    	ehPalavraReservada = true;
//		    	No no = this.elementosPendentes.get(this.elementosPendentes.size() - 1);
//		    	if((no.getElemento() instanceof OctaveOtherwiseSwitch)
//		    	  || no.getElemento() instanceof OctaveCaseSwitch){
//		    		this.elementosPendentes.remove(this.elementosPendentes.size() - 1);
//		    		this.elementosPendentes.remove(this.elementosPendentes.size() - 1);
//		    		pegaTokenProximaLinha();
//		    	}else{
//		    		if(this.token.equals("end") || this.token.equals("endfunction")){
//		    			anterior = this.token;
//		    		}
//		    		pegaTokenProximaLinha();
//		    		this.elementosPendentes.remove(this.elementosPendentes.size()-1);
//		    	}
//		    }
//		    if(!ehPalavraReservada){
//		    	if(this.token.equals("return") || this.token.equals("end") || this.token.equals("endfunction")){
//		    		anterior = this.token;
//		    		pegaTokenProximaLinha();
//		    	}else{
//			    	ehPalavraReservada = false;
//			    	String tokenTemp = this.token;
//			    	tokenTemp = tokenTemp + montaStringExpressao();
//			    	OctaveExpressao expressao = null;
//			    	Vector<ElementoOctave> tokensExpressao;
//			    	tokensExpressao = separaTokens(tokenTemp);
//			    	expressao = montaOctaveExpressao(tokensExpressao);
//			    	
//			    	No no = null;
//			    	no = new No(expressao);
//					((No)this.elementosPendentes.get(this.elementosPendentes.size()-1)).adicionaFilho(no);
//					pegaTokenProximaLinha();
//		    	}
//		    }
//		}
//	}
//
//	private List<Vector<ElementoOctave>> separaExpressoes(Vector<ElementoOctave> tokensExpressao) {
//		List<Vector<ElementoOctave>> lista = new ArrayList<Vector<ElementoOctave>>();
//		Vector<ElementoOctave> temp = new Vector<ElementoOctave>();
//		int indice = 0;
//		int cont = 0;
//		ElementoOctave elemento = null;
//		while(indice < tokensExpressao.size()){
//			elemento = tokensExpressao.get(indice);
//			if(elemento instanceof Token){
//				Token token = (Token)elemento;
//				if((token.getValor().equals("("))||(token.getValor().equals("["))||(token.getValor().equals("{"))){
//					cont++;
//				}
//                if((token.getValor().equals(")"))||(token.getValor().equals("]"))||(token.getValor().equals("}"))){
//					cont--;
//				}
//                if((token.getValor().equals(",") || (token.getValor().equals(";"))) && (cont == 0)){
//                	lista.add(temp);
//                	temp = new Vector<ElementoOctave>();
//                }else{
//                	temp.add(elemento);
//                }
//			}
//			indice++;
//		}
//		if(temp.size()>0)
//			lista.add(temp);
//		return lista;
//	}
//
//	private String montaStringExpressao(){
//		String tokenTemp = "";
//		int cont = 0;
//		while((this.indiceAtual < this.source.length)&&(!this.token.equals("\n"))){
//    		pegaToken(false);
//    		if((!this.token.equals("\r"))&&(!this.token.equals("\n"))){
//    		    tokenTemp = tokenTemp + this.token;
//    		    if(this.token.equals("(")||this.token.equals("[")||this.token.equals("{")){
//    		    	cont++;
//    		    	while(cont>0){
//    		    		pegaToken(false);
//    		    		if(this.token.equals("(")||this.token.equals("[")||this.token.equals("{")){
//    		    			cont++;
//    		    		}
//    		    		if(this.token.equals(")")||this.token.equals("]")||this.token.equals("}")){
//    		    			cont--;
//    		    		}
//    		    		tokenTemp = tokenTemp + this.token;
//    		    	}
//    		    }
//    		}
//    	}
//		return tokenTemp;
//	}
//	
//	private void pegaTokenProximaLinha(){
//		while((this.indiceAtual < this.source.length)&&(!this.token.equals("\n"))){
//			pegaToken(true);
//		}
//		while(this.token.equals("\n")||this.token.equals("\r")){
//			pegaToken(true);
//		}
//	}
//	
//	private OctaveExpressao montaOctaveExpressao(Vector<ElementoOctave> tokens){
//		Vector<ElementoOctave> notacaoPolonesa = transformaEmNotacaoPolonesa(tokens);
//		Vector<ElementoOctave> pilha = new Vector<ElementoOctave>();
//		ElementoOctave elemento;
//		while(notacaoPolonesa.size() > 0){
//			elemento = notacaoPolonesa.get(0);
//			notacaoPolonesa.remove(0);
//			Token token = null;
//			if(elemento instanceof Token){
//				token = (Token)elemento;
//				if((token.getValor().equals("'")) || (token.getValor().equals(".'"))){
//					OctaveExpressaoTranspose transpose = null;
//					if(pilha.get(pilha.size() - 1) instanceof Token){
//					    Token elementoTranspose = (Token)pilha.get(pilha.size() - 1);
//					    pilha.remove(pilha.size() - 1);
//					    OctaveIdentificador identificador = new OctaveIdentificador(elementoTranspose.getValor());
//						transpose = new OctaveExpressaoTranspose(identificador,token.getValor());
//					}else{
//						transpose = new OctaveExpressaoTranspose(pilha.get(pilha.size() - 1),token.getValor());
//						pilha.remove(pilha.size() - 1);
//					}
//					pilha.add(transpose);
//				}else{
//					if(token.getTipo().equals("Delimitador")){
//						if((token.getValor().equals("!"))||(token.getValor().equals("~"))){
//							ElementoOctave parte1 = pilha.get(pilha.size() - 1);
//							pilha.remove(pilha.size() - 1);
//							OctaveOperacaoLogica expLogica = new OctaveOperacaoLogica(parte1, null, token.getValor());
//							pilha.add(expLogica);
//						}else{
//							if((token.getValor().equals("&"))||(token.getValor().equals("&&"))
//							   ||(token.getValor().equals("|"))||(token.getValor().equals("||"))){
//								ElementoOctave parte2 = pilha.get(pilha.size() - 1);
//								pilha.remove(pilha.size() - 1);
//								ElementoOctave parte1 = pilha.get(pilha.size() - 1);
//								pilha.remove(pilha.size() - 1);
//								OctaveOperacaoLogica expLogica = new OctaveOperacaoLogica(parte1, parte2, token.getValor());
//								pilha.add(expLogica);
//							}else{
//								if(token.getValor().equals(":")){
//									ElementoOctave parte2 = null;
//									ElementoOctave parte1 = null;
//									if(pilha.size() > 0){
//										parte2 = pilha.get(pilha.size() - 1);
//										pilha.remove(pilha.size() - 1);
//										parte1 = pilha.get(pilha.size() - 1);
//										pilha.remove(pilha.size() - 1);
//									}
//									OctaveExpressaoLooping expLooping = new OctaveExpressaoLooping(parte1, parte2);
//									pilha.add(expLooping);
//								}else{
//									if((token.getValor().equals(">"))||(token.getValor().equals(">="))
//									  ||(token.getValor().equals("<"))||(token.getValor().equals("<="))
//									  ||(token.getValor().equals("=="))||(token.getValor().equals("!="))
//									  ||(token.getValor().equals("~="))){
//										ElementoOctave parte2 = pilha.get(pilha.size() - 1);
//										pilha.remove(pilha.size() - 1);
//										ElementoOctave parte1 = pilha.get(pilha.size() - 1);
//										pilha.remove(pilha.size() - 1);
//										OctaveOperacaoComparacao expComparacao = new OctaveOperacaoComparacao(parte1, parte2, token.getValor());
//										pilha.add(expComparacao);
//									}else{
//										if(token.getValor().equals("=")){
//											ElementoOctave parte2 = pilha.get(pilha.size() - 1);
//											pilha.remove(pilha.size() - 1);
//											ElementoOctave parte1 = pilha.get(pilha.size() - 1);
//											pilha.remove(pilha.size() - 1);
//											OctaveExpressaoAtribuicao assign = new OctaveExpressaoAtribuicao(parte1,parte2);
//											pilha.add(assign);
//										}else{
//											ElementoOctave parte2 = pilha.get(pilha.size() - 1);
//											pilha.remove(pilha.size() - 1);
//											ElementoOctave parte1 = pilha.get(pilha.size() - 1);
//											pilha.remove(pilha.size() - 1);
//											OctaveExpressaoBinaria expBinaria = new OctaveExpressaoBinaria(parte1, parte2, token.getValor());
//											pilha.add(expBinaria);
//										}
//									}
//								}
//							}
//						}
//					}else{
//						if(token.getTipo().equals("Identificador")){
//							if(token.getValor().toLowerCase().equals("continue")){
//								pilha.add(new OctaveContinue());
//							}else{
//								if(token.getValor().toLowerCase().equals("break")){
//									pilha.add(new OctaveBreak());
//								}else{
//									OctaveIdentificador ident = new OctaveIdentificador(token.getValor());
//									OctaveVariavel var = new OctaveVariavel(ident);
//									pilha.add(var);
//								}
//							}
//						}else{
//							if(token.getTipo().equals("Numero")){
//								OctaveConstante constante = new OctaveConstante(token.getValor());
//								pilha.add(constante);
//							}
//						}
//					}
//				}
//			}else{
//				if(elemento instanceof OctaveExpressaoUnaria){
//					OctaveExpressaoUnaria expUnaria = (OctaveExpressaoUnaria)elemento;
//					if(expUnaria.getElemento() == null){
//						ElementoOctave proximoElemento = notacaoPolonesa.get(0);
//						if(proximoElemento instanceof Token){
//							Token proximo = (Token)proximoElemento;
//							OctaveIdentificador ident = new OctaveIdentificador(proximo.getValor());
//							if(proximo.getTipo().equals("Identificador")){
//								OctaveVariavel var = new OctaveVariavel(ident);
//								proximoElemento = var;
//							}else{
//								if(proximo.getTipo().equals("Numero")){
//									OctaveConstante constante = new OctaveConstante(proximo.getValor());
//									proximoElemento = constante;
//								}
//							}
//						}
//						expUnaria.setElemento(proximoElemento);
//						notacaoPolonesa.remove(0);
//					}
//					pilha.add(expUnaria);
//				}else
//				    pilha.add(elemento);
//			}
//		}
//		elemento = pilha.get(pilha.size() - 1);
//		OctaveExpressao exp = null;
//		if(elemento instanceof OctaveExpressao)
//			exp = (OctaveExpressao)elemento;
//		else{
//			if(elemento instanceof Token){
//				Token token = (Token)elemento;
//				if(token.getTipo().equals("Numero")){
//					OctaveConstante constante = new OctaveConstante(token.getValor());
//					exp = new OctaveExpressao();
//				    exp.setElemento(constante);
//				}else{
//					if(token.getTipo().equals("Identificador")){
//						OctaveIdentificador ident = new OctaveIdentificador(token.getValor());
//						OctaveVariavel var = new OctaveVariavel(ident);
//						exp = new OctaveExpressao();
//					    exp.setElemento(var);
//					}
//				}
//			}else{
//			    exp = new OctaveExpressao();
//			    exp.setElemento(elemento);
//			}
//		}
//		return exp;
//	}
//	
//	private Vector<ElementoOctave> transformaEmNotacaoPolonesa(Vector<ElementoOctave> tokens){
//		Vector<ElementoOctave> pilha = new Vector<ElementoOctave>();
//		Vector<ElementoOctave> saida = new Vector<ElementoOctave>();
//		int indice = 0;
//		Token token = null;
//		Token tokenAnterior = null;
//		while(indice != tokens.size()){
//			if(tokens.get(indice) instanceof Token){
//					tokenAnterior = token;
//					token = (Token)tokens.get(indice);
//					if((!token.getValor().equals(";")) && (!token.getValor().equals("\n")) && (!token.getValor().equals("\r"))){
//						if(token.getTipo().equals("Delimitador")){
//							if(token.getValor().equals("(")){
//								pilha.add(token);
//							}else{
//								if(token.getValor().equals(")")){
//									while(!(((Token)pilha.get(pilha.size()-1)).getValor().equals("("))){
//										saida.add(pilha.get(pilha.size()-1));
//										pilha.remove(pilha.size()-1);
//									}
//									pilha.remove(pilha.size()-1);
//								}else{
//									if(token.getValor().equals("{")){
//										int indiceTemp = indice + 1;
//										Vector<ElementoOctave> pilhaTemp = new Vector<ElementoOctave>();
//										pilhaTemp.add(token);
//										String valorArray = "{";
//										Token proximoToken = null;
//										while(pilhaTemp.size() > 0){
//											proximoToken = (Token)tokens.get(indiceTemp);
//											tokens.remove(indiceTemp);
//											valorArray = valorArray + proximoToken.getValor();
//											if(proximoToken.getValor().equals("}"))
//												pilhaTemp.remove(pilhaTemp.size() - 1);
//											if(proximoToken.getValor().equals("{"))
//												pilhaTemp.add(proximoToken);
//										}
//										
//										if(indiceTemp < tokens.size())
//										    proximoToken = (Token)tokens.get(indiceTemp);
//										
//										if(proximoToken.getValor().equals("(")){
//											valorArray = valorArray + proximoToken.getValor();
//											pilhaTemp.add(proximoToken);
//											proximoToken = null;
//											tokens.remove(indiceTemp);
//											while(pilhaTemp.size() > 0){
//												proximoToken = (Token)tokens.get(indiceTemp);
//												tokens.remove(indiceTemp);
//												valorArray = valorArray + proximoToken.getValor();
//												if(proximoToken.getValor().equals(")"))
//													pilhaTemp.remove(pilhaTemp.size() - 1);
//												if(proximoToken.getValor().equals("("))
//													pilhaTemp.add(proximoToken);
//											}
//										}
//										tokens.remove(indice);
//										if(valorArray.contains(";")||valorArray.contains("\n")||valorArray.contains("=")){
//											OctaveStructure structure = new OctaveStructure(valorArray);
//											tokens.insertElementAt(structure, indice);
//											saida.add(structure);
//										}else{
//											OctaveArray array = new OctaveArray(valorArray);
//											if((tokenAnterior != null) && (tokenAnterior.getTipo().equals("Identificador"))){
//												OctaveIdentificador arrayIdent = new OctaveIdentificador(tokenAnterior.getValor());
//												array.setIdentificador(arrayIdent);
//												saida.remove(saida.size()-1);
//											}
//											tokens.insertElementAt(array, indice);
//											if(tokenAnterior != null){
//												tokens.remove(indice - 1);
//												indice--;
//											}
//											saida.add(array);
//										}
//									}else{
//										if(token.getValor().equals("[")){
//											int indiceTemp = indice + 1;
//											Vector<ElementoOctave> pilhaTemp = new Vector<ElementoOctave>();
//											pilhaTemp.add(token);
//											String valorMatriz = "[";
//											Token proximoToken = null;
//											while(pilhaTemp.size() > 0){
//												proximoToken = (Token)tokens.get(indiceTemp);
//												tokens.remove(indiceTemp);
//												valorMatriz = valorMatriz + proximoToken.getValor();
//												if(proximoToken.getValor().equals("]"))
//													pilhaTemp.remove(pilhaTemp.size() - 1);
//												if(proximoToken.getValor().equals("["))
//													pilhaTemp.add(proximoToken);
//											}
//											if(indiceTemp < tokens.size())
//											    proximoToken = (Token)tokens.get(indiceTemp);
//											
//											if(proximoToken.getValor().equals("(")){
//												valorMatriz = valorMatriz + proximoToken.getValor();
//												pilhaTemp.add(proximoToken);
//												proximoToken = null;
//												tokens.remove(indiceTemp);
//												while(pilhaTemp.size() > 0){
//													proximoToken = (Token)tokens.get(indiceTemp);
//													tokens.remove(indiceTemp);
//													valorMatriz = valorMatriz + proximoToken.getValor();
//													if(proximoToken.getValor().equals(")"))
//														pilhaTemp.remove(pilhaTemp.size() - 1);
//													if(proximoToken.getValor().equals("("))
//														pilhaTemp.add(proximoToken);
//												}
//											}
//											tokens.remove(indice);
//											OctaveMatriz matriz = new OctaveMatriz(valorMatriz);
//											if((tokenAnterior != null) && (tokenAnterior.getTipo().equals("Identificador"))){
//												OctaveIdentificador matrizIdent = new OctaveIdentificador(tokenAnterior.getValor());
//												matriz.setIdentificador(matrizIdent);
//												saida.remove(saida.size()-1);
//											}
//											tokens.insertElementAt(matriz, indice);
//											if(tokenAnterior != null){
//												tokens.remove(indice - 1);
//												indice--;
//											}
//											saida.add(matriz);
//										}else{
//											if(token.getValor().equals("@")){
//												int indiceTemp = indice + 1;
//												Token tokenTemp;
//												Vector<ElementoOctave> tokensTemp = new Vector<ElementoOctave>();
//												tokensTemp.add(token);
//												while(indiceTemp < tokens.size()){
//													tokenTemp = (Token)tokens.get(indiceTemp);
//													if((!tokenTemp.getValor().equals(";")) && (!tokenTemp.getValor().equals("\n")) && (!tokenTemp.getValor().equals("\r")))
//														tokensTemp.add(tokenTemp);
//													tokens.remove(indiceTemp);
//												}
//												OctaveAnonymousFunction anonymousFunc = montaAnonymousFunction(tokensTemp);
//												tokens.remove(indice);
//												tokens.insertElementAt(anonymousFunc, indice);
//												saida.add(anonymousFunc);
//											}else{
//												if((token.getValor().equals("'")) || (token.getValor().equals("\"")) || (token.getValor().equals(".'"))){
//													boolean ehFimDaString = false;
//													String conteudo = "";
//													int indiceTemp = indice + 1;
//													Token proximoToken = null;
//													if(indiceTemp < tokens.size())
//													    proximoToken = (Token)tokens.get(indiceTemp);
//													if((token.getValor().equals("'")) || (token.getValor().equals(".'"))){
//														if(token.getValor().equals(".'")){
//															ElementoOctave elemento = saida.get(saida.size() - 1);
//															if(elemento instanceof Token){
//																Token tokenTranspose = (Token) elemento;
//																if(tokenTranspose.getTipo().equals("Identificador")){
//																	elemento = new OctaveVariavel(new OctaveIdentificador(tokenTranspose.getValor()));
//																	OctaveExpressaoTranspose expTranspose = new OctaveExpressaoTranspose(elemento,token.getValor());
//																	saida.remove(saida.size() - 1);
//																	saida.add(expTranspose);
//																}else{
//																	if(tokenTranspose.getTipo().equals("Numero")){
//																		elemento = new OctaveConstante(tokenTranspose.getValor());
//																		OctaveExpressaoTranspose expTranspose = new OctaveExpressaoTranspose(elemento,token.getValor());
//																		saida.remove(saida.size() - 1);
//																		saida.add(expTranspose);
//																	}else{
//																		saida.add(token);
//																	}
//																}
//															}else{
//																OctaveExpressaoTranspose expTranspose = new OctaveExpressaoTranspose(elemento,token.getValor());
//																saida.remove(saida.size() - 1);
//																saida.add(expTranspose);
//															}
//														}else{
//															if((tokenAnterior == null) || ((tokenAnterior != null) && (tokenAnterior.getTipo().equals("Delimitador")) && (!tokenAnterior.getValor().equals("[")) && (!tokenAnterior.getValor().equals("{")))){
//																conteudo = conteudo + "'";
//																String valorAdicionado = null;
//																while(!ehFimDaString){
//																	valorAdicionado = proximoToken.getValor();
//																	conteudo = conteudo + valorAdicionado;
//																	tokens.remove(indiceTemp);
//																	if(indiceTemp < tokens.size())
//																		proximoToken = (Token)tokens.get(indiceTemp);
//																	else
//																		proximoToken = null;
//																	if( proximoToken!= null && valorAdicionado.equals("'") && proximoToken.getValor().equals("'")){
//																		conteudo = conteudo + proximoToken.getValor();
//																		tokens.remove(indiceTemp);
//																		proximoToken = (Token)tokens.get(indiceTemp);
//																	}else{
//																		if((proximoToken== null) || (valorAdicionado.equals("'") && !proximoToken.getValor().equals("'")))
//																		    ehFimDaString = true;
//																	}
//																}
//																tokens.remove(indice);
//																OctaveString octString = new OctaveString(conteudo);
//																tokens.insertElementAt(octString, indice);
//																saida.add(octString);
//															}
//														}
//													}else{
//														conteudo = conteudo + "\"";
//														String valorAdicionado = null;
//														while(!ehFimDaString){
//															valorAdicionado = proximoToken.getValor();
//															conteudo = conteudo + valorAdicionado;
//															tokens.remove(indiceTemp);
//															if(indiceTemp < tokens.size())
//																proximoToken = (Token)tokens.get(indiceTemp);
//															else
//																proximoToken = null;
//															if( proximoToken!= null && valorAdicionado.equals("\"") && proximoToken.getValor().equals("\"")){
//																conteudo = conteudo + proximoToken.getValor();
//																tokens.remove(indiceTemp);
//																proximoToken = (Token)tokens.get(indiceTemp);
//															}else{
//																if((proximoToken== null) || (valorAdicionado.equals("\"") && !proximoToken.getValor().equals("\"")))
//																    ehFimDaString = true;
//															}
//														}
//														tokens.remove(indice);
//														OctaveString octString = new OctaveString(conteudo);
//														tokens.insertElementAt(octString, indice);
//														saida.add(octString);
//													}
//												}else{
//													if(((token.getValor().equals("-"))&&(indice == 0))||
//													   ((token.getValor().equals("-"))&&(tokenAnterior != null)&&(tokenAnterior.getTipo().equals("Delimitador")))||
//													   ((token.getValor().equals("-"))&&(tokenAnterior != null)&&(tokenAnterior.getValor().equals(":")))){
//														OctaveExpressaoUnaria expUnaria = new OctaveExpressaoUnaria("-");
//														saida.add(expUnaria);
//													}else{
//														if(((token.getValor().equals("+"))&&(indice == 0))||
//														   ((token.getValor().equals("+"))&&(tokenAnterior != null)&&(tokenAnterior.getTipo().equals("Delimitador")))||
//														   ((token.getValor().equals("+"))&&(tokenAnterior != null)&&(tokenAnterior.getValor().equals(":")))){
//																OctaveExpressaoUnaria expUnaria = new OctaveExpressaoUnaria("+");
//																saida.add(expUnaria);
//														}else{
//															if(token.getValor().equals("++") || token.getValor().equals("--")){
//																
//																OctaveIdentificador identificador = null;
//																if((tokenAnterior != null)&&(tokenAnterior.getTipo().equals("Identificador"))){
//																	identificador = new OctaveIdentificador(tokenAnterior.getValor());
//																}else{
//																	Token proximoToken = (Token)tokens.get(indice + 1);
//																	identificador = new OctaveIdentificador(proximoToken.getValor());
//																	indice++;
//																}
//																OctaveVariavel variavel = new OctaveVariavel(identificador);
//																if(token.getValor().equals("++")){
//																	OctaveExpressaoAutoIncremento incremento = new OctaveExpressaoAutoIncremento(variavel);
//																	saida.add(incremento);
//																}else{
//																	OctaveExpressaoAutoDecremento decremento = new OctaveExpressaoAutoDecremento(variavel);
//																	saida.add(decremento);
//																}
//															}else{
//																if(token.getPrecedencia() != -1){
//																	Token topoPilha = null;
//																	if(pilha.size() > 0){
//																		topoPilha = (Token)pilha.get(pilha.size()-1);
//																		while((pilha.size() > 0) && (topoPilha.getPrecedencia() != -1) && (topoPilha.getPrecedencia() <= token.getPrecedencia())){
//																			saida.add(pilha.get(pilha.size()-1));
//																			pilha.remove(pilha.size()-1);
//																			if(pilha.size() > 0)
//																			    topoPilha = (Token)pilha.get(pilha.size()-1);
//																		}
//																	}
//																	pilha.add(token);
//																}
//															}
//														}
//													}
//												}
//											}
//										}
//									}
//								}
//							}
//						}else{
//							if(token.getTipo().equals("Numero")){
//								saida.add(token);
//							}else{
//								if(token.getTipo().equals("Identificador")){
//									int indiceTemp = indice+1;
//									Token proximoToken = null;
//									if(indiceTemp < tokens.size())
//									    proximoToken = (Token)tokens.get(indiceTemp);
//									if((proximoToken != null) && (proximoToken.getValor().equals("("))){
//										Vector<ElementoOctave> tokensTemp = new Vector<ElementoOctave>();
//										Vector<ElementoOctave> pilhaTemp = new Vector<ElementoOctave>();
//										pilhaTemp.add(proximoToken);
//										tokens.remove(indiceTemp);
//										while(pilhaTemp.size()!=0){
//											proximoToken = (Token)tokens.get(indiceTemp);
//											if((proximoToken.getValor().equals("("))||(proximoToken.getValor().equals("["))||(proximoToken.getValor().equals("{"))){
//												pilhaTemp.add(proximoToken);
//											}
//											if((proximoToken.getValor().equals(")"))||(proximoToken.getValor().equals("]"))||(proximoToken.getValor().equals("}"))){
//												pilhaTemp.remove(pilhaTemp.size()-1);
//											}
//											tokensTemp.add(proximoToken);
//											tokens.remove(indiceTemp);
//										}
//										tokensTemp.remove(tokensTemp.size()-1);
//										OctaveIdentificador ident = new OctaveIdentificador(token.getValor());
//										OctaveChamadaFuncao chamada;
//										tokens.remove(indice);
//										if(tokensTemp.size() == 0){
//											chamada = new OctaveChamadaFuncao(ident);
//											tokens.insertElementAt(chamada, indice);
//										}else{
//											chamada = montaChamadaFuncao(ident,tokensTemp);
//											tokens.insertElementAt(chamada, indice);
//										}
//										saida.add(chamada);
//									}else{
//										if(token.getValor().startsWith("@")){
//											OctaveIdentificador ident = new OctaveIdentificador(token.getValor());
//											OctaveHandleFunction handleFunc = new OctaveHandleFunction(ident);
//											saida.add(handleFunc);
//										}else{
//											if(token.getValor().contains(".")){
//												OctaveVariavelStructure varStructure = new OctaveVariavelStructure(token.getValor());
//												saida.add(varStructure);
//											}else{
//												saida.add(token);
//											}
//										}
//									}
//								}
//							}
//						}
//					}				
//			}else{
//				token = null;
//				if(tokens.get(indice) instanceof OctaveExpressaoUnaria){
//					OctaveExpressaoUnaria expUnaria = (OctaveExpressaoUnaria)tokens.get(indice);
//					ElementoOctave proximoElemento = tokens.get(indice + 1);
//					if(expUnaria.getElemento() == null){
//						if(proximoElemento instanceof Token){
//							Token proximo = (Token)proximoElemento;
//							OctaveIdentificador ident = new OctaveIdentificador(proximo.getValor());
//							if(proximo.getTipo().equals("Identificador")){
//								OctaveVariavel var = new OctaveVariavel(ident);
//								proximoElemento = var;
//								tokens.remove(indice + 1);
//							}else{
//								if(proximo.getTipo().equals("Numero")){
//									OctaveConstante constante = new OctaveConstante(proximo.getValor());
//									proximoElemento = constante;
//									tokens.remove(indice + 1);
//								}else{
//									if(proximo.getValor().equals("(")){
//										tokens.remove(indice+1);
//										int cont = 1;
//										Vector<ElementoOctave> elementos = new Vector<ElementoOctave>(); 
//										while(cont > 0){
//											proximoElemento = tokens.get(indice + 1);
//											if((proximoElemento instanceof Token)&&(((Token)proximoElemento)).getValor().equals("(")){
//												cont++;
//											}
//											if((proximoElemento instanceof Token)&&(((Token)proximoElemento)).getValor().equals(")")){
//												cont--;
//											}
//											elementos.add(proximoElemento);
//											tokens.remove(indice+1);
//										}
//										elementos.remove(elementos.size() - 1);
//										proximoElemento = montaOctaveExpressao(elementos);
//									}
//								}
//							}
//						}
//						expUnaria.setElemento(proximoElemento);
//					}
//					saida.add(expUnaria);
//				}else{
//				    saida.add(tokens.get(indice));
//				}
//			}
//			indice++;
//		}
//		while(pilha.size() > 0){
//			saida.add(pilha.get(pilha.size() - 1));
//			pilha.remove(pilha.size() - 1);
//		}
//		return saida;
//	}
//	
//	private OctaveAnonymousFunction montaAnonymousFunction(Vector<ElementoOctave> tokensAnonymousFunction) {
//		OctaveAnonymousFunction anonymousFunc = new OctaveAnonymousFunction();
//		int indice = 0;
//		boolean ehExpressao = false;
//		Vector<ElementoOctave> tokensTemp = new Vector<ElementoOctave>();
//		Vector<Token> pilha = new Vector<Token>();
//		
//		tokensAnonymousFunction.remove(indice);
//		pilha.add((Token)tokensAnonymousFunction.get(indice));
//		tokensAnonymousFunction.remove(indice);
//		Token token = null, tokenAnterior = null;
//		while(pilha.size() > 0){
//			tokenAnterior = token;
//			token = (Token)tokensAnonymousFunction.get(indice);
//			if(token.getValor().equals(")")){
//				pilha.remove(pilha.size() - 1);
//			}else{
//				if(token.getValor().equals(",")){
//					if(ehExpressao){
//						OctaveExpressao expressao = montaOctaveExpressao(tokensTemp);
//						anonymousFunc.adicionaParametro(expressao);
//					}else{
//						if(tokensTemp.get(0) instanceof OctaveExpressaoUnaria){
//							OctaveExpressaoUnaria expUnaria = (OctaveExpressaoUnaria)tokensTemp.get(0);
//							if(expUnaria.getElemento() == null){
//								ElementoOctave proximoElemento = tokensTemp.get(1);
//								if(proximoElemento instanceof Token){
//									Token proximo = (Token)proximoElemento;
//									OctaveIdentificador ident = new OctaveIdentificador(proximo.getValor());
//									if(proximo.getTipo().equals("Identificador")){
//										OctaveVariavel var = new OctaveVariavel(ident);
//										proximoElemento = var;
//									}else{
//										if(proximo.getTipo().equals("Numero")){
//											OctaveConstante constante = new OctaveConstante(proximo.getValor());
//											proximoElemento = constante;
//										}else{
//											if(proximo.getValor().equals("(")){
//												tokensTemp.remove(indice+1);
//												int cont = 1;
//												Vector<ElementoOctave> elementos = new Vector<ElementoOctave>(); 
//												while(cont > 0){
//													proximoElemento = tokensTemp.get(indice + 1);
//													if((proximoElemento instanceof Token)&&(((Token)proximoElemento)).getValor().equals("(")){
//														cont++;
//													}
//													if((proximoElemento instanceof Token)&&(((Token)proximoElemento)).getValor().equals(")")){
//														cont--;
//													}
//													elementos.add(proximoElemento);
//													tokensTemp.remove(indice+1);
//												}
//												elementos.remove(elementos.size() - 1);
//												proximoElemento = montaOctaveExpressao(elementos);
//											}
//										}
//									}
//								}
//								expUnaria.setElemento(proximoElemento);
//							}
//							anonymousFunc.adicionaParametro(expUnaria);
//						}else{
//							if(tokensTemp.get(0) instanceof Token){
//								if(((Token)tokensTemp.get(0)).getTipo().equals("Identificador")){
//									anonymousFunc.adicionaParametro(new OctaveIdentificador(((Token)tokensTemp.get(0)).getValor()));
//								}else{
//									if(((Token)tokensTemp.get(0)).getTipo().equals("Numero")){
//									    anonymousFunc.adicionaParametro(new OctaveConstante(((Token)tokensTemp.get(0)).getValor()));
//									}else{
//										anonymousFunc.adicionaParametro(tokensTemp.get(0));
//									}
//								}
//							}else{
//								anonymousFunc.adicionaParametro(tokensTemp.get(0));
//							}
//						}
//					}
//					tokensTemp = new Vector<ElementoOctave>();
//					ehExpressao = false;
//				}else{
//					if(token.getTipo().equals("Identificador")){
//						int indiceTemp = indice + 1;
//						Token proximoToken = null;
//						if(indiceTemp < tokensAnonymousFunction.size())
//						    proximoToken = (Token)tokensAnonymousFunction.get(indiceTemp);
//						if((proximoToken != null) && (proximoToken.getValor().equals("("))){
//							Vector<ElementoOctave> tokensChamadaFuncao = new Vector<ElementoOctave>();
//							Vector<ElementoOctave> pilhaTemp = new Vector<ElementoOctave>();
//							pilhaTemp.add(proximoToken);
//							tokensAnonymousFunction.remove(indiceTemp);
//							OctaveChamadaFuncao chamadaInterna;
//							while(pilhaTemp.size()!=0){
//								proximoToken = (Token)tokensAnonymousFunction.get(indiceTemp);
//								if((proximoToken.getValor().equals("("))||(proximoToken.getValor().equals("["))||(proximoToken.getValor().equals("{"))){
//									pilhaTemp.add(proximoToken);
//								}
//								if((proximoToken.getValor().equals(")"))||(proximoToken.getValor().equals("]"))||(proximoToken.getValor().equals("}"))){
//									pilhaTemp.remove(pilhaTemp.size()-1);
//								}
//								tokensChamadaFuncao.add(proximoToken);
//								tokensAnonymousFunction.remove(indiceTemp);
//							}
//							tokensChamadaFuncao.remove(tokensChamadaFuncao.size()-1);
//							OctaveIdentificador ident = new OctaveIdentificador(token.getValor());
//							tokensAnonymousFunction.remove(indice);
//							if(tokensChamadaFuncao.size() == 0){
//								chamadaInterna = new OctaveChamadaFuncao(ident);
//								tokensAnonymousFunction.insertElementAt(chamadaInterna, indice);
//							}else{
//								chamadaInterna = montaChamadaFuncao(ident,tokensChamadaFuncao);
//								tokensAnonymousFunction.insertElementAt(chamadaInterna, indice);
//							}
//							tokensTemp.add(chamadaInterna);
//						}else{
//							if(token.getValor().startsWith("@")){
//								OctaveIdentificador ident = new OctaveIdentificador(token.getValor());
//								OctaveHandleFunction handleFunc = new OctaveHandleFunction(ident);
//								tokensTemp.add(handleFunc);
//							}else{
//								if(token.getValor().contains(".")){
//									OctaveVariavelStructure varStructure = new OctaveVariavelStructure(token.getValor());
//									tokensTemp.add(varStructure);
//								}else{
//									tokensTemp.add(token);
//								}
//							}
//						}
//					}else{
//						if(token.getTipo().equals("Numero")){
//							tokensTemp.add(token);
//						}else{
//							if(token.getTipo().equals("Delimitador")){
//								if(token.getValor().equals("{")){
//									int indiceTemp = indice + 1;
//									Vector<ElementoOctave> pilhaTemp = new Vector<ElementoOctave>();
//									pilhaTemp.add(token);
//									String valorArray = "{";
//									Token proximoToken = null;
//									while(pilhaTemp.size() > 0){
//										proximoToken = (Token)tokensAnonymousFunction.get(indiceTemp);
//										tokensAnonymousFunction.remove(indiceTemp);
//										valorArray = valorArray + proximoToken.getValor();
//										if(proximoToken.getValor().equals("}"))
//											pilhaTemp.remove(pilhaTemp.size() - 1);
//										if(proximoToken.getValor().equals("{"))
//											pilhaTemp.add(proximoToken);
//									}
//									if(indiceTemp < tokensAnonymousFunction.size())
//									    proximoToken = (Token)tokensAnonymousFunction.get(indiceTemp);
//									
//									if(proximoToken.getValor().equals("(")){
//										valorArray = valorArray + proximoToken.getValor();
//										pilhaTemp.add(proximoToken);
//										proximoToken = null;
//										tokensAnonymousFunction.remove(indiceTemp);
//										while(pilhaTemp.size() > 0){
//											proximoToken = (Token)tokensAnonymousFunction.get(indiceTemp);
//											tokensAnonymousFunction.remove(indiceTemp);
//											valorArray = valorArray + proximoToken.getValor();
//											if(proximoToken.getValor().equals(")"))
//												pilhaTemp.remove(pilhaTemp.size() - 1);
//											if(proximoToken.getValor().equals("("))
//												pilhaTemp.add(proximoToken);
//										}
//									}
//									tokensAnonymousFunction.remove(indice);
//									if(valorArray.contains(";")||valorArray.contains("\n")||valorArray.contains("=")){
//										OctaveStructure structure = new OctaveStructure(valorArray);
//										tokensAnonymousFunction.insertElementAt(structure, indice);
//										tokensTemp.add(structure);
//									}else{
//										OctaveArray array = new OctaveArray(valorArray);
//										if((tokenAnterior != null) && (tokenAnterior.getTipo().equals("Identificador"))){
//											OctaveIdentificador arrayIdent = new OctaveIdentificador(tokenAnterior.getValor());
//											array.setIdentificador(arrayIdent);
//											tokensTemp.remove(tokensTemp.size()-1);
//										}
//										tokensAnonymousFunction.insertElementAt(array, indice);
//										tokensTemp.add(array);
//									}
//								}else{
//									if(token.getValor().equals("[")){
//										int indiceTemp = indice + 1;
//										Vector<ElementoOctave> pilhaTemp = new Vector<ElementoOctave>();
//										pilhaTemp.add(token);
//										String valorMatriz = "[";
//										Token proximoToken = null;
//										while(pilhaTemp.size() > 0){
//											proximoToken = (Token)tokensAnonymousFunction.get(indiceTemp);
//											tokensAnonymousFunction.remove(indiceTemp);
//											valorMatriz = valorMatriz + proximoToken.getValor();
//											if(proximoToken.getValor().equals("]"))
//												pilhaTemp.remove(pilhaTemp.size() - 1);
//											if(proximoToken.getValor().equals("["))
//												pilhaTemp.add(proximoToken);
//										}
//										if(indiceTemp < tokensAnonymousFunction.size())
//										    proximoToken = (Token)tokensAnonymousFunction.get(indiceTemp);
//										
//										if(proximoToken.getValor().equals("(")){
//											valorMatriz = valorMatriz + proximoToken.getValor();
//											pilhaTemp.add(proximoToken);
//											proximoToken = null;
//											tokensAnonymousFunction.remove(indiceTemp);
//											while(pilhaTemp.size() > 0){
//												proximoToken = (Token)tokensAnonymousFunction.get(indiceTemp);
//												tokensAnonymousFunction.remove(indiceTemp);
//												valorMatriz = valorMatriz + proximoToken.getValor();
//												if(proximoToken.getValor().equals(")"))
//													pilhaTemp.remove(pilhaTemp.size() - 1);
//												if(proximoToken.getValor().equals("("))
//													pilhaTemp.add(proximoToken);
//											}
//										}
//										tokensAnonymousFunction.remove(indice);
//										OctaveMatriz matriz = new OctaveMatriz(valorMatriz);
//										if((tokenAnterior != null) && (tokenAnterior.getTipo().equals("Identificador"))){
//											OctaveIdentificador matrizIdent = new OctaveIdentificador(tokenAnterior.getValor());
//											matriz.setIdentificador(matrizIdent);
//											tokensTemp.remove(tokensTemp.size()-1);
//										}
//										tokensAnonymousFunction.insertElementAt(matriz, indice);
//										tokensTemp.add(matriz);
//									}else{
//										if(token.getValor().equals("@")){
//											Vector<ElementoOctave> pilhaAnonymousFunc = new Vector<ElementoOctave>();
//											Vector<ElementoOctave> tokensAnonymousFunctionInto = new Vector<ElementoOctave>();
//											int indiceTemp = indice + 1;
//											boolean ehFimHandleFunction = false;
//											Token tokenTemp = null;
//											tokensAnonymousFunctionInto.add(token);
//											while(!ehFimHandleFunction){
//												if(indiceTemp < tokensAnonymousFunction.size()){
//													tokenTemp = (Token)tokensAnonymousFunction.get(indiceTemp);
//													if((tokenTemp.getValor().equals("(")) || (tokenTemp.getValor().equals("[")) || (tokenTemp.getValor().equals("{"))){
//														pilhaAnonymousFunc.add(tokenTemp);
//													}
//													if(tokenTemp.getValor().equals(")")){
//														pilhaAnonymousFunc.remove(pilhaAnonymousFunc.size()-1);
//													}
//													if((tokenTemp.getValor().equals(",")) && (pilhaAnonymousFunc.size() == 0)){
//														ehFimHandleFunction = true;
//													}else{
//														tokensAnonymousFunctionInto.add(tokenTemp);
//													    tokensAnonymousFunction.remove(indiceTemp);
//													}
//												}else{
//													ehFimHandleFunction = true;
//												}
//											}
//											OctaveAnonymousFunction anonymousFuncInto = montaAnonymousFunction(tokensAnonymousFunctionInto);
//											tokensAnonymousFunction.remove(indice);
//											tokensAnonymousFunction.insertElementAt(anonymousFuncInto,indice);
//											tokensTemp.add(anonymousFuncInto);
//										}else{
//											if((token.getValor().equals("'")) || (token.getValor().equals("\"")) || (token.getValor().equals(".'"))){
//												boolean ehFimDaString = false;
//												String conteudo = "";
//												int indiceTemp = indice + 1;
//												Token proximoToken = null;
//												if(indiceTemp < tokensAnonymousFunction.size())
//												    proximoToken = (Token)tokensAnonymousFunction.get(indiceTemp);
//												if((token.getValor().equals("'"))||(token.getValor().equals(".'"))){
//													if(token.getValor().equals(".'")){
//														ElementoOctave elemento = tokensTemp.get(tokensTemp.size() - 1);
//														if(elemento instanceof Token){
//															Token tokenTranspose = (Token) elemento;
//															if(tokenTranspose.getTipo().equals("Identificador")){
//																elemento = new OctaveVariavel(new OctaveIdentificador(tokenTranspose.getValor()));
//																OctaveExpressaoTranspose expTranspose = new OctaveExpressaoTranspose(elemento,token.getValor());
//																tokensTemp.remove(tokensTemp.size() - 1);
//																tokensTemp.add(expTranspose);
//															}else{
//																if(tokenTranspose.getTipo().equals("Numero")){
//																	elemento = new OctaveConstante(tokenTranspose.getValor());
//																	OctaveExpressaoTranspose expTranspose = new OctaveExpressaoTranspose(elemento,token.getValor());
//																	tokensTemp.remove(tokensTemp.size() - 1);
//																	tokensTemp.add(expTranspose);
//																}else{
//																	tokensTemp.add(token);
//																}
//															}
//														}else{
//															OctaveExpressaoTranspose expTranspose = new OctaveExpressaoTranspose(elemento,token.getValor());
//															tokensTemp.remove(tokensTemp.size() - 1);
//															tokensTemp.add(expTranspose);
//														}
//													}else{
//														if((tokenAnterior == null) || ((tokenAnterior != null) && (tokenAnterior.getTipo().equals("Delimitador")) && (!tokenAnterior.getValor().equals("[")) && (!tokenAnterior.getValor().equals("{")))){
//															conteudo = conteudo + "'";
//															String valorAdicionado = null;
//															while(!ehFimDaString){
//																valorAdicionado = proximoToken.getValor();
//																conteudo = conteudo + valorAdicionado;
//																tokensAnonymousFunction.remove(indiceTemp);
//																if(indiceTemp < tokensAnonymousFunction.size())
//																	proximoToken = (Token)tokensAnonymousFunction.get(indiceTemp);
//																else
//																	proximoToken = null;
//																if( proximoToken!= null && valorAdicionado.equals("'") && proximoToken.getValor().equals("'")){
//																	conteudo = conteudo + proximoToken.getValor();
//																	tokensAnonymousFunction.remove(indiceTemp);
//																	proximoToken = (Token)tokensAnonymousFunction.get(indiceTemp);
//																}else{
//																	if((proximoToken== null) || (valorAdicionado.equals("'") && !proximoToken.getValor().equals("'")))
//																	    ehFimDaString = true;
//																}
//															}
//															tokensAnonymousFunction.remove(indice);
//															OctaveString octString = new OctaveString(conteudo);
//															tokensAnonymousFunction.insertElementAt(octString, indice);
//															tokensTemp.add(octString);
//														}
//													}
//												}else{
//													conteudo = conteudo + "\"";
//													String valorAdicionado = null;
//													while(!ehFimDaString){
//														valorAdicionado = proximoToken.getValor();
//														conteudo = conteudo + valorAdicionado;
//														tokensAnonymousFunction.remove(indiceTemp);
//														if(indiceTemp < tokensAnonymousFunction.size())
//															proximoToken = (Token)tokensAnonymousFunction.get(indiceTemp);
//														else
//															proximoToken = null;
//														if( proximoToken!= null && valorAdicionado.equals("\"") && proximoToken.getValor().equals("\"")){
//															conteudo = conteudo + proximoToken.getValor();
//															tokensAnonymousFunction.remove(indiceTemp);
//															proximoToken = (Token)tokensAnonymousFunction.get(indiceTemp);
//														}else{
//															if((proximoToken== null) || (valorAdicionado.equals("\"") && !proximoToken.getValor().equals("\"")))
//															    ehFimDaString = true;
//														}
//													}
//													tokensAnonymousFunction.remove(indice);
//													OctaveString octString = new OctaveString(conteudo);
//													tokensAnonymousFunction.insertElementAt(octString, indice);
//													tokensTemp.add(octString);
//												}
//											}else{
//												if (((tokenAnterior == null)&&(token.getValor().equals("+")))||
//												        (((token.getValor().equals("+"))&&(tokenAnterior.getTipo().equals("Delimitador")))||
//														((token.getValor().equals("+"))&&(tokenAnterior.getValor().equals(","))))||
//														((token.getValor().equals("+"))&&(tokenAnterior != null)&&(tokenAnterior.getValor().equals(":")))){
//														OctaveExpressaoUnaria expUnaria = new OctaveExpressaoUnaria("+");
//														tokensTemp.add(expUnaria);
//												}else{
//													if(((tokenAnterior == null)&&(token.getValor().equals("-")))||
//															((token.getValor().equals("-"))&&(tokenAnterior.getTipo().equals("Delimitador")))||
//															((token.getValor().equals("-"))&&(tokenAnterior.getValor().equals(",")))||
//															((token.getValor().equals("-"))&&(tokenAnterior != null)&&(tokenAnterior.getValor().equals(":")))){
//													    OctaveExpressaoUnaria expUnaria = new OctaveExpressaoUnaria("-");
//														tokensTemp.add(expUnaria);
//													}else{
//														if(token.getValor().equals("++") || token.getValor().equals("--")){
//															
//															OctaveIdentificador identificador = null;
//															if((tokenAnterior != null)&&(tokenAnterior.getTipo().equals("Identificador"))){
//																identificador = new OctaveIdentificador(tokenAnterior.getValor());
//															}else{
//																Token proximoToken = (Token)tokensAnonymousFunction.get(indice + 1);
//																identificador = new OctaveIdentificador(proximoToken.getValor());
//																indice++;
//															}
//															OctaveVariavel variavel = new OctaveVariavel(identificador);
//															if(token.getValor().equals("++")){
//																OctaveExpressaoAutoIncremento incremento = new OctaveExpressaoAutoIncremento(variavel);
//																tokensTemp.add(incremento);
//															}else{
//																OctaveExpressaoAutoIncremento decremento = new OctaveExpressaoAutoIncremento(variavel);
//																tokensTemp.add(decremento);
//															}
//														}else{
//														    ehExpressao = true;
//														    tokensTemp.add(token);
//														}
//													}
//												}
//											}
//										}
//									}
//								}	
//							}
//						}
//					}
//				}
//			}
//			tokensAnonymousFunction.remove(indice);
//		}
//		if(ehExpressao){
//			OctaveExpressao expressao = montaOctaveExpressao(tokensTemp);
//			anonymousFunc.adicionaParametro(expressao);
//		}else{
//			if(tokensTemp.size() > 0){
//				if(tokensTemp.get(0) instanceof OctaveExpressaoUnaria){
//					OctaveExpressaoUnaria expUnaria = (OctaveExpressaoUnaria)tokensTemp.get(0);
//					ElementoOctave proximoElemento = tokensTemp.get(1);
//					if(expUnaria.getElemento() == null){
//						if(proximoElemento instanceof Token){
//							Token proximo = (Token)proximoElemento;
//							OctaveIdentificador ident = new OctaveIdentificador(proximo.getValor());
//							if(proximo.getTipo().equals("Identificador")){
//								OctaveVariavel var = new OctaveVariavel(ident);
//								proximoElemento = var;
//							}else{
//								if(proximo.getTipo().equals("Numero")){
//									OctaveConstante constante = new OctaveConstante(proximo.getValor());
//									proximoElemento = constante;
//								}else{
//									if(proximo.getValor().equals("(")){
//										tokensTemp.remove(indice+1);
//										int cont = 1;
//										Vector<ElementoOctave> elementos = new Vector<ElementoOctave>(); 
//										while(cont > 0){
//											proximoElemento = tokensTemp.get(indice + 1);
//											if((proximoElemento instanceof Token)&&(((Token)proximoElemento)).getValor().equals("(")){
//												cont++;
//											}
//											if((proximoElemento instanceof Token)&&(((Token)proximoElemento)).getValor().equals(")")){
//												cont--;
//											}
//											elementos.add(proximoElemento);
//											tokensTemp.remove(indice+1);
//										}
//										elementos.remove(elementos.size() - 1);
//										proximoElemento = montaOctaveExpressao(elementos);
//									}
//								}
//							}
//						}
//						expUnaria.setElemento(proximoElemento);
//					}
//					anonymousFunc.adicionaParametro(expUnaria);
//				}else{
//					if(tokensTemp.get(0) instanceof Token){
//						if(((Token)tokensTemp.get(0)).getTipo().equals("Identificador")){
//							anonymousFunc.adicionaParametro(new OctaveVariavel(new OctaveIdentificador(((Token)tokensTemp.get(0)).getValor())));
//						}else{
//							if(((Token)tokensTemp.get(0)).getTipo().equals("Numero")){
//							    anonymousFunc.adicionaParametro(new OctaveConstante(((Token)tokensTemp.get(0)).getValor()));
//							}else{
//								anonymousFunc.adicionaParametro(tokensTemp.get(0));
//							}
//						}
//					}else{
//						anonymousFunc.adicionaParametro(tokensTemp.get(0));
//					}
//				}
//			}
//		}
//		OctaveExpressao expressao = montaOctaveExpressao(tokensAnonymousFunction);
//		anonymousFunc.setExpressao(expressao);
//		return anonymousFunc;
//	}
//	private OctaveChamadaFuncao montaChamadaFuncao(OctaveIdentificador identificador, Vector<ElementoOctave> parametrosFuncao) {
//		OctaveChamadaFuncao chamada = new OctaveChamadaFuncao(identificador);
//		int indice = 0;
//		boolean ehExpressao = false;
//		Vector<ElementoOctave> tokensTemp = new Vector<ElementoOctave>();
//		Token tokenAnterior = null;
//		Token token = null;
//		while(parametrosFuncao.size() > 0){
//			tokenAnterior = token;
//			    token = (Token)parametrosFuncao.get(indice);
//				if(token.getValor().equals(",")){
//					if(ehExpressao){
//						OctaveExpressao expressao = montaOctaveExpressao(tokensTemp);
//						chamada.adicionaParametro(expressao);
//					}else{
//						if(tokensTemp.get(0) instanceof OctaveExpressaoUnaria){
//							OctaveExpressaoUnaria expUnaria = (OctaveExpressaoUnaria)tokensTemp.get(0);
//							if(expUnaria.getElemento() == null){
//								ElementoOctave proximoElemento = tokensTemp.get(1);
//								if(proximoElemento instanceof Token){
//									Token proximo = (Token)proximoElemento;
//									OctaveIdentificador ident = new OctaveIdentificador(proximo.getValor());
//									if(proximo.getTipo().equals("Identificador")){
//										OctaveVariavel var = new OctaveVariavel(ident);
//										proximoElemento = var;
//									}else{
//										if(proximo.getTipo().equals("Numero")){
//											OctaveConstante constante = new OctaveConstante(proximo.getValor());
//											proximoElemento = constante;
//										}else{
//											if(proximo.getValor().equals("(")){
//												tokensTemp.remove(indice+1);
//												int cont = 1;
//												Vector<ElementoOctave> elementos = new Vector<ElementoOctave>(); 
//												while(cont > 0){
//													proximoElemento = tokensTemp.get(indice + 1);
//													if((proximoElemento instanceof Token)&&(((Token)proximoElemento)).getValor().equals("(")){
//														cont++;
//													}
//													if((proximoElemento instanceof Token)&&(((Token)proximoElemento)).getValor().equals(")")){
//														cont--;
//													}
//													elementos.add(proximoElemento);
//													tokensTemp.remove(indice+1);
//												}
//												elementos.remove(elementos.size() - 1);
//												proximoElemento = montaOctaveExpressao(elementos);
//											}
//										}
//									}
//								}
//								expUnaria.setElemento(proximoElemento);
//							}
//							chamada.adicionaParametro(expUnaria);
//						}else{
//							if(tokensTemp.get(0) instanceof Token){
//								if(((Token)tokensTemp.get(0)).getTipo().equals("Identificador")){
//									chamada.adicionaParametro(new OctaveIdentificador(((Token)tokensTemp.get(0)).getValor()));
//								}else{
//									if(((Token)tokensTemp.get(0)).getTipo().equals("Numero")){
//									    chamada.adicionaParametro(new OctaveConstante(((Token)tokensTemp.get(0)).getValor()));
//									}else{
//										chamada.adicionaParametro(tokensTemp.get(0));
//									}
//								}
//							}else{
//								chamada.adicionaParametro(tokensTemp.get(0));
//							}
//						}
//					}
//					tokensTemp = new Vector<ElementoOctave>();
//					ehExpressao = false;
//				}else{
//					if(token.getTipo().equals("Identificador")){
//						int indiceTemp = indice + 1;
//						Token proximoToken = null;
//						if(indiceTemp < parametrosFuncao.size())
//						    proximoToken = (Token)parametrosFuncao.get(indiceTemp);
//						if((proximoToken != null) && (proximoToken.getValor().equals("("))){
//							Vector<ElementoOctave> tokensChamadaFuncao = new Vector<ElementoOctave>();
//							Vector<ElementoOctave> pilhaTemp = new Vector<ElementoOctave>();
//							pilhaTemp.add(proximoToken);
//							parametrosFuncao.remove(indiceTemp);
//							OctaveChamadaFuncao chamadaInterna;
//							while(pilhaTemp.size()!=0){
//								proximoToken = (Token)parametrosFuncao.get(indiceTemp);
//								if((proximoToken.getValor().equals("("))||(proximoToken.getValor().equals("["))||(proximoToken.getValor().equals("{"))){
//									pilhaTemp.add(proximoToken);
//								}
//								if((proximoToken.getValor().equals(")"))||(proximoToken.getValor().equals("]"))||(proximoToken.getValor().equals("}"))){
//									pilhaTemp.remove(pilhaTemp.size()-1);
//								}
//								tokensChamadaFuncao.add(proximoToken);
//								parametrosFuncao.remove(indiceTemp);
//							}
//							tokensChamadaFuncao.remove(tokensChamadaFuncao.size()-1);
//							OctaveIdentificador ident = new OctaveIdentificador(token.getValor());
//							parametrosFuncao.remove(indice);
//							if(tokensChamadaFuncao.size() == 0){
//								chamadaInterna = new OctaveChamadaFuncao(ident);
//								parametrosFuncao.insertElementAt(chamadaInterna, indice);
//							}else{
//								chamadaInterna = montaChamadaFuncao(ident,tokensChamadaFuncao);
//								parametrosFuncao.insertElementAt(chamadaInterna, indice);
//							}
//							tokensTemp.add(chamadaInterna);
//						}else{
//							if(token.getValor().startsWith("@")){
//								OctaveIdentificador ident = new OctaveIdentificador(token.getValor());
//								OctaveHandleFunction handleFunc = new OctaveHandleFunction(ident);
//								tokensTemp.add(handleFunc);
//							}else{
//								if(token.getValor().contains(".")){
//									OctaveVariavelStructure varStructure = new OctaveVariavelStructure(token.getValor());
//									tokensTemp.add(varStructure);
//								}else{
//									tokensTemp.add(token);
//								}
//							}
//						}
//					}else{
//						if(token.getTipo().equals("Numero")){
//							tokensTemp.add(token);
//						}else{
//							if(token.getTipo().equals("Delimitador")){
//								if(token.getValor().equals("{")){
//									int indiceTemp = indice + 1;
//									Vector<ElementoOctave> pilhaTemp = new Vector<ElementoOctave>();
//									pilhaTemp.add(token);
//									String valorArray = "{";
//									Token proximoToken = null;
//									while(pilhaTemp.size() > 0){
//										proximoToken = (Token)parametrosFuncao.get(indiceTemp);
//										parametrosFuncao.remove(indiceTemp);
//										valorArray = valorArray + proximoToken.getValor();
//										if(proximoToken.getValor().equals("}"))
//											pilhaTemp.remove(pilhaTemp.size() - 1);
//										if(proximoToken.getValor().equals("{"))
//											pilhaTemp.add(proximoToken);
//									}
//									if(indiceTemp < parametrosFuncao.size())
//									    proximoToken = (Token)parametrosFuncao.get(indiceTemp);
//									
//									if(proximoToken.getValor().equals("(")){
//										valorArray = valorArray + proximoToken.getValor();
//										pilhaTemp.add(proximoToken);
//										proximoToken = null;
//										parametrosFuncao.remove(indiceTemp);
//										while(pilhaTemp.size() > 0){
//											proximoToken = (Token)parametrosFuncao.get(indiceTemp);
//											parametrosFuncao.remove(indiceTemp);
//											valorArray = valorArray + proximoToken.getValor();
//											if(proximoToken.getValor().equals(")"))
//												pilhaTemp.remove(pilhaTemp.size() - 1);
//											if(proximoToken.getValor().equals("("))
//												pilhaTemp.add(proximoToken);
//										}
//									}
//									parametrosFuncao.remove(indice);
//									if(valorArray.contains(";")||valorArray.contains("\n")||valorArray.contains("=")){
//										OctaveStructure structure = new OctaveStructure(valorArray);
//										parametrosFuncao.insertElementAt(structure, indice);
//										tokensTemp.add(structure);
//									}else{
//										OctaveArray array = new OctaveArray(valorArray);
//										if((tokenAnterior != null) && (tokenAnterior.getTipo().equals("Identificador"))){
//											OctaveIdentificador arrayIdent = new OctaveIdentificador(tokenAnterior.getValor());
//											array.setIdentificador(arrayIdent);
//											tokensTemp.remove(tokensTemp.size()-1);
//										}
//										parametrosFuncao.insertElementAt(array, indice);
//										tokensTemp.add(array);
//									}
//								}else{
//									if(token.getValor().equals("[")){
//										int indiceTemp = indice + 1;
//										Vector<ElementoOctave> pilhaTemp = new Vector<ElementoOctave>();
//										pilhaTemp.add(token);
//										String valorMatriz = "[";
//										Token proximoToken = null;
//										while(pilhaTemp.size() > 0){
//											proximoToken = (Token)parametrosFuncao.get(indiceTemp);
//											parametrosFuncao.remove(indiceTemp);
//											valorMatriz = valorMatriz + proximoToken.getValor();
//											if(proximoToken.getValor().equals("]"))
//												pilhaTemp.remove(pilhaTemp.size() - 1);
//											if(proximoToken.getValor().equals("["))
//												pilhaTemp.add(proximoToken);
//										}
//										if(indiceTemp < parametrosFuncao.size())
//										    proximoToken = (Token)parametrosFuncao.get(indiceTemp);
//										
//										if(proximoToken.getValor().equals("(")){
//											valorMatriz = valorMatriz + proximoToken.getValor();
//											pilhaTemp.add(proximoToken);
//											proximoToken = null;
//											parametrosFuncao.remove(indiceTemp);
//											while(pilhaTemp.size() > 0){
//												proximoToken = (Token)parametrosFuncao.get(indiceTemp);
//												parametrosFuncao.remove(indiceTemp);
//												valorMatriz = valorMatriz + proximoToken.getValor();
//												if(proximoToken.getValor().equals(")"))
//													pilhaTemp.remove(pilhaTemp.size() - 1);
//												if(proximoToken.getValor().equals("("))
//													pilhaTemp.add(proximoToken);
//											}
//										}
//										parametrosFuncao.remove(indice);
//										OctaveMatriz matriz = new OctaveMatriz(valorMatriz);
//										if((tokenAnterior != null) && (tokenAnterior.getTipo().equals("Identificador"))){
//											OctaveIdentificador matrizIdent = new OctaveIdentificador(tokenAnterior.getValor());
//											matriz.setIdentificador(matrizIdent);
//											tokensTemp.remove(tokensTemp.size()-1);
//										}
//										parametrosFuncao.insertElementAt(matriz, indice);
//										tokensTemp.add(matriz);
//									}else{
//										if(token.getValor().equals("@")){
//											Vector<ElementoOctave> pilha = new Vector<ElementoOctave>();
//											Vector<ElementoOctave> tokensAnonymousFunction = new Vector<ElementoOctave>();
//											int indiceTemp = indice + 1;
//											boolean ehFimHandleFunction = false;
//											Token tokenTemp = null;
//											tokensAnonymousFunction.add(token);
//											while(!ehFimHandleFunction){
//												if(indiceTemp < parametrosFuncao.size()){
//													tokenTemp = (Token)parametrosFuncao.get(indiceTemp);
//													if((tokenTemp.getValor().equals("(")) || (tokenTemp.getValor().equals("[")) || (tokenTemp.getValor().equals("{"))){
//														pilha.add(tokenTemp);
//													}
//													if(tokenTemp.getValor().equals(")")){
//														pilha.remove(pilha.size()-1);
//													}
//													if((tokenTemp.getValor().equals(",")) && (pilha.size() == 0)){
//														ehFimHandleFunction = true;
//													}else{
//													    tokensAnonymousFunction.add(tokenTemp);
//													    parametrosFuncao.remove(indiceTemp);
//													}
//												}else{
//													ehFimHandleFunction = true;
//												}
//											}
//											OctaveAnonymousFunction anonymousFunc = montaAnonymousFunction(tokensAnonymousFunction);
//											parametrosFuncao.remove(indice);
//											parametrosFuncao.insertElementAt(anonymousFunc,indice);
//											tokensTemp.add(anonymousFunc);
//										}else{
//											if((token.getValor().equals("'")) || (token.getValor().equals("\"")) || (token.getValor().equals(".'"))){
//												boolean ehFimDaString = false;
//												String conteudo = "";
//												int indiceTemp = indice + 1;
//												Token proximoToken = null;
//												if(indiceTemp < parametrosFuncao.size())
//												    proximoToken = (Token)parametrosFuncao.get(indiceTemp);
//												if((token.getValor().equals("'"))||(token.getValor().equals(".'"))){
//													if(token.getValor().equals(".'")){
//														ElementoOctave elemento = tokensTemp.get(tokensTemp.size() - 1);
//														if(elemento instanceof Token){
//															Token tokenTranspose = (Token) elemento;
//															if(tokenTranspose.getTipo().equals("Identificador")){
//																elemento = new OctaveVariavel(new OctaveIdentificador(tokenTranspose.getValor()));
//																OctaveExpressaoTranspose expTranspose = new OctaveExpressaoTranspose(elemento,token.getValor());
//																tokensTemp.remove(tokensTemp.size() - 1);
//																tokensTemp.add(expTranspose);
//															}else{
//																if(tokenTranspose.getTipo().equals("Numero")){
//																	elemento = new OctaveConstante(tokenTranspose.getValor());
//																	OctaveExpressaoTranspose expTranspose = new OctaveExpressaoTranspose(elemento,token.getValor());
//																	tokensTemp.remove(tokensTemp.size() - 1);
//																	tokensTemp.add(expTranspose);
//																}else{
//																	tokensTemp.add(token);
//																}
//															}
//														}else{
//															OctaveExpressaoTranspose expTranspose = new OctaveExpressaoTranspose(elemento,token.getValor());
//															tokensTemp.remove(tokensTemp.size() - 1);
//															tokensTemp.add(expTranspose);
//														}
//													}else{	
//														if((tokenAnterior == null) || ((tokenAnterior != null) && (tokenAnterior.getTipo().equals("Delimitador")) && (!tokenAnterior.getValor().equals("[")) && (!tokenAnterior.getValor().equals("{")))){
//															conteudo = conteudo + "'";
//															String valorAdicionado = null;
//															while(!ehFimDaString){
//																valorAdicionado = proximoToken.getValor();
//																conteudo = conteudo + valorAdicionado;
//																parametrosFuncao.remove(indiceTemp);
//																if(indiceTemp < parametrosFuncao.size())
//																	proximoToken = (Token)parametrosFuncao.get(indiceTemp);
//																else
//																	proximoToken = null;
//																if( proximoToken!= null && valorAdicionado.equals("'") && proximoToken.getValor().equals("'")){
//																	conteudo = conteudo + proximoToken.getValor();
//																	parametrosFuncao.remove(indiceTemp);
//																	proximoToken = (Token)parametrosFuncao.get(indiceTemp);
//																}else{
//																	if((proximoToken== null) || (valorAdicionado.equals("'") && !proximoToken.getValor().equals("'")))
//																	    ehFimDaString = true;
//																}
//															}
//															parametrosFuncao.remove(indice);
//															OctaveString octString = new OctaveString(conteudo);
//															parametrosFuncao.insertElementAt(octString, indice);
//															tokensTemp.add(octString);
//														}
//												    }
//												}else{
//													conteudo = conteudo + "\"";
//													String valorAdicionado = null;
//													while(!ehFimDaString){
//														valorAdicionado = proximoToken.getValor();
//														conteudo = conteudo + valorAdicionado;
//														parametrosFuncao.remove(indiceTemp);
//														if(indiceTemp < parametrosFuncao.size())
//															proximoToken = (Token)parametrosFuncao.get(indiceTemp);
//														else
//															proximoToken = null;
//														if( proximoToken!= null && valorAdicionado.equals("\"") && proximoToken.getValor().equals("\"")){
//															conteudo = conteudo + proximoToken.getValor();
//															parametrosFuncao.remove(indiceTemp);
//															proximoToken = (Token)parametrosFuncao.get(indiceTemp);
//														}else{
//															if((proximoToken== null) || (valorAdicionado.equals("\"") && !proximoToken.getValor().equals("\"")))
//															    ehFimDaString = true;
//														}
//													}
//													parametrosFuncao.remove(indice);
//													OctaveString octString = new OctaveString(conteudo);
//													parametrosFuncao.insertElementAt(octString, indice);
//													tokensTemp.add(octString);
//												}
//											}else{
//												if(((tokenAnterior == null)&&(token.getValor().equals("+")))||
//														((token.getValor().equals("+"))&&(tokenAnterior.getTipo().equals("Delimitador")))||
//														((token.getValor().equals("+"))&&(tokenAnterior.getValor().equals(",")))||
//														((token.getValor().equals("+"))&&(tokenAnterior != null)&&(tokenAnterior.getValor().equals(":")))){
//														OctaveExpressaoUnaria expUnaria = new OctaveExpressaoUnaria("+");
//														tokensTemp.add(expUnaria);
//												}else{
//													if(((tokenAnterior == null)&&(token.getValor().equals("-")))||
//															((token.getValor().equals("-"))&&(tokenAnterior.getTipo().equals("Delimitador")))||
//															((token.getValor().equals("-"))&&(tokenAnterior.getValor().equals(",")))||
//															((token.getValor().equals("-"))&&(tokenAnterior != null)&&(tokenAnterior.getValor().equals(":")))){
//													    OctaveExpressaoUnaria expUnaria = new OctaveExpressaoUnaria("-");
//														tokensTemp.add(expUnaria);
//													}else{
//														if(token.getValor().equals("++") || token.getValor().equals("--")){
//															
//															OctaveIdentificador ident = null;
//															if((tokenAnterior != null)&&(tokenAnterior.getTipo().equals("Identificador"))){
//																ident = new OctaveIdentificador(tokenAnterior.getValor());
//															}else{
//																Token proximoToken = (Token)parametrosFuncao.get(indice + 1);
//																ident = new OctaveIdentificador(proximoToken.getValor());
//																indice++;
//															}
//															OctaveVariavel variavel = new OctaveVariavel(ident);
//															if(token.getValor().equals("++")){
//																OctaveExpressaoAutoIncremento incremento = new OctaveExpressaoAutoIncremento(variavel);
//																tokensTemp.add(incremento);
//															}else{
//																OctaveExpressaoAutoIncremento decremento = new OctaveExpressaoAutoIncremento(variavel);
//																tokensTemp.add(decremento);
//															}
//														}else{
//														    ehExpressao = true;
//														    tokensTemp.add(token);
//														}
//													}
//												}
//											}
//										}
//									}
//								}	
//							}
//						}
//					}
//				}
//				parametrosFuncao.remove(indice);
//		}
//		if(ehExpressao){
//			OctaveExpressao expressao = montaOctaveExpressao(tokensTemp);
//			chamada.adicionaParametro(expressao);
//		}else{
//			if(tokensTemp.size()>0){
//				if(tokensTemp.get(0) instanceof OctaveExpressaoUnaria){
//					OctaveExpressaoUnaria expUnaria = (OctaveExpressaoUnaria)tokensTemp.get(0);
//					ElementoOctave proximoElemento = tokensTemp.get(1);
//					if(expUnaria.getElemento() == null){
//						if(proximoElemento instanceof Token){
//							Token proximo = (Token)proximoElemento;
//							OctaveIdentificador ident = new OctaveIdentificador(proximo.getValor());
//							if(proximo.getTipo().equals("Identificador")){
//								OctaveVariavel var = new OctaveVariavel(ident);
//								proximoElemento = var;
//							}else{
//								if(proximo.getTipo().equals("Numero")){
//									OctaveConstante constante = new OctaveConstante(proximo.getValor());
//									proximoElemento = constante;
//								}else{
//									if(proximo.getValor().equals("(")){
//										tokensTemp.remove(indice+1);
//										int cont = 1;
//										Vector<ElementoOctave> elementos = new Vector<ElementoOctave>(); 
//										while(cont > 0){
//											proximoElemento = tokensTemp.get(indice + 1);
//											if((proximoElemento instanceof Token)&&(((Token)proximoElemento)).getValor().equals("(")){
//												cont++;
//											}
//											if((proximoElemento instanceof Token)&&(((Token)proximoElemento)).getValor().equals(")")){
//												cont--;
//											}
//											elementos.add(proximoElemento);
//											tokensTemp.remove(indice+1);
//										}
//										elementos.remove(elementos.size() - 1);
//										proximoElemento = montaOctaveExpressao(elementos);
//									}
//								}
//							}
//						}
//						expUnaria.setElemento(proximoElemento);
//					}
//					chamada.adicionaParametro(expUnaria);
//				}else{
//					if(tokensTemp.get(0) instanceof Token){
//						if(((Token)tokensTemp.get(0)).getTipo().equals("Identificador")){
//							chamada.adicionaParametro(new OctaveVariavel(new OctaveIdentificador(((Token)tokensTemp.get(0)).getValor())));
//						}else{
//							if(((Token)tokensTemp.get(0)).getTipo().equals("Numero")){
//							    chamada.adicionaParametro(new OctaveConstante(((Token)tokensTemp.get(0)).getValor()));
//							}else{
//								chamada.adicionaParametro(tokensTemp.get(0));
//							}
//						}
//					}else{
//						chamada.adicionaParametro(tokensTemp.get(0));
//					}
//				}
//			}
//		}
//		return chamada;
//	}
//	private Vector<ElementoOctave> separaTokens(String textoExpressao){
//		Vector<ElementoOctave> elementosOctave = new Vector<ElementoOctave>();
//		int indice = 0;
//		boolean tokenPonto = false;
//		String token = "", identificador = "";
//		String tokenAnterior = "";
//		while(indice != textoExpressao.length()){	
//			tokenAnterior = token;
//			token = textoExpressao.substring(indice,indice+1);
//			String proximoToken = "";
//			if((indice+1) != textoExpressao.length())
//			    proximoToken = textoExpressao.substring(indice+1,indice+2);
//		
//			if(token.equals("'") || token.equals("\"")){
//				if((tokenAnterior.equals(""))
//			    		||(token.equals("\""))
//			    		||((token.equals("'")) && (!tokenAnterior.equals(")")) && (!tokenAnterior.equals("]")) && (!tokenAnterior.equals("}")) && (this.delimitadores.contains(tokenAnterior)))
//			    		||((token.equals("'")) && (tokenAnterior.equals(" ")))){
//			    	identificador = identificador + token;
//					String valorAdicionado = null;
//					String proximo = null;
//					indice++;
//					if(indice < textoExpressao.length()){
//						proximo = textoExpressao.substring(indice,indice+1);
//					}
//					boolean ehFimDaString = false;
//					while(!ehFimDaString){
//						indice++;
//						valorAdicionado = proximo;
//						identificador = identificador + valorAdicionado;
//						if(indice < textoExpressao.length()){
//							proximo = textoExpressao.substring(indice,indice+1);
//						}else
//							proximo = null;
//						if( proximo!= null && valorAdicionado.equals(String.valueOf(token)) && proximo.equals(String.valueOf(token))){
//							identificador = identificador + proximo;
//							proximo = textoExpressao.substring(indice+1,indice+2);
//							indice++;
//						}else{
//							if((proximo== null) || (valorAdicionado.equals(String.valueOf(token)) && !proximo.equals(String.valueOf(token))))
//							    ehFimDaString = true;
//						}
//					}
//					elementosOctave.add(new Token(identificador,"Identificador",-1));
//					identificador = "";
//					if(indice < textoExpressao.length())
//					    token = textoExpressao.substring(indice,indice+1);
//					else
//						break;
//				}	
//			}
//			
//			if((token.equals(".")) && (tokenAnterior.equals(")")) && (!this.delimitadores.contains(proximoToken))){
//				int cont = 1;
//				elementosOctave.remove(elementosOctave.size() - 1);
//				identificador = ").";
//				while(cont > 0){
//					if(((Token)elementosOctave.get(elementosOctave.size() - 1)).getValor().equals("(")){
//						cont--;
//					}
//					if(((Token)elementosOctave.get(elementosOctave.size() - 1)).getValor().equals(")")){
//						cont++;
//					}
//					identificador = ((Token)elementosOctave.get(elementosOctave.size() - 1)).getValor() + identificador;
//					elementosOctave.remove(elementosOctave.size() - 1);
//				}
//				identificador = ((Token)elementosOctave.get(elementosOctave.size() - 1)).getValor() + identificador;
//				elementosOctave.remove(elementosOctave.size() - 1);
//				indice++;
//				token = textoExpressao.substring(indice,indice+1);
//			}
//		
//			if((token.equals(".")) && (proximoToken.equals("("))){
//				int cont = 1;
//				identificador = identificador + textoExpressao.substring(indice,indice+1);
//				indice++;
//				identificador = identificador + textoExpressao.substring(indice,indice+1);
//				indice++;
//				while(cont > 0){
//					identificador = identificador + textoExpressao.substring(indice,indice+1);
//					if(textoExpressao.substring(indice,indice+1).equals("(")){
//						cont++;
//					}
//					if(textoExpressao.substring(indice,indice+1).equals(")")){
//						cont--;
//					}
//					indice++;
//				}
//				token = textoExpressao.substring(indice,indice+1);
//		    }
//			if((this.delimitadores.contains(token))||((token.equals(".")) && (this.delimitadores.contains(proximoToken)))){
//				if(!identificador.trim().equals("")){
//			    	if(ehNumero(identificador))
//			    		elementosOctave.add(new Token(identificador,"Numero",-1));
//			    	else{
//			    		elementosOctave.add(new Token(identificador,"Identificador",-1));
//			    	}
//			    	identificador = "";
//			    }
//                if(token.equals(".")){	
//                	indice++;
//					token = textoExpressao.substring(indice,indice+1);
//					proximoToken = textoExpressao.substring(indice+1,indice+2);
//					tokenPonto = true;
//				}
//			    int precedencia = -1;
//			    
//			    if((token.equals("&"))||(token.equals("|"))||(token.equals("!"))||(token.equals("~"))){
//                    if((proximoToken.equals("&"))||(proximoToken.equals("|"))){
//                    	token = token + proximoToken;
//                    	indice++;
//                    }
//                    if(token.equals("!") || token.equals("~")){
//                    	precedencia = 5;
//                    }
//                    if(token.equals("&") || token.equals("&&")){
//                    	precedencia = 6;
//                    }
//                    if(token.equals("|") || token.equals("||")){
//                    	precedencia = 7;
//                    }
//			    }
//			    
//			    if((token.equals(">"))||(token.equals("<"))||(token.equals("="))||(token.equals("!"))||(token.equals("~"))){
//                    if(((token.equals(">"))||(token.equals("<")))&&(proximoToken.equals("="))){
//                    	token = token + proximoToken;
//                    	indice++;
//                    }else{
//                    	if(proximoToken.equals("=")){
//                    		token = token + proximoToken;
//                        	indice++;
//                    	}
//                    }
//			    	precedencia = 4;
//			    }
//			    
//			    if((token.equals("^"))||(token.equals("'"))||((token.equals("*")) && (proximoToken.equals("*")))){
//			    	if(token.equals("*")){
//			    		token = token + proximoToken;
//			    		indice++;
//			    	}
//			    	precedencia = 0;
//			    }
//			    
//			    if((token.equals("*") && !proximoToken.equals("*"))||(token.equals("/"))||(token.equals("\\")))
//			    	precedencia = 1;
//			    
//			    if((token.equals("+"))||(token.equals("-"))){
//			    	if(token.equals("+")){
//				    	if((proximoToken != null)&&(proximoToken.equals("+"))){
//				    		token = token + proximoToken;
//                        	indice++;
//                        	precedencia = 0;
//				    	}else{
//				    		precedencia = 2;
//				    	}
//			    	}else{
//			    		if((proximoToken != null)&&(proximoToken.equals("-"))){
//			    			token = token + proximoToken;
//                        	indice++;
//                        	precedencia = 0;
//				    	}else{
//				    		precedencia = 2;
//				    	}
//			    	}
//			    }
//			    if(tokenPonto){
//			    	token = "."+token;
//			    	tokenPonto = false;
//			    }
//			    
//			    if(token.equals(":")){
//			    	precedencia = 8;
//			    }
//			    
//			    if(token.equals("=")){
//			    	precedencia = 9;
//			    }
//			    
//			    elementosOctave.add(new Token(token,"Delimitador",precedencia));
//			}else{
//				if(!(token.trim().equals(""))){
//					if((token.equals("@")) && (textoExpressao.substring(indice+1,indice+2).equals("("))){
//						elementosOctave.add(new Token(token,"Delimitador",-1));
//					}else{
//				        identificador = identificador + token;
//				    }
//				}    
//			}
//			indice++;
//		}
//		if(!identificador.trim().equals("")){
//			if(ehNumero(identificador))
//				elementosOctave.add(new Token(identificador,"Numero",-1));
//	    	else
//	    		elementosOctave.add(new Token(identificador,"Identificador",-1));
//		}
//		return elementosOctave;
//	}
//	
//	private OctaveFunction converteElementoFunction() {
//		OctaveFunction func = new OctaveFunction();
//		String tokenTemp = "";
//		boolean possuiParenteses = false;
//		while((!this.token.equals("="))&&(!this.token.equals("("))&&(!this.token.equals("\n"))){
//			pegaToken(true);
//			if((!this.token.equals("="))&&(!this.token.equals("("))&&(!this.token.equals("\n")))
//			    tokenTemp = tokenTemp + this.token;
//			else{
//				if(this.token.equals("=")){
//					func.setRetorno(tokenTemp);
//					pegaToken(true);
//					tokenTemp = this.token;
//				}else{
//					func.setIdentificador(tokenTemp);
//					if(this.token.equals("(")){
//						possuiParenteses = true;
//					}
//				}
//			}
//		}
//		tokenTemp = "";
//		if(possuiParenteses){
//			pegaToken(true);
//			while(!this.token.equals(")")){
//				if(this.token.equals(",")){
//					ElementoOctave parametro = null;
//					if(tokenTemp.substring(0,1).equals("@"))
//						parametro = new OctaveHandleFunction(new OctaveIdentificador(tokenTemp));
//					else
//						parametro = new OctaveIdentificador(tokenTemp);
//					func.adicionaParametros(parametro);
//					tokenTemp = "";
//				}else{
//				    tokenTemp = tokenTemp + this.token;
//				}
//				pegaToken(true);
//			}
//			if(!tokenTemp.trim().equals("")){
//				if(tokenTemp.substring(0,1).equals("@"))
//					func.adicionaParametros(new OctaveHandleFunction(new OctaveIdentificador(tokenTemp)));
//				else
//					func.adicionaParametros(new OctaveIdentificador(tokenTemp));
//			}
//			pegaToken(true);
//	    }
//		return func;
//	}
//
//	private void pegaToken(boolean ignoraEspacos){
//		this.token = "";
//		char caractereAnterior = ' ';
//		char caractere = ' ';
//		while(true){
//			if(this.indiceAtual >= this.source.length)
//				break;
//			if(this.indiceAtual>0)
//			    caractereAnterior = this.source[this.indiceAtual-1];
//			caractere = this.source[this.indiceAtual];
//		    if((caractere == ' ')&&(ignoraEspacos)){
//		    	if((!this.token.trim().equals("")) && (!this.token.trim().equals("...")))
//		    		break;
//		    	if(this.token.trim().equals("...")){
//		    		while(this.source[this.indiceAtual]!='\n'){
//						this.indiceAtual++;
//					}
//		    		this.indiceAtual++;
//		    		this.token = "";
//		    	}
//			    removeEspacos();
//			    caractere = this.source[this.indiceAtual];
//		    }
//		    if((caractere == '\'')||(caractere == '"')){
//		    	if((caractereAnterior == ' ')
//		    		||(caractere == '"')
//		    		||((caractere == '\'') && (caractereAnterior != ')') && (caractereAnterior != ']') && (caractereAnterior != '}') && (this.delimitadores.contains(String.valueOf(caractereAnterior))))
//		    		||((caractere == '\'') && (caractereAnterior == ' '))){
//			    	this.token = this.token + caractere;
//					String valorAdicionado = null;
//					String proximoToken = null;
//					this.indiceAtual++;
//					if(this.indiceAtual < this.source.length){
//						proximoToken = String.valueOf(this.source[this.indiceAtual]);
//					}
//					boolean ehFimDaString = false;
//					while(!ehFimDaString){
//						this.indiceAtual++;
//						valorAdicionado = proximoToken;
//						this.token = this.token + valorAdicionado;
//						if(this.indiceAtual < this.source.length){
//							proximoToken = String.valueOf(this.source[this.indiceAtual]);
//						}else
//							proximoToken = null;
//						if( proximoToken!= null && valorAdicionado.equals(String.valueOf(caractere)) && proximoToken.equals(String.valueOf(caractere))){
//							this.token = this.token + proximoToken;
//							proximoToken = String.valueOf(this.source[this.indiceAtual+1]);
//							this.indiceAtual++;
//						}else{
//							if((proximoToken== null) || (valorAdicionado.equals(String.valueOf(caractere)) && !proximoToken.equals(String.valueOf(caractere))))
//							    ehFimDaString = true;
//						}
//					}
//					break;
//		    	}
//			}
//		    if(delimitadores.contains(String.valueOf(caractere))){
//				this.delimitadorEncontrado = caractere;
//				if(this.token.trim().equals("")){
//					this.token = String.valueOf(this.delimitadorEncontrado);
//					this.indiceAtual++;
//					break;
//				}else{
//					if(this.token.trim().equals("...")){
//						while(this.source[this.indiceAtual]!='\n'){
//							this.indiceAtual++;
//						}
//						this.token = "";
//					}else{
//						break;
//					}
//				}
//			}else{
//				if((caractere == '%')||(caractere == '#')){
//					ignoraComentario();
//					this.delimitadorEncontrado = caractere;
//					this.indiceAtual--;
//				}else{
//					if((caractere == ' ') && (!ignoraEspacos)){
//						if(this.token.trim().equals("")){
//							this.token = " ";
//							this.indiceAtual++;
//							break;
//						}else{
//							if(this.token.trim().equals("...")){
//								while(this.source[this.indiceAtual]!='\n'){
//									this.indiceAtual++;
//								}
//								this.token = "";
//							}else{
//								break;
//							}
//						}
//					}
//					
//					this.token = this.token + this.source[this.indiceAtual];
//				}
//			}
//			this.indiceAtual++;
//		}
//	}
//	
//	private void removeEspacos() {
//		while(this.source[this.indiceAtual]==' '){
//			this.indiceAtual++;
//		}
//	}
//
//	private void ignoraComentario(){
//		while((this.indiceAtual < this.source.length)&&(this.source[this.indiceAtual]!='\n')){
//			this.indiceAtual++;
//		}
////		this.indiceAtual++;
////		if((this.source[this.indiceAtual] == '%')||(this.source[this.indiceAtual] == '#')){
////			ignoraComentario();
////		}
//	}
//	
//	private boolean isEnd(String token) {
//		boolean isEnd = false;
//		switch (token){
//			case "end":
//				isEnd = true;
//				break;
//			case "endfunction":
//				isEnd = true;
//				break;
//			case "end_try_catch":
//				isEnd = true;
//				break;
//			case "end_unwind_protect":
//				isEnd = true;
//				break;
//			case "endfor":
//				isEnd = true;
//				break;
//			case "endgrent":
//				isEnd = true;
//				break;
//			case "endif":
//				isEnd = true;
//				break;
//			case "endparfor":
//				isEnd = true;
//				break;
//			case "endpwent":
//				isEnd = true;
//				break;
//			case "endswitch":
//				isEnd = true;
//				break;
//			case "endwhile":
//				isEnd = true;
//				break;
//		}
//		return isEnd;
//	}
//	
//	private void streamFile(String fileName){
//		String c = "";
//		try {
//			c = readFile(new FileInputStream(fileName), Charset.defaultCharset());
//		} catch (IOException e) {
//		}
//		this.content = c;
//	}
//	
//	private static String readFile(InputStream stream, Charset cs) throws IOException {
//
//		try {
//			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
//			StringBuilder builder = new StringBuilder();
//			char[] buffer = new char[8192];
//			int read;
//			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
//				builder.append(buffer, 0, read);
//			}
//			return builder.toString();
//		} finally {
//			stream.close();
//		}
//	}
//	
//	private boolean ehNumero(String possivelNumero) {
//		boolean ehNumero = true;
//		int size = possivelNumero.length();
//		for(int i = 0; (i < size) && ehNumero; i ++) {
//			if((possivelNumero.charAt(i) != '.')&&(possivelNumero.charAt(i) != ',')){
//				ehNumero = Character.isDigit(possivelNumero.charAt(i));
//			}
//		}
//		return ehNumero;
//	}
//}