package app.Engine;

import app.entities.ingredients.BasicIngredient;
import app.entities.ingredients.chemical.AmmoniumChloride;
import app.entities.ingredients.natural.Lavender;
import app.entities.ingredients.natural.Mint;
import app.entities.ingredients.natural.Nettle;
import app.entities.ingredients.natural.Strawberry;
import app.entities.labels.BasicLabel;
import app.entities.shampoos.BasicShampoo;
import app.entities.shampoos.concreteShampooProducts.FiftyShades;
import app.entities.shampoos.concreteShampooProducts.FreshNuke;
import app.entities.shampoos.concreteShampooProducts.PinkPanther;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Engine implements Runnable {

    private EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("shampoo_company");

    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    public Engine() {

    }

    @Override
    public void run() {

        entityManager.getTransaction().begin();

        createFreshNukeShampoo();
        createFiftyShadesShampoo();
        createPinkPantherShampoo();
        entityManager.getTransaction().commit();
        entityManager.close();
    }


    void createFreshNukeShampoo() {

        BasicIngredient am = new AmmoniumChloride();
        BasicIngredient mint = new Mint();
        BasicIngredient nettle = new Nettle();

        BasicLabel label = new BasicLabel("Fresh Nuke Shampoo", "Contains mint and nettle");

        BasicShampoo shampoo = new FreshNuke(label);
        shampoo.getIngredients().add(mint);
        shampoo.getIngredients().add(nettle);
        shampoo.getIngredients().add(am);

        entityManager.persist(shampoo);
    }

    void createPinkPantherShampoo() {

        BasicIngredient lavender = new Lavender();
        BasicIngredient nettle = new Nettle();

        BasicLabel label = new BasicLabel("Pink Panther", "It’s made of Lavender and Nettle");

        BasicShampoo shampoo = new PinkPanther(label);
        shampoo.getIngredients().add(lavender);
        shampoo.getIngredients().add(nettle);

        entityManager.persist(shampoo);
    }

    void createFiftyShadesShampoo() {

        BasicIngredient strawberry = new Strawberry();
        BasicIngredient nettle = new Nettle();

        BasicLabel label = new BasicLabel("Fifty Shades", "It’s made of Strawberry and Nettle");

        BasicShampoo shampoo = new FiftyShades(label);

        shampoo.getIngredients().add(strawberry);
        shampoo.getIngredients().add(nettle);

        entityManager.persist(shampoo);
    }
}
