package com.example.cours.endpoint.event.consumer.model;

import com.example.cours.PojaGenerated;
import com.example.cours.endpoint.event.model.PojaEvent;

@PojaGenerated
public record TypedEvent(String typeName, PojaEvent payload) {}
