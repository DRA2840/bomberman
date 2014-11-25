package br.unb.unbomber.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;

public class Graphics implements ApplicationListener {
	
	
	public static int WIDTH;
	public static int HEIGHT;
	
	/**
	 * Esse m�todo � chamado apenas uma vez quando o jogo � iniciado.
	 */
	public void create(){
		
		/**
		 * Pega o width e o height de bomberman-desktop
		 */
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
	}
	
	/**
	 * M�todo do Game Loop. Chamado constantemente durante o jogo.
	 */
	public void render(){
		
		Gdx.gl.glClearColor(0,0,0,1);
		//Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
	}
	
	/**
	 * Altera o tamanho da tela do jogo.
	 */
	public void resize(int widt, int height){
		
	}
	
	/**
	 * M�todo respons�vel por pausar o jogo.
	 */
	public void pause(){
		
	}
	
	/**
	 * M�todo chamado ap�s o pause(), retoma o jogo.
	 */
	public void resume(){
		
	}
	
	/**
	 * M�todo chamado apenas uma vez ao t�rmino do jogo.
	 */
	public void dispose(){
		
	}

}
