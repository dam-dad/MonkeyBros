package dad.CoreJuego.Elementos;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

public class MyContactListener implements ContactListener {


    public void beginContact(Contact contact) {

        if (contact.getFixtureA().getBody().getUserData() == "CuerpoPersonaje") {

            if (contact.getFixtureB().getBody().getUserData() == "Muro") {

            }
        }
    }

    // Other methods in the ContactListener interface
    public void endContact(Contact contact) {}
    public void preSolve(Contact contact, Manifold oldManifold) {}
    public void postSolve(Contact contact, ContactImpulse impulse) {}
}
