package org.example;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
  @author   Oleh
  @project   CompanyService
  @class  CompanyServiceImplTest
  @version  1.0.0 
  @since 15.09.2024 - 16.06
*/

class CompanyServiceImplTest {

    private final CompanyServiceImpl service = new CompanyServiceImpl();

    // Tests for getTopLevelParent method

    // 1 When company has no parent, then it returns itself
    @Test
    public void whenCompanyHasNoParent_thenReturnsItself() {
        Company company = new Company(null, 50);
        Company result = service.getTopLevelParent(company);
        assertEquals(company, result);
    }

    // 2 When company has one parent, then it returns the parent
    @Test
    public void whenCompanyHasOneParent_thenReturnsParent() {
        Company parent = new Company(null, 100);
        Company child = new Company(parent, 50);
        Company result = service.getTopLevelParent(child);
        assertEquals(parent, result);
    }

    // 3 When company has multiple levels of parents, then it returns the top-level parent
    @Test
    public void whenCompanyHasMultipleParents_thenReturnsTopLevelParent() {
        Company topParent = new Company(null, 200);
        Company midParent = new Company(topParent, 150);
        Company child = new Company(midParent, 50);
        Company result = service.getTopLevelParent(child);
        assertEquals(topParent, result);
    }

    // 4 When multiple companies have the same parent, it still returns the top-level parent
    @Test
    public void whenMultipleCompaniesHaveSameParent_thenReturnsTopLevelParent() {
        Company topParent = new Company(null, 200);
        Company child1 = new Company(topParent, 50);
        Company child2 = new Company(topParent, 70);
        assertEquals(topParent, service.getTopLevelParent(child1));
        assertEquals(topParent, service.getTopLevelParent(child2));
    }

    // 5 When company is its own parent, it should not crash and return itself
    @Test
    public void whenCompanyIsItsOwnParent_thenReturnsItself() {
        Company company = new Company(null, 50);
        company.setParent(company);  // Company is its own parent
        Company result = service.getTopLevelParent(company);
        assertEquals(company, result);
    }

    // 6 When company is null, then it should throw an exception
    @Test
    public void whenCompanyIsNull_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.getTopLevelParent(null));
    }

    // Tests for getEmployeeCountForCompanyAndChildren method

    // 1 When company has no children, then returns its own employee count
    @Test
    public void whenCompanyHasNoChildren_thenReturnsOwnEmployeeCount() {
        Company company = new Company(null, 50);
        List<Company> companies = Collections.emptyList();
        long result = service.getEmployeeCountForCompanyAndChildren(company, companies);
        assertEquals(50, result);
    }

    // 2 When company has one child, it returns the sum of employees for both
    @Test
    public void whenCompanyHasOneChild_thenReturnsSumOfEmployees() {
        Company parent = new Company(null, 100);
        Company child = new Company(parent, 50);
        List<Company> companies = Arrays.asList(child);
        long result = service.getEmployeeCountForCompanyAndChildren(parent, companies);
        assertEquals(150, result);
    }

    // 3 When company has multiple children, it returns the total employee count for all
    @Test
    public void whenCompanyHasMultipleChildren_thenReturnsTotalEmployeeCount() {
        Company parent = new Company(null, 100);
        Company child1 = new Company(parent, 50);
        Company child2 = new Company(parent, 70);
        List<Company> companies = Arrays.asList(child1, child2);
        long result = service.getEmployeeCountForCompanyAndChildren(parent, companies);
        assertEquals(220, result);
    }

    // 4 When company has nested children, it returns the total count for all levels
    @Test
    public void whenCompanyHasNestedChildren_thenReturnsTotalEmployeeCount() {
        Company topParent = new Company(null, 150);
        Company midParent = new Company(topParent, 100);
        Company child = new Company(midParent, 50);
        List<Company> companies = Arrays.asList(midParent, child);
        long result = service.getEmployeeCountForCompanyAndChildren(topParent, companies);
        assertEquals(300, result);
    }

    // 5 When companies have no hierarchy, then returns only employee count for the company itself
    @Test
    public void whenNoHierarchy_thenReturnsOnlyCompanyEmployeeCount() {
        Company company = new Company(null, 100);
        List<Company> companies = Arrays.asList(new Company(null, 50), new Company(null, 70));
        long result = service.getEmployeeCountForCompanyAndChildren(company, companies);
        assertEquals(100, result);
    }

    // 6 When company has children with no employees, returns total count correctly
    @Test
    public void whenChildrenHaveNoEmployees_thenReturnsParentCount() {
        Company parent = new Company(null, 150);
        Company child1 = new Company(parent, 0);
        Company child2 = new Company(parent, 0);
        List<Company> companies = Arrays.asList(child1, child2);
        long result = service.getEmployeeCountForCompanyAndChildren(parent, companies);
        assertEquals(150, result);
    }

    // 7 When company has no employees but its children do, return children’s count
    @Test
    public void whenCompanyHasNoEmployees_thenReturnsChildrenTotal() {
        Company parent = new Company(null, 0);
        Company child1 = new Company(parent, 100);
        Company child2 = new Company(parent, 50);
        List<Company> companies = Arrays.asList(child1, child2);
        long result = service.getEmployeeCountForCompanyAndChildren(parent, companies);
        assertEquals(150, result);
    }

    // 8 When company has no employees and no children, then returns 0
    @Test
    public void whenCompanyHasNoEmployeesAndNoChildren_thenReturnsZero() {
        Company company = new Company(null, 0);
        List<Company> companies = Collections.emptyList();
        long result = service.getEmployeeCountForCompanyAndChildren(company, companies);
        assertEquals(0, result);
    }

    // 9 When company has mixed children (some with employees, some without), returns correct total
    @Test
    public void whenCompanyHasMixedChildren_thenReturnsCorrectTotal() {
        Company parent = new Company(null, 100);
        Company child1 = new Company(parent, 50);
        Company child2 = new Company(parent, 0);
        Company child3 = new Company(parent, 70);
        List<Company> companies = Arrays.asList(child1, child2, child3);
        long result = service.getEmployeeCountForCompanyAndChildren(parent, companies);
        assertEquals(220, result);
    }

    // 10 When company has children with two children, returns total count for all levels
    @Test
    public void whenCompanyHasChildrenWithChildren_thenReturnsTotalCount() {
        Company topParent = new Company(null, 100);
        Company midParent = new Company(topParent, 50);
        Company child1 = new Company(midParent, 20);
        Company child2 = new Company(midParent, 30);
        List<Company> companies = Arrays.asList(midParent, child1, child2);
        long result = service.getEmployeeCountForCompanyAndChildren(topParent, companies);
        assertEquals(200, result);
    }

    // 11 When company has cyclic child reference, it does not double count employees
    @Test
    public void whenCompanyHasCyclicChildReference_thenDoesNotDoubleCountEmployees() {
        Company parent = new Company(null, 200);
        Company child = new Company(parent, 50);
        parent.setParent(child);  // Create a cyclic reference
        List<Company> companies = Arrays.asList(child);
        long result = service.getEmployeeCountForCompanyAndChildren(parent, companies);
        assertEquals(250, result);  // Should not double count
    }

    // 12 When company is null, then it should throw an exception
    @Test
    public void whenCompanyIsNull_thenThrowsException2() {
        assertThrows(IllegalArgumentException.class, () -> service.getEmployeeCountForCompanyAndChildren(null, Collections.emptyList()));
    }

    // 13 When companies list is null, then it should throw an exception
    @Test
    public void whenCompaniesListIsNull_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.getEmployeeCountForCompanyAndChildren(new Company(null, 50), null));
    }

    // 14 When company and companies list are null, then it should throw an exception
    @Test
    public void whenCompanyAndCompaniesListAreNull_thenThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> service.getEmployeeCountForCompanyAndChildren(null, null));
    }

}