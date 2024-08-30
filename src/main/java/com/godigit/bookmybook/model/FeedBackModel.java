package com.godigit.bookmybook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.godigit.bookmybook.dto.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "FeedBack")
@ToString(exclude = {"book", "user"})
public class FeedBackModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //    @Size(min = 1, max = 5)
    private Long rating;

    private String Comments;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = BookModel.class)
    @JoinColumn(name = "book_id")
    @JsonIgnore
    private BookModel book;

    @ManyToOne(cascade = CascadeType.PERSIST, targetEntity = UserModel.class)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserModel user;





    @PrePersist
    void pre(){
        if(this.user == null) {
            this.user = new UserModel();
        }

    }

}
