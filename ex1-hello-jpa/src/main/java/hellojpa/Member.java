package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    //LocalDate나 LocalDateTime이 있으면 생략 가
    private Date lastModifiedDate;

    @Lob
    private String description;
//Getter, Setter ...
}