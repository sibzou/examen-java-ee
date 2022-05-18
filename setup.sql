create table kitchenType(
    id integer primary key,
    name text not null
);

create table restaurant(
    id integer primary key,
    name text not null,
    address text not null,
    kitchenTypeId integer not null references kitchenType(id)
);

create table dishType(
    id integer primary key,
    name text not null
);

create table dish(
    id integer primary key,
    name text not null,
    dishTypeId integer not null references dishType(id),
    price number not null,
    restaurantId integer not null references restaurant(id)
);

insert into kitchenType(name) values("italien"),
                                    ("japonais"),
                                    ("français"),
                                    ("américain"),
                                    ("indien"),
                                    ("chinois");

insert into restaurant(name, address, kitchenTypeId) values("Chez Vito", "12 Avenue du Général Leclerc, Nancy", 1),
                                                           ("Fuji Sushi", "133 Rue de la Barque, Metz", 2),
                                                           ("McDonald's", "1 Place de la République, Metz", 4);

insert into dishType(name) values("entrée"),
                                 ("plat"),
                                 ("dessert");

insert into dish(name, dishTypeId, price, restaurantId) values("Big Mac", 2, 8.49, 3),
                                                              ("Makis", 1, 7.02, 2),
                                                              ("Mochis", 3, 5.35, 2);
