package org.example;

/*
  @author   Oleh
  @project   CompanyService
  @class  ICompanyService
  @version  1.0.0 
  @since 15.09.2024 - 15.16
*/

import java.util.List;

public interface ICompanyService {

    Company getTopLevelParent(Company child);

    long getEmployeeCountForCompanyAndChildren(Company company, List<Company> companies);

}
