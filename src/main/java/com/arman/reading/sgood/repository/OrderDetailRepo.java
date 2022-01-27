package com.arman.reading.sgood.repository;

import com.arman.reading.sgood.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepo extends JpaRepository<OrderDetail, Integer> {
}
