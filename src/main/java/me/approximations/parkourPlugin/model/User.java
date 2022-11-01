package me.approximations.parkourPlugin.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class User {
    private UUID uuid;
    private long bestTime;
}
