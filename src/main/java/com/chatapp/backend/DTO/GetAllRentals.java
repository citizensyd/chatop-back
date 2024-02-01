package com.chatapp.backend.DTO;

import lombok.Data;

import java.util.List;

@Data
public class GetAllRentals {

    private List<RentalDTO> rentals;

}
