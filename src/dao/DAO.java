/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import model.Employee;

/**
 *
 * @author Carlos
 */
public class DAO {

    private String table;

    public DAO(String table) {
        this.table = table;
    }

    public ObjectContainer loadConfiguration() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Employee.class).cascadeOnDelete(true);
        ObjectContainer container = Db4oEmbedded.openFile(config, table + ".db4o");
        return container;
    }

    public void closeConfiguration(ObjectContainer container) {
        container.close();
    }

}
