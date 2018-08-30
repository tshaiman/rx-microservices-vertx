package com.mstraining.service;

import io.reactivex.Observable;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.client.HttpResponse;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.mstraining.service.Helpers.client;

public class MainServiceConsumer {

    public static void main(String[] args) {

        SuperHeroesService.run();

        /*client().get("/heroes").rxSend()
                .map(HttpResponse::bodyAsJsonObject)
                .map(JsonObject::size)
                .subscribe(length-> System.out.println("number of heroes is " + length));

        client().get("/villains").rxSend()
                .map(HttpResponse::bodyAsJsonObject)
                .map(js->js.stream().map(Map.Entry::getValue).collect(Collectors.toList()))
                .flatMapObservable(Observable::fromIterable)
                .cast(String.class)
                .subscribe(rs-> System.out.println(rs));*/

        String name1 = "Yoda";
        String name2 = "clement";

        CheckIsHero(name1);
        CheckIsHero(name2);


    }

    private static void CheckIsHero(String name) {
        client().get("/heroes").rxSend()
                .map(HttpResponse::bodyAsJsonObject)
                .filter(js-> contains(name,js))
                .subscribe(x-> System.out.println("Yes ," + name + " is an hero !"),
                        Throwable::printStackTrace,
                        () -> System.out.println("No , " + name + "is not an hero !"));
    }

    private static boolean contains(String name, JsonObject json) {
        return json.stream().anyMatch(v->v.getValue().toString().equalsIgnoreCase(name));
    }
}
