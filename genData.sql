-- Create guests table if it doesn't exist
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='guests' AND xtype='U')
CREATE TABLE guests (
    guest_id INT IDENTITY(1,1) PRIMARY KEY,
    name NVARCHAR(255) NOT NULL,
    gender NVARCHAR(50) NOT NULL,
    room_number INT,
    phone_number NVARCHAR(50) NOT NULL,
    email NVARCHAR(255),
    social_id BIGINT NOT NULL,
    status NVARCHAR(50) NOT NULL
);

-- Create bookings table if it doesn't exist
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='bookings' AND xtype='U')
CREATE TABLE bookings (
    booking_id BIGINT IDENTITY(1,1) PRIMARY KEY,
    guest_id INT NOT NULL,
    check_in_date DATE,
    check_out_date DATE,
    FOREIGN KEY (guest_id) REFERENCES guests(guest_id)
);

-- Create parcel table if it doesn't exist
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name='parcel' AND xtype='U')
CREATE TABLE parcel (
    parcel_id INT IDENTITY(1,1) PRIMARY KEY,
    guest_id INT NOT NULL,
    received_date DATE NOT NULL,
    pick_up_date DATE,
    status NVARCHAR(50) NOT NULL,
    description NVARCHAR(255),
    FOREIGN KEY (guest_id) REFERENCES guests(guest_id)
);

-- Clear existing data if needed
DELETE FROM bookings;
DELETE FROM parcel;
DELETE FROM guests;

-- Reset identity columns
DBCC CHECKIDENT ('bookings', RESEED, 0);
DBCC CHECKIDENT ('parcel', RESEED, 0);
DBCC CHECKIDENT ('guests', RESEED, 0);

-- Insert 50 guests
INSERT INTO guests (name, gender, room_number, phone_number, email, social_id, status)
VALUES 
('John Smith', 'Male', 101, '555-123-4567', 'john.smith@email.com', 1234567890, 'I'),
('Emma Johnson', 'Female', 102, '555-234-5678', 'emma.johnson@email.com', 2345678901, 'I'),
('Michael Davis', 'Male', 103, '555-345-6789', 'michael.davis@email.com', 3456789012, 'I'),
('Sarah Wilson', 'Female', 104, '555-456-7890', 'sarah.wilson@email.com', 4567890123, 'I'),
('David Brown', 'Male', 105, '555-567-8901', 'david.brown@email.com', 5678901234, 'I'),
('Jennifer Miller', 'Female', 106, '555-678-9012', 'jennifer.miller@email.com', 6789012345, 'I'),
('Robert Taylor', 'Male', 107, '555-789-0123', 'robert.taylor@email.com', 7890123456, 'I'),
('Lisa Anderson', 'Female', 108, '555-890-1234', 'lisa.anderson@email.com', 8901234567, 'I'),
('James Thomas', 'Male', 109, '555-901-2345', 'james.thomas@email.com', 9012345678, 'I'),
('Karen Jackson', 'Female', 110, '555-012-3456', 'karen.jackson@email.com', 1234567890, 'I'),
('Richard White', 'Male', 201, '555-123-7890', 'richard.white@email.com', 2345678901, 'I'),
('Susan Harris', 'Female', 202, '555-234-8901', 'susan.harris@email.com', 3456789012, 'I'),
('Thomas Martin', 'Male', 203, '555-345-9012', 'thomas.martin@email.com', 4567890123, 'I'),
('Jessica Thompson', 'Female', 204, '555-456-0123', 'jessica.thompson@email.com', 5678901234, 'I'),
('Daniel Garcia', 'Male', 205, '555-567-1234', 'daniel.garcia@email.com', 6789012345, 'I'),
('Nancy Martinez', 'Female', 206, '555-678-2345', 'nancy.martinez@email.com', 7890123456, 'O'),
('Paul Robinson', 'Male', 207, '555-789-3456', 'paul.robinson@email.com', 8901234567, 'O'),
('Margaret Clark', 'Female', 208, '555-890-4567', 'margaret.clark@email.com', 9012345678, 'O'),
('Mark Rodriguez', 'Male', 209, '555-901-5678', 'mark.rodriguez@email.com', 1234567890, 'O'),
('Sandra Lewis', 'Female', 210, '555-012-6789', 'sandra.lewis@email.com', 2345678901, 'O'),
('Joseph Lee', 'Male', 301, '555-123-8901', 'joseph.lee@email.com', 3456789012, 'O'),
('Patricia Walker', 'Female', 302, '555-234-9012', 'patricia.walker@email.com', 4567890123, 'O'),
('Kevin Hall', 'Male', 303, '555-345-0123', 'kevin.hall@email.com', 5678901234, 'O'),
('Linda Allen', 'Female', 304, '555-456-1234', 'linda.allen@email.com', 6789012345, 'O'),
('Steven Young', 'Male', 305, '555-567-2345', 'steven.young@email.com', 7890123456, 'O'),
('Barbara Hernandez', 'Female', 306, '555-678-3456', 'barbara.hernandez@email.com', 8901234567, 'O'),
('Edward King', 'Male', 307, '555-789-4567', 'edward.king@email.com', 9012345678, 'O'),
('Dorothy Wright', 'Female', 308, '555-890-5678', 'dorothy.wright@email.com', 1234567890, 'O'),
('Jason Lopez', 'Male', 309, '555-901-6789', 'jason.lopez@email.com', 2345678901, 'O'),
('Michelle Hill', 'Female', 310, '555-012-7890', 'michelle.hill@email.com', 3456789012, 'O'),
('Ryan Scott', 'Male', 401, '555-123-9012', 'ryan.scott@email.com', 4567890123, 'B'),
('Carol Green', 'Female', 402, '555-234-0123', 'carol.green@email.com', 5678901234, 'B'),
('Kenneth Adams', 'Male', 403, '555-345-1234', 'kenneth.adams@email.com', 6789012345, 'B'),
('Amanda Baker', 'Female', 404, '555-456-2345', 'amanda.baker@email.com', 7890123456, 'B'),
('Gregory Nelson', 'Male', 405, '555-567-3456', 'gregory.nelson@email.com', 8901234567, 'B'),
('Melissa Mitchell', 'Female', 406, '555-678-4567', 'melissa.mitchell@email.com', 9012345678, 'B'),
('Joshua Phillips', 'Male', 407, '555-789-5678', 'joshua.phillips@email.com', 1234567890, 'B'),
('Deborah Campbell', 'Female', 408, '555-890-6789', 'deborah.campbell@email.com', 2345678901, 'B'),
('Brian Parker', 'Male', 409, '555-901-7890', 'brian.parker@email.com', 3456789012, 'B'),
('Rebecca Evans', 'Female', 410, '555-012-8901', 'rebecca.evans@email.com', 4567890123, 'B'),
('Justin Turner', 'Male', 501, '555-123-0123', 'justin.turner@email.com', 5678901234, 'B'),
('Laura Collins', 'Female', 502, '555-234-1234', 'laura.collins@email.com', 6789012345, 'B'),
('Brandon Stewart', 'Male', 503, '555-345-2345', 'brandon.stewart@email.com', 7890123456, 'B'),
('Catherine Morris', 'Female', 504, '555-456-3456', 'catherine.morris@email.com', 8901234567, 'B'),
('Jonathan Rogers', 'Male', 505, '555-567-4567', 'jonathan.rogers@email.com', 9012345678, 'B'),
('Victoria Reed', 'Female', 506, '555-678-5678', 'victoria.reed@email.com', 1234567890, 'B'),
('Tyler Cox', 'Male', 507, '555-789-6789', 'tyler.cox@email.com', 2345678901, 'B'),
('Olivia Sanchez', 'Female', 508, '555-890-7890', 'olivia.sanchez@email.com', 3456789012, 'B'),
('Jeremy Price', 'Male', 509, '555-901-8901', 'jeremy.price@email.com', 4567890123, 'B'),
('Stephanie Hughes', 'Female', 510, '555-012-9012', 'stephanie.hughes@email.com', 5678901234, 'B');

-- Insert 50 bookings
INSERT INTO bookings (guest_id, check_in_date, check_out_date)
VALUES
(1, '2025-04-10', '2025-04-15'),
(2, '2025-04-11', '2025-04-18'),
(3, '2025-04-12', '2025-04-16'),
(4, '2025-04-13', '2025-04-20'),
(5, '2025-04-14', '2025-04-21'),
(6, '2025-04-15', '2025-04-22'),
(7, '2025-04-16', '2025-04-19'),
(8, '2025-04-17', '2025-04-25'),
(9, '2025-04-18', '2025-04-23'),
(10, '2025-04-19', '2025-04-24'),
(11, '2025-04-10', '2025-04-17'),
(12, '2025-04-11', '2025-04-15'),
(13, '2025-04-12', '2025-04-19'),
(14, '2025-04-13', '2025-04-18'),
(15, '2025-04-14', '2025-04-20'),
(16, '2025-03-15', '2025-03-22'),
(17, '2025-03-16', '2025-03-23'),
(18, '2025-03-17', '2025-03-24'),
(19, '2025-03-18', '2025-03-25'),
(20, '2025-03-19', '2025-03-26'),
(21, '2025-03-10', '2025-03-15'),
(22, '2025-03-11', '2025-03-16'),
(23, '2025-03-12', '2025-03-17'),
(24, '2025-03-13', '2025-03-18'),
(25, '2025-03-14', '2025-03-19'),
(26, '2025-03-15', '2025-03-20'),
(27, '2025-03-16', '2025-03-21'),
(28, '2025-03-17', '2025-03-22'),
(29, '2025-03-18', '2025-03-23'),
(30, '2025-03-19', '2025-03-24'),
(31, '2025-05-01', '2025-05-06'),
(32, '2025-05-02', '2025-05-07'),
(33, '2025-05-03', '2025-05-08'),
(34, '2025-05-04', '2025-05-09'),
(35, '2025-05-05', '2025-05-10'),
(36, '2025-05-06', '2025-05-11'),
(37, '2025-05-07', '2025-05-12'),
(38, '2025-05-08', '2025-05-13'),
(39, '2025-05-09', '2025-05-14'),
(40, '2025-05-10', '2025-05-15'),
(41, '2025-05-15', '2025-05-20'),
(42, '2025-05-16', '2025-05-21'),
(43, '2025-05-17', '2025-05-22'),
(44, '2025-05-18', '2025-05-23'),
(45, '2025-05-19', '2025-05-24'),
(46, '2025-05-20', '2025-05-25'),
(47, '2025-05-21', '2025-05-26'),
(48, '2025-05-22', '2025-05-27'),
(49, '2025-05-23', '2025-05-28'),
(50, '2025-05-24', '2025-05-29');

-- Insert 50 parcels
INSERT INTO parcel (guest_id, received_date, pick_up_date, status, description)
VALUES
(1, '2025-04-11', '2025-04-12', 'COMPLETED', 'Small package from Amazon'),
(2, '2025-04-12', '2025-04-13', 'COMPLETED', 'Box from family'),
(3, '2025-04-13', '2025-04-14', 'COMPLETED', 'Express envelope'),
(4, '2025-04-14', '2025-04-15', 'COMPLETED', 'Large box from online retailer'),
(5, '2025-04-15', '2025-04-16', 'COMPLETED', 'Document from business associate'),
(6, '2025-04-16', '2025-04-17', 'COMPLETED', 'Package from overseas'),
(7, '2025-04-17', '2025-04-18', 'COMPLETED', 'Gift box'),
(8, '2025-04-18', '2025-04-19', 'COMPLETED', 'Letter from attorney'),
(9, '2025-04-19', '2025-04-20', 'COMPLETED', 'Registered mail'),
(10, '2025-04-20', NULL, 'PENDING', 'Fragile package marked "Handle with Care"'),
(11, '2025-04-11', '2025-04-12', 'COMPLETED', 'Small electronics package'),
(12, '2025-04-12', '2025-04-13', 'COMPLETED', 'Clothing delivery'),
(13, '2025-04-13', '2025-04-14', 'COMPLETED', 'Book shipment'),
(14, '2025-04-14', '2025-04-15', 'COMPLETED', 'Medication package'),
(15, '2025-04-15', NULL, 'PENDING', 'Important documents'),
(16, '2025-03-17', '2025-03-18', 'COMPLETED', 'Birthday gift'),
(17, '2025-03-18', '2025-03-19', 'COMPLETED', 'Food delivery'),
(18, '2025-03-19', '2025-03-20', 'COMPLETED', 'Personal items from home'),
(19, '2025-03-20', '2025-03-21', 'COMPLETED', 'Business materials'),
(20, '2025-03-21', '2025-03-22', 'COMPLETED', 'Tech accessories'),
(21, '2025-03-12', '2025-03-13', 'COMPLETED', 'Small package from eBay'),
(22, '2025-03-13', '2025-03-14', 'COMPLETED', 'Package from department store'),
(23, '2025-03-14', '2025-03-15', 'COMPLETED', 'Envelope with photos'),
(24, '2025-03-15', '2025-03-16', 'COMPLETED', 'Work-related package'),
(25, '2025-03-16', '2025-03-17', 'COMPLETED', 'Skincare products'),
(26, '2025-03-17', '2025-03-18', 'COMPLETED', 'Electronic device'),
(27, '2025-03-18', '2025-03-19', 'COMPLETED', 'Legal documents'),
(28, '2025-03-19', '2025-03-20', 'COMPLETED', 'Tax documents'),
(29, '2025-03-20', '2025-03-21', 'COMPLETED', 'Travel documents'),
(30, '2025-03-21', NULL, 'PENDING', 'Prescription medication'),
(31, '2025-04-20', NULL, 'PENDING', 'Laptop delivery'),
(32, '2025-04-19', NULL, 'PENDING', 'Gift from relative'),
(33, '2025-04-18', NULL, 'PENDING', 'Business package'),
(34, '2025-04-17', NULL, 'PENDING', 'Personal correspondence'),
(35, '2025-04-16', NULL, 'PENDING', 'Event tickets'),
(36, '2025-04-15', NULL, 'PENDING', 'Fashion accessories'),
(37, '2025-04-14', NULL, 'PENDING', 'Sporting equipment'),
(38, '2025-04-13', NULL, 'PENDING', 'Health supplements'),
(39, '2025-04-12', NULL, 'PENDING', 'Pet supplies'),
(40, '2025-04-11', NULL, 'PENDING', 'Fragile glassware'),
(41, '2025-04-20', NULL, 'PENDING', 'Express international package'),
(42, '2025-04-19', NULL, 'PENDING', 'Overnight delivery'),
(43, '2025-04-18', NULL, 'PENDING', 'Legal certification'),
(44, '2025-04-17', NULL, 'PENDING', 'Important business documents'),
(45, '2025-04-16', NULL, 'PENDING', 'Special delivery'),
(46, '2025-04-15', NULL, 'PENDING', 'Time-sensitive documents'),
(47, '2025-04-14', NULL, 'PENDING', 'Certificate package'),
(48, '2025-04-13', NULL, 'PENDING', 'Confidential documents'),
(49, '2025-04-12', NULL, 'PENDING', 'Rush delivery package'),
(50, '2025-04-11', NULL, 'PENDING', 'Urgent supplies');