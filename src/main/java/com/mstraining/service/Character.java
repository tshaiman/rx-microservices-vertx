package com.mstraining.service;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Character {

    private String name;
    private List<String> superpowers = new ArrayList<>();
    private boolean villain;
    private int id;

    public Character(JsonObject json) {
        this.id = json.getInteger("id");
        this.name = json.getString("name");
        this.villain = json.getBoolean("villain");
        JsonArray powers = json.getJsonArray("superpowers");
        this.superpowers = powers.stream().map(Object::toString).collect(Collectors.toList());

    }

}
