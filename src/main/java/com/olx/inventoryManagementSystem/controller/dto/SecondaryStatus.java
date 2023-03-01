package com.olx.inventoryManagementSystem.controller.dto;

import lombok.*;



import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SecondaryStatus implements Serializable {
    String name;
    String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SecondaryStatus that = (SecondaryStatus) o;
        return name.equals(that.name);
    }

}
