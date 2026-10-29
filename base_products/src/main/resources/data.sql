INSERT INTO product (id, name, description, category, price_usd) VALUES
    ('P-101', 'Laptop Gamer XYZ', 'Laptop de 16 pulgadas con RTX 4060 y 16 GB de RAM', 'Computadoras', 1200.00),
    ('P-102', 'Monitor 27 pulgadas QHD', 'Monitor IPS de 165 Hz', 'Monitores', 320.00),
    ('P-103', 'Teclado mecánico RGB', 'Teclado con switches rojos', 'Accesorios', 85.50)
ON CONFLICT (id) DO NOTHING;
