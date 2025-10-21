package carsharingapp.app.dto.rental;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Data;

@Data
public class RentalSearchParameters {
    @NotEmpty
    private List<Long> userIds;
    @NotEmpty
    private List<Boolean> isActive;
}
