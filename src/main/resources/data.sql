
insert into `brands` (`id`, `name`)
values
    (1, 'Toyota'),
    (2, 'Ford');

insert into `models` (`id`, `brand_id`, `name`)
values
    (1, 1, 'Camry'),
    (2, 1, 'Corolla'),
    (3, 2, 'Focus'),
    (4, 2, 'Fiesta');