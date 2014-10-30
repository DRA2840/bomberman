package br.unb.unbomber.systems;

import java.util.ArrayList;
import java.util.List;

import br.unb.unbomber.component.CellPlacement;
import br.unb.unbomber.component.Direction;
import br.unb.unbomber.component.Explosion;
import br.unb.unbomber.component.Timer;
import br.unb.unbomber.core.BaseSystem;
import br.unb.unbomber.core.Entity;
import br.unb.unbomber.core.EntityManager;
import br.unb.unbomber.core.Event;
import br.unb.unbomber.event.ExplosionStartedEvent;

public class ExplosionSystem extends BaseSystem {

	// List of already treated events
	List<ExplosionStartedEvent> treatedEvents;
	int nextId = 1;

	public ExplosionSystem() {
		super();
	}

	public ExplosionSystem(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public void start() {
		treatedEvents = new ArrayList<ExplosionStartedEvent>();
		super.start();
	}

	@Override
	public void update() {

		// flag to see if an event was already treated
		int flag;

		// Get all ExplosionStartedEvents from EntityManager
		List<Event> events = getEntityManager().getEvents(
				ExplosionStartedEvent.class);

		// treat all non treated events
		for (Event event : events) {

			// check if this event was already treated
			flag = 0;
			for (int i = 0; i < treatedEvents.size(); i++) {
				if (treatedEvents.get(i).getOwnerId() == event.getOwnerId()) {
					flag = 1;
					break;
				}
			}
			// if event was not treated
			if (flag == 0) {
				// typecasting to use the specific event
				ExplosionStartedEvent explosionStartedEvent = (ExplosionStartedEvent) event;
				// creating explosion
				createExplosion(explosionStartedEvent.getInitialPosition(),
						explosionStartedEvent.getPower());
				// put the treated event on the treatedEvents list
				treatedEvents.add(explosionStartedEvent);
			}
		}

	}
	
	/** 
	   \mainpage
	   \author Guilherme Pedrinha, Giulia Aleixo, Hichemm Khalyd.
	   \since 12/10/2014
	   \version 1.0
	 */ 

	/**
	  \brief cria a explos�o*/
	public void createExplosion(CellPlacement expPlacement, int expRange) {
		/**
		 \details Cria a explos�o recebendo o posicionamento e o range da explos�o
		  \param expPlacement indica um array com duas posi��es
		  \param expRange indica um inteiro com o Range da explos�o
		  \return essa fun��o n�o retorna nada		  
		 */
		
		//cria uma entidade explos�o
		Entity explosionEntity = new Entity();
		//adiciona a nova entidade no entity manager
		getEntityManager().addEntity(explosionEntity);
		
		//cria um objeto explos�o
		Explosion exp = new Explosion();
		//seta o id dela com o id da entidade explos�o
		exp.setEntityId(explosionEntity.getEntityId());
		//seta o range com o range recebido pelo parametro
		exp.setRange(expRange);
		
		// seta o id do expPlacement com o id da entidade explos�o
		expPlacement.setEntityId(explosionEntity.getEntityId());
		//cria um timer de 16 turnos
		Timer expTimer = new Timer(16, null);
        //adiciona o objeto exp , o exlPlacement e o expTimer como components da entidade explos�o 
		explosionEntity.addComponent(exp);
		explosionEntity.addComponent(expPlacement);
		explosionEntity.addComponent(expTimer);
		
		//seta a dire��o a qual a explos�o vai se propagar para cima
		exp.setPropagationDirection(Direction.UP);
		propagateExplosion(exp, expPlacement, expRange);
		//seta a dire��o a qual a explos�o vai se propagar para baixo
		exp.setPropagationDirection(Direction.DOWN);
		propagateExplosion(exp, expPlacement, expRange);
		//seta a dire��o a qual a explos�o vai se propagar para a esquerda
		exp.setPropagationDirection(Direction.LEFT);
		propagateExplosion(exp, expPlacement, expRange);
		//seta a dire��o a qual a explos�o vai se propagar para a direita
		exp.setPropagationDirection(Direction.RIGHT);
		propagateExplosion(exp, expPlacement, expRange);
		
		
	}

	/**
	  \brief Propaga a explos�o pelo grid*/
	public void propagateExplosion(Explosion exp, CellPlacement cellPlacement, int range) {
		/**
		  \details Propaga a explos�o pelo grid
		  \param exp recebe a explos�o
		  \param cellplacement recebe em que celula do grid a explos�o atualmente
		  \param range recebe o Range da explos�o
		  \return essa fun��o n�o retorna nada
		  */
		
		if (range != 0 && detectExplosionCollision(exp, cellPlacement)) {
			// enquanto range diferente de zero, detecta se h� colis�o com a proxima celula
			
			Entity explosionEntity = new Entity();
			getEntityManager().addEntity(explosionEntity);
			
			Explosion newExp = new Explosion();
			newExp.setEntityId(explosionEntity.getEntityId());
			newExp.setRange(range);
			 
			CellPlacement newExpPlacement = new CellPlacement();
			newExpPlacement.setEntityId(explosionEntity.getEntityId());
			
			if(exp.getPropagationDirection() == Direction.UP){
				//faz com que a explos�o ande uma c�lula para cima
				newExpPlacement.setCellX(cellPlacement.getCellX());
				newExpPlacement.setCellY(cellPlacement.getCellX() + 1);
			}
			else if(exp.getPropagationDirection() == Direction.DOWN){
				//faz com que a explos�o ande uma c�lula para cbaixo
				newExpPlacement.setCellX(cellPlacement.getCellX());
				newExpPlacement.setCellY(cellPlacement.getCellX() - 1);
			}
			else if(exp.getPropagationDirection() == Direction.LEFT){
				//faz com que a explos�o ande uma c�lula para a esquerda
				newExpPlacement.setCellX(cellPlacement.getCellX() - 1);
				newExpPlacement.setCellY(cellPlacement.getCellX());
			}
			else{
				//faz com que a explos�o ande uma c�lula para a direita
				newExpPlacement.setCellX(cellPlacement.getCellX() + 1);
				newExpPlacement.setCellY(cellPlacement.getCellX());
			}
			
			//cria a dura��o da explos�o
			Timer expTimer = new Timer(16, null);

			explosionEntity.addComponent(exp);
			explosionEntity.addComponent(newExpPlacement);
			explosionEntity.addComponent(expTimer);
			
			--range;
			propagateExplosion(newExp,newExpPlacement,range);
		}
	}


	public boolean detectExplosionCollision(Explosion explosion, CellPlacement cellPlacement) {
		return true;
	}

	public void processExplosionCollision(Entity entity) {

	}

}
