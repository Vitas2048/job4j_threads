package ru.job4j.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {

    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) != null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        if (accounts.get(id) != null) {
            return Optional.of(accounts.get(id));
        }
        return Optional.empty();
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
            Account fromAcc = getById(fromId).get();
            if (fromAcc.amount() >= amount ) {
                Account newFrom = new Account(fromId, fromAcc.amount() - amount);
                Account newTo = new Account(toId, getById(toId).get().amount() + amount);
                update(newFrom);
                update(newTo);
                return true;
            }
        return false;
    }
}