package com.scm.Entity;


import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class contact {
    @Id
    private String  id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    //annonation create karenege jo file validation karega
    //size
    //resolution
    private String picture;
    @Column(length = 500)
    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkedlinLink;
    @ManyToOne
    private User user;
     @OneToMany(mappedBy = "contacts" ,cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
  private List<SocialLink>sociallink=new ArrayList<>();



}
