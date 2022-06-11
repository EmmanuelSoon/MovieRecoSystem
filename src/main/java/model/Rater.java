package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
@Entity
public class Rater {
    @Id
    private int id;
    @OneToMany(mappedBy="raterId")
    private List<Rating> ratings;
}
