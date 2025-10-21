package carsharingapp.app.repository.rental.spec;

import carsharingapp.app.model.Rental;
import carsharingapp.app.repository.SpecificationProvider;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsActiveSpecificationProvider implements SpecificationProvider<Rental> {
    private static final String KEY_PARAMETER = "isActive";
    private static final String FIELD_PARAMETER = "actualRentalDate";

    @Override
    public Specification<Rental> getSpecification(List<String> params) {
        Boolean isActive = params.stream()
                .map(Boolean::parseBoolean)
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("Input parameters not be null"));

        return (root, query, cb) -> {
            if (isActive) {
                return cb.isNull(root.get(FIELD_PARAMETER));
            } else {
                return cb.isNotNull(root.get(FIELD_PARAMETER));
            }
        };
    }

    @Override
    public String getKey() {
        return KEY_PARAMETER;
    }
}
