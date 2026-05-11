package app.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class SingletonRegistry {

    /**
     * juste pour les classes Singleton
     */
    public interface Singleton {}

    private static final Map<Class<?>, Singleton> INSTANCES = new ConcurrentHashMap<>();

    private SingletonRegistry() {}

    /**
     * retourne l'instance d'une classe Singleton
     */
    public static <T extends Singleton> T get(Class<T> type, Supplier<? extends T> creator) {
        return type.cast(INSTANCES.computeIfAbsent(type, k -> creator.get()));
    }
}