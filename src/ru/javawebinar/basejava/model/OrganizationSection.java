package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {
    private static final long serialVertionUID = 1l;

    private List<Organization> organizationList;

    public OrganizationSection(Organization... organizationList) {
        this(Arrays.asList(organizationList));
    }

    public OrganizationSection(List<Organization> organizationList) {
        Objects.requireNonNull(organizationList, "organizations must not be null");
        this.organizationList = organizationList;
    }

    public OrganizationSection() {
        this.organizationList = new ArrayList<>();
    }

    public List<Organization> getOrganizationList() {
        return organizationList;
    }

    public void setOrganizationList(List<Organization> organizationList) {
        Objects.requireNonNull(organizationList, "organizations must not be null");
        this.organizationList = organizationList;
    }

    @Override
    public String toString() {
        return " " + organizationList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(organizationList, that.organizationList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationList);
    }
}
