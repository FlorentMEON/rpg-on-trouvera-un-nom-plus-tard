package com.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * Cette class permet de gérer des actions à la chaine.
 * On crée un gestionnaire d'action, puis on les ajoute une à une ou plusieurs à la fois via une list.
 * Enfin, on les joue.
 */
public class ActionManager {
    private Queue<Consumer<Runnable>> fileDAttente = new LinkedList<>();

    // 1. Pour une action avec délai (Animations, projectiles, pauses)
    public void ajouterAction(Consumer<Runnable> action) {
        fileDAttente.add(action);
    }

    // 2. Pour UNE action instantanée (Soin, gain de dé)
    public void ajouterAction(Runnable actionInstantanee) {
        fileDAttente.add(fin -> {
            actionInstantanee.run();
            fin.run();
        });
    }

    // 🌟 3. LA NOUVELLE MÉTHODE : Pour une LISTE d'actions instantanées
    public void ajouterAction(List<Runnable> actions) {
        for (Runnable action : actions) {
            // On réutilise la méthode #2 pour chaque action de la liste !
            this.ajouterAction(action);
        }
    }

    // 4. Le lanceur de la machine
    public void jouerProchaineAction(Runnable onAllFinished) {
        if (fileDAttente.isEmpty()) {
            if (onAllFinished != null) onAllFinished.run();
            return;
        }

        Consumer<Runnable> action = fileDAttente.poll();
        action.accept(() -> {
            jouerProchaineAction(onAllFinished);
        });
    }
}