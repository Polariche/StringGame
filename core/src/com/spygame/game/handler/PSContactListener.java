package com.spygame.game.handler;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.spygame.game.entity.Contactable;
import com.spygame.game.entity.Entity;
import com.spygame.game.entity.FixtureType;
import com.spygame.game.entity.Interactable;
import com.spygame.game.entity.Player;
import com.spygame.game.entity.Player.PlayerState;
import com.spygame.game.state.PlayState;

public class PSContactListener implements ContactListener {
	private Player player;
	
	public PSContactListener(PlayState s) {
		this.player = s.getPlayer();
	}
	
	@Override
	public void beginContact(Contact contact) {
		//System.out.println("START: " + contact.getFixtureA().getUserData() + " " + contact.getFixtureB().getUserData());
		
		Entity A = (Entity) contact.getFixtureA().getBody().getUserData();
		Entity B = (Entity) contact.getFixtureB().getBody().getUserData();
		
		
		if(contactInvolves(contact,FixtureType.PlayerFoot)) {
			if(A instanceof Player) {
				player.footContact.add(B);
			}
			if(B instanceof Player) {
				player.footContact.add(A);
			}
			player.setState(PlayerState.Grounded);
		}

		if(contactInvolves(contact,FixtureType.Player)) {
			if(A instanceof Interactable) {
				player.interactables.add((Interactable)A);
			}
			if(B instanceof Interactable) {
				player.interactables.add((Interactable)B);
			}
		}
		
		if(A instanceof Contactable) {
			((Contactable) A).startContact(contact.getFixtureB(), contact.getFixtureA());
		}
		
		if(B instanceof Contactable) {
			((Contactable) B).startContact(contact.getFixtureA(), contact.getFixtureB());
		}
	}

	@Override
	public void endContact(Contact contact) {
		//System.out.println("END: " + contact.getFixtureA().getUserData() + " " + contact.getFixtureB().getUserData());
		
		Entity A = (Entity) contact.getFixtureA().getBody().getUserData();
		Entity B = (Entity) contact.getFixtureB().getBody().getUserData();
		
		if(contactInvolves(contact,FixtureType.PlayerFoot)) {
			if(A instanceof Player) {
				player.footContact.remove(B);
			}
			if(B instanceof Player) {
				player.footContact.remove(A);
			}
			if(player.footContact.size() < 1) {
				player.setState(PlayerState.Flying);
			}
		}

		if(contactInvolves(contact,FixtureType.Player)) {
			if(A instanceof Interactable) {
				player.interactables.remove((Interactable)A);
			}
			if(B instanceof Interactable) {
				player.interactables.remove((Interactable)B);
			}
		}
		
		if(A instanceof Contactable) {
			((Contactable) A).endContact(contact.getFixtureB(), contact.getFixtureA());
		}
		
		if(B instanceof Contactable) {
			((Contactable) B).endContact(contact.getFixtureA(), contact.getFixtureB());
		}
	}
	
	private boolean contactInvolves(Contact c, FixtureType type) {
		return c.getFixtureA().getUserData().equals(type) || c.getFixtureB().getUserData().equals(type);
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
