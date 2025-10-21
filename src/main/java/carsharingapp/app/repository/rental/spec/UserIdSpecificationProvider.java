package carsharingapp.app.repository.rental.spec;

import carsharingapp.app.model.Rental;
import carsharingapp.app.repository.SpecificationProvider;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserIdSpecificationProvider implements SpecificationProvider<Rental> {
    private static final String USER_ID_FIELD_PARAMETER = "userIds";

    @Override
    public Specification<Rental> getSpecification(List<String> params) {
        List<Long> ids = params.stream()
                .map(Long::parseLong)
                .toList();
        return (root, query, cb) ->
                root.get("user").get("id").in(ids);

    }

    @Override
    public String getKey() {
        return USER_ID_FIELD_PARAMETER;
    }
}
