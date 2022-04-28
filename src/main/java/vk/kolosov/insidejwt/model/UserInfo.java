package vk.kolosov.insidejwt.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //инкрементация
    @Column(name = "id")
    private Integer id; //serial идет как int

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_password")
    private String password;

    @OneToMany(mappedBy = "id") //поле связываемой таблицы в этом классе
    private List<Message> messageList;

    public UserInfo(String name, String password) {
        this.name = name;
        this.password = password;
    }
}
