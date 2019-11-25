package com.cargotracker.monolith.booking.domain.aggregates;

import javax.persistence.*;
/**
 * Cargo
 */
@Entity
@NamedQueries({
  @NamedQuery(name = "Cargo.findAll",
              query = "Select c from Cargo c"),
  @NamedQuery(name = "Cargo.findByBookingId",
              query = "Select c from Cargo c where c.bookingId = :bookingId"),
  @NamedQuery(name = "Cargo.getAllBookingIds",
              query = "Select c.bookingId from Cargo c")
})
public class Cargo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private BookingId bookingId;

  @Embedded
  private BookingAmount bookingAmount;



}
