package com.example.maktabsharif.homeservices.enumeration;

public enum OrderStatus {
    PENDING_OFFERS,      // Waiting for expert advice
    AWAITING_SELECTION,  // Waiting for specialist selection
    AWAITING_ARRIVAL,    // Waiting for the specialist to come to your location
    STARTED,
    COMPLETED,
    PAID
}
