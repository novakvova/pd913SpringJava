package app.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="tbl_doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String phone;
}
