package at.htl.musicstore.logic;

import at.htl.musicstore.models.IdentityObject;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

class GenericRepository<T extends IdentityObject> {
    private Class<T> genericType;
    private List<T> models = new ArrayList<>();
    private List<State> states = new ArrayList<>();

    public GenericRepository(Class<T> clsType) {
        genericType = clsType;
    }

    protected void clear() {
        models.clear();
        states.clear();
    }
    protected void add(T model) {
        if (model == null)
            throw new IllegalArgumentException("model");

        models.add(model);
        states.add(State.Current);
    }

    public T create() {
        T model = null;
        try {
            model = genericType.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return model;
    }

    public int getSize() {
        return models.size();
    }

    public T[] getAll() {
        final T[] result = (T[])Array.newInstance(genericType, models.size());

        return models.toArray(result);
    }

    public T getById(int id) {
        T result = null;

        for (int i = 0; i < models.size() && result == null; i++) {
            if (models.get(i).getId() == id) {
                result = models.get(i);
            }
        }
        return result;
        // oder mit einem stream()
//        return (T)models.stream().filter(p -> p.getId() == id);
    }

    public boolean insert(T model) {
        if (model == null)
            throw new IllegalArgumentException("model");

        int maxId = 0;

        for (T m : models) {
            if (m.getId() > maxId) {
                maxId = m.getId();
            }
        }
        model.setId(maxId + 1);
        models.add(model);
        states.add(State.Added);
        return true;
    }

    public boolean update(T model) {
        if (model == null)
            throw new IllegalArgumentException("model");

        boolean result = false;
        T curModel = getById(model.getId());

        if (curModel != null) {
            int idx = models.indexOf(curModel);

            models.set(idx, model);
            if (states.get(idx) == State.Current) {
                states.set(idx, State.Modified);
            }
            result = true;
        }
        return result;
    }

    public boolean delete(int id) {
        boolean result = false;
        T curModel = getById(id);

        if (curModel != null) {
            int idx = models.indexOf(curModel);

            states.set(idx, State.Deleted);
            result = true;
        }
        return result;
    }

    protected T getAt(int idx) {
        T result = null;

        if (idx >= 0 && idx < models.size()) {
            result = models.get(idx);
        }
        return result;
    }

    protected List<T> getModels() {
        return models;
    }

    protected List<State> getStates() {
        return states;
    }
}
