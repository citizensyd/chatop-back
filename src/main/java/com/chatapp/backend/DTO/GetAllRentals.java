package com.chatapp.backend.DTO;

import java.util.List;

public class GetAllRentals {

    private List<RentalDTO> rentals;

    public GetAllRentals() {
    }

    public GetAllRentals(List<RentalDTO> rentals) {
        this.rentals = rentals;
    }

    // Getters et setters
    public List<RentalDTO> getRentals() {
        return rentals;
    }

    public void setRentals(List<RentalDTO> rentals) {
        this.rentals = rentals;
    }
}
