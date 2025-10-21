package carsharingapp.app.controller;

import carsharingapp.app.dto.rental.RentalRequestDto;
import carsharingapp.app.dto.rental.RentalResponseDto;
import carsharingapp.app.dto.rental.RentalSearchParameters;
import carsharingapp.app.model.User;
import carsharingapp.app.service.rental.RentalService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RentalResponseDto createRental(
            @RequestBody @Valid RentalRequestDto requestDto,
            Authentication authentication) {

        return rentalService.save(requestDto, getIdForAuthenticationUser(authentication));
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @GetMapping("/{id}")
    public RentalResponseDto getRentalById(
            @PathVariable @Positive Long id,
            Authentication authentication) {

        return rentalService
                .getRentalById(
                        id,
                        getIdForAuthenticationUser(authentication),
                        isManager(authentication));
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @GetMapping
    public List<RentalResponseDto> getAllRentals(
            @Valid RentalSearchParameters searchParameters,
            Pageable pageable,
            Authentication authentication
    ) {
        if (!isManager(authentication)) {
            searchParameters.setUserIds(List.of(getIdForAuthenticationUser(authentication)));
        } else {
            if (searchParameters.getUserIds() == null || searchParameters.getUserIds().isEmpty()) {
                searchParameters.setUserIds(null);
            }
        }
        return rentalService.getAllRentals(searchParameters, pageable);
    }

    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    @PostMapping("/{id}/return")
    public RentalResponseDto returnRental(
            @PathVariable @Positive Long id,
            Authentication authentication) {
        return rentalService.returnRental(
                id,
                getIdForAuthenticationUser(authentication),
                isManager(authentication)
        );
    }

    private Long getIdForAuthenticationUser(Authentication authentication) {
        return ((User) authentication.getPrincipal()).getId();
    }

    private boolean isManager(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(roles -> roles.getAuthority().equals("ROLE_MANAGER"));
    }
}
