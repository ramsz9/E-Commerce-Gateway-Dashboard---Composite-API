INSERT INTO inventory (product_id, available_units, warehouse_location, delivery_days) VALUES
    ('P-101', 15, 'Almacén Chihuahua, pasillo A-3', 2),
    ('P-102', 0, 'Almacén Monterrey, pasillo B-1', 7),
    ('P-103', 40, 'Almacén Chihuahua, pasillo C-5', 2)
ON CONFLICT (product_id) DO NOTHING;
