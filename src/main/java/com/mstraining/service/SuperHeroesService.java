package com.mstraining.service;


import io.reactivex.Completable;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.RoutingContext;

import java.util.*;
import java.util.stream.Collectors;

public class SuperHeroesService {

    public static void main(String[] args) {
        new SuperHeroesService().start().subscribe();
    }

    private Map<Integer, Character> villains;
    private Map<Integer, Character> heroes;

    public Completable start() {

        Vertx vertx = Vertx.vertx();
        //Router
        Router router = Router.router(vertx);
        router.get("/heroes").handler(this::getAllHeroes);
        router.get("/villains").handler(this::getAllVillains);

        return vertx.fileSystem().rxReadFile("src/main/resources/characters.json")
                .map(buffer -> buffer.toJsonArray().stream().map(jObj -> new Character((JsonObject) jObj)).collect(Collectors.toList()))
                .doOnSuccess(list -> System.out.println("Loaded " + list.size() + " SuperHeroes from file"))
                .doOnSuccess(list -> {
                    this.villains = list.stream().filter(Character::isVillain).collect(HashMap::new,(map,item)-> map.put(item.getId(),item),HashMap::putAll);
                    this.heroes = list.stream().filter(ch-> !ch.isVillain()).collect(HashMap::new,(map,item)-> map.put(item.getId(),item),HashMap::putAll);
                }).flatMap(x->
                vertx.createHttpServer()
                .requestHandler(router::accept)
                .rxListen(8080))
                .toCompletable();


    }

    private void getAllVillains(RoutingContext rc) {
        rc.response().end(villains.values().stream()
                .collect(JsonObject::new,
                        (json,item) -> json.put(String.valueOf(item.getId()),item.getName()),
                        JsonObject::mergeIn).encodePrettily());


    }

    private void getAllHeroes(RoutingContext rc) {
        rc.response().end(
                this.heroes.values().stream()
                .collect(JsonObject::new,(json,item)-> json.put(String.valueOf(item.getId()),item.getName()),
                        JsonObject::mergeIn).encodePrettily());
    }
}
