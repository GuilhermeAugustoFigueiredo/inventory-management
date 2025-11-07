-- script teste sql
INSERT INTO usuario (nome, email, cpf, cargo, salario, ativo) VALUES
('Ana Silva', 'ana.silva@email.com', '11122233344', 'Gerente', 7500.00, true),
('Bruno Costa', 'bruno.costa@email.com', '22233344455', 'Gerente', 7500.00, true),
('Carla Mendes', 'carla.mendes@email.com', '33344455566', 'Estoquista', 2800.00, true),
('Daniel Moreira', 'daniel.moreira@email.com', '44455566677', 'Vendedor', 3200.00, false);

INSERT INTO produto (categoria, marca, nome, preco_unitario, preco_final) VALUES
('Hardware', 'Intel', 'Processador Core i7', 1200.00, 1850.50),
('Hardware', 'Kingston', 'Memória RAM 16GB DDR4', 350.00, 599.90),
('Periféricos', 'Logitech', 'Mouse Gamer G502', 280.00, 450.00),
('Periféricos', 'Redragon', 'Teclado Mecânico Kumara', 190.00, 319.99);