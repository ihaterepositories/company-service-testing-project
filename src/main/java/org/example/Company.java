package org.example;

/*
  @author   Oleh
  @project   CompanyService
  @class  Company
  @version  1.0.0 
  @since 15.09.2024 - 15.16
*/

public class Company {

    private Company parent;
    private long employeesCount;

    public Company(Company parent, long employeesCount) {
        this.parent = parent;
        this.employeesCount = employeesCount;
    }

    public Company getParent() {
        return parent;
    }

    public long getEmployeesCount() {
        return employeesCount;
    }

    public void setParent(Company parent) {
        this.parent = parent;
    }

}
