package carsharingapp.app.repository.rental;

import carsharingapp.app.model.Rental;
import carsharingapp.app.repository.SpecificationProvider;
import carsharingapp.app.repository.SpecificationProviderManager;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RentalSpecificationProviderManager implements SpecificationProviderManager<Rental> {
    private final List<SpecificationProvider<Rental>> specificationProviderList;

    @Override
    public SpecificationProvider<Rental> getSpecificationProvider(String key) {
        return specificationProviderList
                .stream()
                .filter(provider -> provider.getKey().equals(key))
                .findFirst()
                .orElseThrow(() ->
                        new NoSuchElementException("No specification provider found for key: "
                                + key));
    }
}
