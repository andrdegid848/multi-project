insert into street (id, name, post_id)
values (1, 'street1', 1),
       (2, 'street2', 1),
       (3, 'street3', 2);

insert into house (id, name, build_date, floors_number, building_type, street_id, material)
values (1, 'house1', '2000-01-01', 5, 'HOUSE', 1, 'WOOD'),
       (2, 'house2', '2000-02-02', 7, 'HOUSE', 2, 'STONE'),
       (3, 'house3', '2000-03-03', 1, 'GARAGE', 3, 'BRICK'),
       (4, 'house4', '2000-04-04', 2, 'COMMERCIAL_PREMISES', 1, 'STEEL');

insert into flat (id, number, square, room_number, house_id)
values (1, 10, 10, 2, 1),
       (2, 20, 20, 1, 2),
       (3, 30, 30, 3, 3),
       (4, 40, 40, 2, 4),
       (5, 50, 50, 1, 1);