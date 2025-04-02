package com.tasker.Tasker.model;

import com.tasker.Tasker.entity.User;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "workspaces")
public class Workspace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "workspaces")
    private List<User> members;

    @OneToMany(mappedBy = "workspace")
    private List<Project> projects;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<User> getMembers() { return members; }
    public void setMembers(List<User> members) { this.members = members; }
    public List<Project> getProjects() { return projects; }
    public void setProjects(List<Project> projects) { this.projects = projects; }
}