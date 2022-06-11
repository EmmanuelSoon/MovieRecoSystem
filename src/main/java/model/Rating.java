package model;

import javax.persistence.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@Entity
public class Rating {
    private String item;
    private double value;

    public int compareTo(Rating other){
        if (this.value > other.getValue()) return 1;
        else if (this.value < other.getValue()) return -1;
        else return 0;
    }
}
