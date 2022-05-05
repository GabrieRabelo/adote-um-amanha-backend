-- Usuários
insert into usuario(id, ativo, email, nome, perfil, senha, documento, telefone, site, endereco_estado,
                    endereco_cidade, endereco_bairro, endereco_CEP, endereco_rua, endereco_numero)
values (1, true, 'doador@email.com', 'Joe testes', 'DOADOR',
        '$2a$10$KDZdDpsS30B0YID4riOWoeUwfvRB5lXRB8WOoNFiO/nidARaU4haC', '000.000.000-00', '5135174877',
        '','RS', 'Porto Alegre', 'Mario Quintana', '91260-370', 'R. Deodoro', 250);

---- Casas
insert into usuario(id, ativo, email, nome, perfil, senha, documento, telefone, site, endereco_estado,
                    endereco_cidade, endereco_bairro, endereco_CEP, endereco_rua, endereco_numero)
values (9999, true, 'lar_esperanca@email.com', 'Lar Esperança', 'CASA',
        '$2a$10$KDZdDpsS30B0YID4riOWoeUwfvRB5lXRB8WOoNFiO/nidARaU4haC', '92.965.417/0002-98', '5135174877',
        'www.laresperanca.com','RS', 'Porto Alegre', 'Mario Quintana', '91260-370', 'R. Deodoro', 250);
insert into usuario(id, ativo, email, nome, perfil, senha, documento, telefone, site, endereco_estado,
                    endereco_cidade, endereco_bairro, endereco_CEP, endereco_rua, endereco_numero)
values (9998, true, 'cmjp@casadomenino.org.br', 'Casa do Menino Jesus', 'CASA',
        '$2a$10$KDZdDpsS30B0YID4riOWoeUwfvRB5lXRB8WOoNFiO/nidARaU4haC', '92.965.417/0002-98', '5135174877',
        'www.casadomenino.org.br','RS', 'Porto Alegre', 'Partenon', '91530-350', 'R. Nelsom Zang', 420);
insert into usuario(id, ativo, email, nome, perfil, senha, documento, telefone, site, endereco_estado,
                    endereco_cidade, endereco_bairro, endereco_CEP, endereco_rua, endereco_numero)
values (9997, true, 'lardesaojose@hotmail.com', 'Lar de São José', 'CASA',
        '$2a$10$KDZdDpsS30B0YID4riOWoeUwfvRB5lXRB8WOoNFiO/nidARaU4haC', '92.965.417/0002-98', '5135174877',
        'www.lardesaojosepoa.com.br','RS', 'Porto Alegre', 'Santana', '90620-110', 'R. São Manoel', 1909);

---- Admin
insert into usuario(id, ativo, email, nome, perfil, senha, documento, telefone, site)
values (9996, true, 'admin@gmail.com', 'Cinara', 'ADMIN',
        '$2a$10$KDZdDpsS30B0YID4riOWoeUwfvRB5lXRB8WOoNFiO/nidARaU4haC', '99999999999', '5112123213',
        'admin.com.br');

-- Pedidos
---- Necessidades
insert into pedido(id, tipo_pedido, categoria, subcategoria, status, assunto, descricao, data_hora, usuario_id, url_video)
values (9999, 'NECESSIDADE', 'BEM', 'ALIMENTACAO', 'PENDENTE', 'Leite em pó desnatado',
        'J.P. tem tolerancia a lactose e precisa de um tipo específico de leite', '2022-04-08 04:05:06', 9999, 'https://www.youtube.com/watch?v=0gBgyxXJ1GY');

insert into pedido(id, tipo_pedido, categoria, subcategoria, status, assunto, descricao, data_hora, usuario_id, url_video)
values (9998, 'NECESSIDADE', 'SERVIÇO', 'SAUDE', 'FINALIZADA', 'Consulta psicológica',
        'A. C. precisa de sessões terapeuticas para superar traumas', '2022-04-01 04:05:06', 9999, 'https://www.youtube.com/watch?v=0gBgyxXJ1GY');

insert into pedido(id, tipo_pedido, categoria, subcategoria, status, assunto, descricao, data_hora, usuario_id, url_video)
values (9997, 'NECESSIDADE', 'SERVIÇO', 'SAUDE', 'PENDENTE', 'Consulta Nutricional',
        'A. J. está com problemas ao ingerir certos alimentos, por isso precisa de atendimento nutricional',
        '2022-04-01 04:05:06', 9999, 'https://www.youtube.com/watch?v=0gBgyxXJ1GY');

--- Doacoes
insert into pedido(id, tipo_pedido, categoria, subcategoria, status, assunto, descricao, data_hora, usuario_id, url_video)
values (50, 'DOACAO', 'BEM', 'ALIMENTACAO', 'PENDENTE', 'Leite em pó desnatado',
        'J.P. tem tolerancia a lactose e precisa de um tipo específico de leite', '2022-04-08 04:05:06', 1, 'https://www.youtube.com/watch?v=0gBgyxXJ1GY');
