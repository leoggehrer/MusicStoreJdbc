package at.htl.musicstore.logic;

///CRUD....CreateReadUpdateDelete
public interface Repository<T> {
    T[] getAll();
    T getById(int id);
    T create();
    boolean insert(T model);
    boolean update(T model);
    boolean delete(int id);
    void save();
}
