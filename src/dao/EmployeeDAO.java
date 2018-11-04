/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import java.util.List;
import model.Employee;

/**
 *
 * DAO de empleados
 */
public class EmployeeDAO extends DAO {

    public EmployeeDAO() {
        super("employee");
    }

    public List<Employee> readAll(ObjectContainer container) {
        Query query = container.query();
        query.constrain(Employee.class);
        ObjectSet os = query.execute();
        List<Employee> employees = os;
        return employees;
    }

    public void create(ObjectContainer container, Employee employee) {
        container.store(employee);
    }

    public Employee read(ObjectContainer container, int id) {
        Query query = container.query();
        query.constrain(Employee.class);
        query.descend("empno").constrain(id);
        ObjectSet os = query.execute();
        Employee employee = (Employee) os.next();
        return employee;
    }

    public void update(ObjectContainer container, Employee e) {
        Query query = container.query();
        query.constrain(Employee.class);
        query.descend("empno").constrain(e.getEmpno());
        ObjectSet os = query.execute();
        Employee employee = (Employee) os.next();
        employee.setEmpno(e.getEmpno());
        employee.setEname(e.getEname());
        employee.setLname(e.getLname());
        employee.setJob(e.getJob());
        employee.setMgr(e.getMgr());
        employee.setHiredate(e.getHiredate());
        employee.setSal(e.getSal());
        employee.setComm(e.getComm());
        employee.setDeptno(e.getDeptno());
        container.store(employee);
    }

    public void delete(ObjectContainer container, int id) {
        Query query = container.query();
        query.constrain(Employee.class);
        query.descend("empno").constrain(id);
        ObjectSet os = query.execute();
        while (os.hasNext()) {
            Employee employee = (Employee) os.next();
            container.delete(employee);
        }
    }
}
