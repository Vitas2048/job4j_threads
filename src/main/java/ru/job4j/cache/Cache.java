package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (s, i) -> {
            if (i.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are different");
            }
            return memory.put(s, i);
        }
        ) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }
}