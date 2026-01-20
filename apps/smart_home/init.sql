\c smarthome

CREATE TABLE IF NOT EXISTS sensors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    location VARCHAR(100) NOT NULL,
    value FLOAT DEFAULT 0,
    unit VARCHAR(20),
    status VARCHAR(20) NOT NULL DEFAULT 'inactive',
    last_updated TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for common queries
CREATE INDEX IF NOT EXISTS idx_sensors_type ON sensors(type);
CREATE INDEX IF NOT EXISTS idx_sensors_location ON sensors(location);
CREATE INDEX IF NOT EXISTS idx_sensors_status ON sensors(status);

INSERT INTO sensors (name, type, location, value, unit, status) VALUES
    ('Температура в гостиной', 'temperature', 'Гостиная', 22.5, '°C', 'active'),
    ('Температура на кухне', 'temperature', 'Кухня', 21, '°C', 'active'),
    ('Температура в спальне', 'temperature', 'Спальня', 23, '°C', 'active'),
    ('Температура в гараже', 'temperature', 'Гараж', 10, '°C', 'active')
ON CONFLICT DO NOTHING;

SELECT 'Database initialized successfully!' as message;

INSERT INTO sensors (name, type, location, value, unit, status) VALUES
    ('Температура в гостиной', 'temperature', 'Гостиная', 22.5, '°C', 'active'),
    ('Температура на кухне', 'temperature', 'Кухня', 21, '°C', 'active'),
    ('Температура в спальне', 'temperature', 'Спальня', 23, '°C', 'active'),
    ('Температура в гараже', 'temperature', 'Гараж', 10, '°C', 'active')
ON CONFLICT DO NOTHING;

SELECT 'Database initialized successfully!' as message;