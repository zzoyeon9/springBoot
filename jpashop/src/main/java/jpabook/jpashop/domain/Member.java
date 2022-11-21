package jpabook.jpashop.domain;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Column(length = 10, name = "USERNAME")
    private String name;

    @Embedded
    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return name;
    }

    public void setUsername(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Team getTeam() {
        return team;
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this); //**
    }
}
