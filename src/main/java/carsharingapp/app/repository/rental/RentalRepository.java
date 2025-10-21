package carsharingapp.app.repository.rental;

import carsharingapp.app.model.Rental;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface RentalRepository extends JpaRepository<Rental, Long>,
        JpaSpecificationExecutor<Rental> {

    @Query("SELECT r FROM Rental r "
            + "WHERE r.user.id = :userId "
            + "AND r.car.id = :carId "
            + "AND r.isActive = true "
            + "AND r.rentalDate <= :returnDate "
            + "AND r.returnDate >= :rentalDate")
    Optional<Rental> findOverlappingRental(
            Long userId,
            Long carId,
            LocalDate rentalDate,
            LocalDate returnDate);

    @EntityGraph(attributePaths = {"car"})
    Page<Rental> findAll(Specification<Rental> spec, Pageable pageable);

    @EntityGraph(attributePaths = {"car"})
    Optional<Rental> findById(Long rentalId);
}
