package at.htl.musicstore.logic;

import at.htl.musicstore.models.IdentityObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class JdbcRepository<T extends IdentityObject> extends GenericRepository<T> implements AutoCloseable {
    private String connectionString = null;
    private String user = null;
    private String password = null;

    protected JdbcRepository(Class<T> cls) {
        this(cls, "jdbc:mariadb://localhost:3306/musicstoredb", "root", "passme");
    }
    protected JdbcRepository(Class<T> cls, String connectionString, String user, String password) {
        super(cls);
        this.connectionString = connectionString;
        this.user = user;
        this.password = password;
    }

    protected Connection createConnection() {
        Connection result = null;

        try {
            result = DriverManager.getConnection(connectionString, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected void executeSql(String sql) {
/*        Connection con1 = null;
        Statement stmt1 = null;
        try {
            con1 = createConnection();
            stmt1 = con1.createStatement();

            stmt1.execute(sql);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            if (stmt1 != null) {
                try {
                    stmt1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con1 != null) {
                try {
                    con1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
*/
        try (Connection con = createConnection();
             Statement stmt = con.createStatement()) {
            stmt.execute(sql);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected abstract void readModels();
    protected abstract void insertModel(T model);
    protected abstract void updateModel(T model);
    protected abstract void deleteModel(T model);

    public void save() {
        for (int i = 0; i < getSize(); i++) {
            T model = getAt(i);
            State state = getStates().get(i);

            if (state == State.Added) {
                insertModel(model);
            } else if (state == State.Modified) {
                updateModel(model);
            } else if (state == State.Deleted) {
                deleteModel(model);
            }
        }
        readModels();
    }
    public void close() {
        clear();
    }
}
