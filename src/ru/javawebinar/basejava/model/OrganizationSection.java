package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSection extends AbstractSection<Organization> {
    private List<Organization> organizationList;

    public OrganizationSection() {
        this.organizationList = new ArrayList<>();
    }

    @Override
    public void addInfo(Organization organization) {
        organizationList.add(organization);
    }

    @Override
    public String toString() {
        return " " + organizationList;
    }
}
