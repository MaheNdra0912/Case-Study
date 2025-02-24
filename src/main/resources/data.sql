-- Delete existing data
DELETE FROM intakes;
DELETE FROM schedules;
DELETE FROM medications;

-- Insert into medications table
INSERT INTO medications (id, name, dosage_form, strength) VALUES
(1, 'Paracetamol', 'Tablet', '500mg'),
(2, 'Ibuprofen', 'Capsule', '200mg'),
(3, 'Metformin', 'Tablet', '850mg'),
(4, 'Amoxicillin', 'Capsule', '500mg');

-- Insert into schedules table
INSERT INTO schedules (id, medication_id, user_id, scheduled_time, days_of_week) VALUES
(1, 1, 101, '08:00:00', '{1,2,3,4,5}'),
(2, 2, 102, '12:00:00', '{2,4,6}'),
(3, 3, 103, '18:30:00', '{1,3,5,7}'),
(4, 4, 104, '22:00:00', '{1,3,5}');

-- Insert into intakes table (Spanning multiple days for SMA calculation)
INSERT INTO intakes (id, schedule_id, status, scheduled_for, taken_at) VALUES
-- Day 1 (7 days ago)
(1, 1, 'TAKEN', '2025-02-17 08:00:00', '2025-02-17 08:05:00'),
(2, 2, 'MISSED', '2025-02-17 12:00:00', NULL),
(3, 3, 'TAKEN', '2025-02-17 18:30:00', '2025-02-17 18:35:00'),
(4, 4, 'TAKEN', '2025-02-17 22:00:00', '2025-02-17 22:02:00'),

-- Day 2 (6 days ago)
(5, 1, 'MISSED', '2025-02-18 08:00:00', NULL),
(6, 2, 'TAKEN', '2025-02-18 12:00:00', '2025-02-18 12:10:00'),
(7, 3, 'TAKEN', '2025-02-18 18:30:00', '2025-02-18 18:32:00'),
(8, 4, 'MISSED', '2025-02-18 22:00:00', NULL),

-- Day 3 (5 days ago)
(9, 1, 'TAKEN', '2025-02-19 08:00:00', '2025-02-19 08:03:00'),
(10, 2, 'MISSED', '2025-02-19 12:00:00', NULL),
(11, 3, 'TAKEN', '2025-02-19 18:30:00', '2025-02-19 18:33:00'),
(12, 4, 'MISSED', '2025-02-19 22:00:00', NULL),

-- Day 4 (4 days ago)
(13, 1, 'TAKEN', '2025-02-20 08:00:00', '2025-02-20 08:04:00'),
(14, 2, 'TAKEN', '2025-02-20 12:00:00', '2025-02-20 12:08:00'),
(15, 3, 'MISSED', '2025-02-20 18:30:00', NULL),
(16, 4, 'TAKEN', '2025-02-20 22:00:00', '2025-02-20 22:01:00'),

-- Day 5 (3 days ago)
(17, 1, 'TAKEN', '2025-02-21 08:00:00', '2025-02-21 08:02:00'),
(18, 2, 'MISSED', '2025-02-21 12:00:00', NULL),
(19, 3, 'TAKEN', '2025-02-21 18:30:00', '2025-02-21 18:30:00'),
(20, 4, 'MISSED', '2025-02-21 22:00:00', NULL),

-- Day 6 (2 days ago)
(21, 1, 'MISSED', '2025-02-22 08:00:00', NULL),
(22, 2, 'MISSED', '2025-02-22 12:00:00', NULL),
(23, 3, 'TAKEN', '2025-02-22 18:30:00', '2025-02-22 18:33:00'),
(24, 4, 'MISSED', '2025-02-22 22:00:00', NULL),

-- Day 7 (1 day ago)
(25, 1, 'TAKEN', '2025-02-23 08:00:00', '2025-02-23 08:05:00'),
(26, 2, 'MISSED', '2025-02-23 12:00:00', NULL),
(27, 3, 'TAKEN', '2025-02-23 18:30:00', '2025-02-23 18:35:00'),
(28, 4, 'TAKEN', '2025-02-23 22:00:00', '2025-02-23 22:02:00');