/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.query.Query;
import java.util.List;
import model.Job;

/**
 *
 * DAO de puestos
 */
public class JobDAO {

    private static JobDAO singleton;

    public static JobDAO getInstance() {
        if (singleton == null) {
            singleton = new JobDAO();
        }
        return singleton;
    }

    private JobDAO() {
    }

    public ObjectContainer loadConfiguration() {
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().objectClass(Job.class).cascadeOnDelete(true);
        ObjectContainer container = Db4oEmbedded.openFile(config, "job.db4o");
        return container;
    }

    public void closeConfiguration(ObjectContainer container) {
        container.close();
    }

    public List<Job> readAll(ObjectContainer container) {
        Query query = container.query();
        query.constrain(Job.class);
        ObjectSet os = query.execute();
        List<Job> jobs = os;
        return jobs;
    }

    public void create(ObjectContainer container, Job job) {
        container.store(job);
    }

    public Job read(ObjectContainer container, int id) {
        Query query = container.query();
        query.constrain(Job.class);
        query.descend("jobno").constrain(id);
        ObjectSet os = query.execute();
        Job job = (Job) os.next();
        return job;
    }

    public void update(ObjectContainer container, Job j) {
        Query query = container.query();
        query.constrain(Job.class);
        query.descend("jobno").constrain(j.getJobno());
        ObjectSet os = query.execute();
        Job job = (Job) os.next();
        job.setJobno(j.getJobno());
        job.setJname(j.getJname());
        job.setGradesal(j.getGradesal());
        container.store(job);
    }

    public void delete(ObjectContainer container, int id) {
        Query query = container.query();
        query.constrain(Job.class);
        query.descend("jobno").constrain(id);
        ObjectSet os = query.execute();
        while (os.hasNext()) {
            Job job = (Job) os.next();
            container.delete(job);
        }
    }

}
