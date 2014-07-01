/**
 * 
 */
package com.prodyna.pac.flightplan.scenario;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;

/**
 * TODO mfroehlich Comment me
 * 
 * @author mfroehlich
 *
 */
public class ScenarioPreparer {

    @Inject
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    @Inject
    private Logger logger;

    public void executeSqlFile(Scenario... scenarios) throws Exception {

        utx.begin();
        em.joinTransaction();

        for (Scenario scenario : scenarios) {
            for (String sqlStatement : scenario.getSqlStatements()) {
                try {
                    Query query = em.createNativeQuery(sqlStatement);
                    query.executeUpdate();
                } catch (Exception ex) {
                    logger.error("Error executing SQL-Statement '" + sqlStatement + "'.", ex);
                }
            }
        }

        utx.commit();
        em.clear();
    }

    // StringBuilder fileContent = new StringBuilder();
    //
    // LineNumberReader reader = null;
    // try {
    // reader = new LineNumberReader(new FileReader(file));
    // String line;
    // while ((line = reader.readLine()) != null) {
    // fileContent.append(line);
    // }
    // } catch (IOException e) {
    // logger.error("Error reading sql-file " + file.getName(), e);
    // } finally {
    // if (reader != null) {
    // try {
    // reader.close();
    // } catch (IOException e) {
    // logger.error("Error closing the reader.", e);
    // }
    // }
    // }
    //
    // String fileContentStr = fileContent.toString();
    // String[] sqlStatements = fileContentStr.split(";");
}
