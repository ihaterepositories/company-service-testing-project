package org.example;

/*
  @author   Oleh
  @project   CompanyService
  @class  CompanyServiceImpl
  @version  1.0.0 
  @since 15.09.2024 - 15.17
*/

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompanyServiceImpl implements ICompanyService {

    @Override
    public Company getTopLevelParent(Company child) {

        if (child == null) {
            throw new IllegalArgumentException("Company cannot be null");
        }

        if (child.getParent() == child) {
            return child;
        }

        if(child.getParent() != null ){
            child = this.getTopLevelParent(child.getParent());
        }

        return child;
    }

    private Set<Company> getAllChildrenAndCompanyToSet(Company company, List<Company> companies) {
        Set<Company> children = new HashSet<>();

        children.add(company);

        for (Company cmp : companies) {
            Company current = cmp;

            while (current.getParent() != null) {
                if (current.getParent().equals(company)) {
                    children.add(cmp);
                    break;
                }
                current = current.getParent();
            }
        }
        return children;
    }

    @Override
    public long getEmployeeCountForCompanyAndChildren(Company company, List<Company> companies) {

        if (company == null || companies == null) {
            throw new IllegalArgumentException("Company and companies cannot be null");
        }

        Set<Company> children = this.getAllChildrenAndCompanyToSet(company, companies);
        long result = 0;
        for (Company cmp : children) {
            result += cmp.getEmployeesCount();
        }
        return result;
    }

}
