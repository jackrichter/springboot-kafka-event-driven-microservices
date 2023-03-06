package net.javaguides.stockservice.repository;

import net.javaguides.stockservice.entity.OrderEventDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventDetailsRepository extends JpaRepository<OrderEventDetails, Long> {
}
