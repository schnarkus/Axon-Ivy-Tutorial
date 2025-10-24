package hello.world;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

// The Ivy API entrypoint used in your other project. Depending on classpath this import may differ.
// The example uses a static Ivy.persistence() accessor as in the working project output.
import ch.ivyteam.ivy.environment.Ivy;

/**
 * Small helper that demonstrates obtaining an EntityManager from Axon Ivy and
 * performing simple persist / find operations.
 */
public final class DatabaseAccess {
  private DatabaseAccess() {}

  public static void persistSampleData() {
    EntityManager em = null;
    EntityTransaction tx = null;
    try {
      // Use the logical persistence unit name from persistence.xml
      em = Ivy.persistence().get("helloworld").createEntityManager();
      tx = em.getTransaction();
      tx.begin();

      DataEntity d = new DataEntity();
      d.setFirstname("Max");
      d.setLastname("Mustermann");
      d.setMessage("Hello from DatabaseAccess");

      em.persist(d);

      tx.commit();
    } catch (Exception e) {
      if (tx != null && tx.isActive()) tx.rollback();
      throw new RuntimeException("Failed to persist sample data", e);
    } finally {
      if (em != null) em.close();
    }
  }

  public static DataEntity findData(long id) {
    EntityManager em = null;
    try {
      em = Ivy.persistence().get("helloworld").createEntityManager();
      return em.find(DataEntity.class, Long.valueOf(id));
    } finally {
      if (em != null) em.close();
    }
  }
}
