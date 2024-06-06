package repo;

import com.example.iss.HibernateUtil;
import domain.Carte;
import domain.Exemplar;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CarteDBRepository extends MemoryRepository<Carte> {
    private final Set<Integer> usedCodExemplars = new HashSet<>();

    private final String JDBC_URL = "jdbc:sqlite:C:\\Users\\curca\\Desktop\\sem 1\\iss\\lab4\\iss\\books.db";
    private Connection connection;

    public CarteDBRepository() throws RepositoryException {
        openConnection();
        //addBooksAndExemplars();
        createTable();
        loadDataInMemory();
    }

    private void openConnection() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(JDBC_URL);

        try {
            if (connection == null || connection.isClosed()) {
                connection = ds.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        try (Statement st = connection.createStatement()) {
            st.execute("CREATE TABLE IF NOT EXISTS carti(id INT, titlu VARCHAR(255), autor VARCHAR(255));");
            st.execute("CREATE TABLE IF NOT EXISTS exemplare(cod_exemplar INT, disponibil INT, carte varchar(255));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadDataInMemory() {
        for (Carte carte : getAll()) {
            data.add(carte);
        }
    }

    public void addBooksAndExemplars() throws RepositoryException {
        Carte[] books = {
                new Carte(1, "Harry Potter și Piatra Filozofală", "J.K. Rowling", "Adventure"),
                new Carte(2, "Domnișoara Peregrine", "Ransom Riggs", "Thriller"),
                new Carte(3, "Cronicile din Narnia", "C.S. Lewis", "Fantasy"),
                new Carte(4, "Game of Thrones", "George R.R. Martin", "Adventure"),
                new Carte(5, "To Kill a Mockingbird", "Harper Lee", "Historical"),
                new Carte(6, "1984", "George Orwell", "Dystopian"),
                new Carte(7, "Pride and Prejudice", "Jane Austen", "Romance"),
                new Carte(8, "The Catcher in the Rye", "J.D. Salinger", "Contemporary"),
                new Carte(9, "The Great Gatsby", "F. Scott Fitzgerald", "Contemporary"),
                new Carte(10, "The Hobbit", "J.R.R. Tolkien", "Adventure"),
                new Carte(11, "Moby Dick", "Herman Melville", "Adventure"),
                new Carte(12, "Dracula", "Bram Stoker", "Thriller")
        };

        for (Carte book : books) {
            for (int i = 1; i <= 5; i++) {
                Exemplar exemplar = new Exemplar();
                exemplar.setCodExemplar(generateUniqueCodExemplar());
                exemplar.setDisponibil(1);
                exemplar.setCarte(book);
                addExemplar(book, exemplar);
            }
        }
    }

    private int generateUniqueCodExemplar() {
        int codExemplar;
        do {
            codExemplar = generateRandomCodExemplar();
        } while (usedCodExemplars.contains(codExemplar));
        usedCodExemplars.add(codExemplar);
        return codExemplar;
    }

    private int generateRandomCodExemplar() {
        Random random = new Random();
        return random.nextInt(10000);
    }

    public List<Carte> getAll() {
        List<Carte> carti = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM carti")) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String titlu = rs.getString("titlu");
                String autor = rs.getString("autor");
                String categorie = rs.getString("categorie");
                Set<Exemplar> coduriExemplare = getCoduriExemplareForCarte(id);
                carti.add(new Carte(id, titlu, autor, categorie, coduriExemplare));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carti;
    }

    private Set<Exemplar> getCoduriExemplareForCarte(int carteId) {
        Set<Exemplar> exemplare = new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT cod_exemplar, disponibil FROM exemplare WHERE carte_id = ?")) {
            statement.setInt(1, carteId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int codExemplar = rs.getInt("cod_exemplar");
                int disponibil = rs.getInt("disponibil");
                Exemplar exemplar = new Exemplar();
                exemplar.setCodExemplar(codExemplar);
                exemplar.setDisponibil(disponibil);

                Carte carte = new Carte();
                carte.setId(carteId);
                exemplar.setCarte(carte);

                exemplare.add(exemplar);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exemplare;
    }

    public void add(Carte carte) throws RepositoryException {
        if (!data.contains(carte)) {
            super.add(carte);
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO carti VALUES (?,?,?,?)")) {
                statement.setInt(1, carte.getId());
                statement.setString(2, carte.getTitlu());
                statement.setString(3, carte.getAutor());
                statement.setString(4, carte.getCategorie());
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RepositoryException("Error adding Carte to database", e);
            }
            for (Exemplar exemplar : carte.getCoduriExemplare()) {
                addExemplar(carte, exemplar);
            }
        } else {
            throw new RepositoryException("Cannot duplicate repository objects!");
        }
    }


    public void addExemplar(Carte carte, Exemplar exemplar) throws RepositoryException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO exemplare (cod_exemplar, disponibil, carte_id) VALUES (?,?,?)")) {
            statement.setInt(1, exemplar.getCodExemplar());
            statement.setInt(2, exemplar.getDisponibil());
            statement.setInt(3, carte.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RepositoryException("Error adding Exemplar to database", e);
        }
    }


    public void update(int id, Carte newCarte) {
        int index = poz(id);
        if (index != -1) {
            super.update(id, newCarte);
            try (PreparedStatement statement = connection.prepareStatement("UPDATE carti SET titlu=?, autor=?, categorie=? WHERE id=?")) {
                statement.setString(1, newCarte.getTitlu());
                statement.setString(2, newCarte.getAutor());
                statement.setString(3, newCarte.getCategorie());
                statement.setInt(4, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(int id) {
        int index = poz(id);
        if (index != -1) {
            super.delete(id);
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM carti WHERE id=?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM exemplare WHERE carte_id=?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Carte getById(int id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM carti WHERE id = ?")) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String titlu = rs.getString("titlu");
                String autor = rs.getString("autor");
                String categorie = rs.getString("categorie");
                Set<Exemplar> coduriExemplare = getCoduriExemplareForCarte(id);
                return new Carte(id, titlu, autor, categorie, coduriExemplare);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void imprumutaExemplar(int codExemplar) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE exemplare SET disponibil = 0 WHERE cod_exemplar = ?")) {
            statement.setInt(1, codExemplar);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean verificaDisponibilitateExemplar(int codExemplar) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT disponibil FROM exemplare WHERE cod_exemplar = ?")) {
            statement.setInt(1, codExemplar);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int disponibil = rs.getInt("disponibil");
                return disponibil == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void returneazaExemplar(int codExemplar) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE exemplare SET disponibil = 1 WHERE cod_exemplar = ?")) {
            statement.setInt(1, codExemplar);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean verificaExemplarImprumutat(int codExemplar) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT disponibil FROM exemplare WHERE cod_exemplar = ?")) {
            statement.setInt(1, codExemplar);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int disponibil = rs.getInt("disponibil");
                return disponibil == 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
