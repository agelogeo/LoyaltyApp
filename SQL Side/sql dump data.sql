INSERT INTO `customers` ( `name`, `surname`, `phone`, `barcode`, `stamps`, `coupons_used`, `visits`, `last_visit`) VALUES
( 'Pasxalis', 'Angelopoulos', '2310209135', '45556', 3, 0, 0, ''),
( 'g', 'g', '55', '74323', 0, 0, 0, ''),
( 'Alex', 'Greek', '123', '79471', 0, 0, 0, ''),
( 'σουκης', 'σουλης', '666', '95227', 0, 0, 0, ''),
( 'foivos', 'trela', '120', '76371', 0, 0, 0, ''),
( 'γοβργος', 'αγελ', '0000', '95961', 0, 0, 0, ''),
( 'Andrew', 'Drazdou', '1212184', '58431', 0, 0, 0, ''),
( 'qaz', 'qwer', '1122', '78652', 0, 0, 0, ''),
( 'salpi', 'salpi', '44444', '59077', 0, 0, 0, ''),
( 't', 'tt', '0967', '53304', 0, 0, 0, ''),
( 'sissi', 'karioti', '56556', '58185', 0, 0, 0, ''),
( 'stavros', 'anton', '12321', '95165', 0, 0, 0, ''),
( 'mama', 'mama', '565', '90601', 0, 0, 0, ''),
( 'drift', 'tokyo', '2004', '44647', 0, 0, 0, ''),
( 'georgios', 'liak', '6983867324', '62998', 0, 0, 0, ''),
( 'Joey', 'Tribbiani', '012345', '12345', 3, 10, 101, '1-2-2017'),
( 'Rachel', 'Green', '012346', '12346', 4, 11, 102, '1-2-2017'),
( 'Ross', 'Geller', '012347', '12347', 7, 19, 151, '2-2-2017'),
( 'Monica', 'Geller', '012348', '12348', 2, 5, 10, '10-2-2017'),
( 'Chandler', 'Bing', '252885', '25285', 7, 8, 87, '19-2-2017'),
( 'Phoebe', 'Buffay', '012543', '12333', 0, 10, 11, '29-1-2017');

INSERT INTO `coupons` (`name`, `required_stamps`) VALUES
('Volemataki', 1),
('Medium', 3),
('Extreme', 10),
('Coupon #1', 1),
('Coupon #31', 4),
('Coupon #8', 8),
('Coupon #51', 5),
('Coupon #6', 66);


INSERT INTO `operators` (`username`, `password`, `access_level`, `first_name`, `last_name`, `phone`) VALUES
( 'admin', 'ney-8392', '1', 'Giorgos', 'Angelopoulos', '6976942475'),
( 'sofia278', 'ney-8392', '2', 'Sofia', 'Karioti', '6976942476'),
( 'z', 'z', '2', 'g', 'g', '55'),
( 'makrina', '111', '2', 'Makrina', 'Kar', '6969'),
( 'dominic', 'admin', '1', 'Dom', 'Toreto', '2001');
