package ch.zhaw.springboot.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToMany
    @JoinTable(name = "group_members", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> members;
    
    public Group(List<Person> members) {
        this.members = members;
    }
    
    public long getId() {
        return this.id;
    }
    
    public void addMember(Person member) {
        this.members.add(member);
    }
    
    public List<Person> getMembers() {
        return this.members;
    }
    
    public void removeMember(Person member) {
        this.members.remove(member);
    }
    
}
