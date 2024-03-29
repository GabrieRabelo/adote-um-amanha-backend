-- Usuários
-- Admin
insert into usuario(id, ativo, email, nome, perfil, senha, documento, telefone, site)
values (9999, true, 'admin@mail.com', 'admin', 'ADMIN',
        '$2a$10$KDZdDpsS30B0YID4riOWoeUwfvRB5lXRB8WOoNFiO/nidARaU4haC', '99999999999', '5112123213',
        'admin.com.br');

-- Casas
insert into usuario(id, ativo, email, nome, perfil, senha, documento, telefone, site, endereco_estado,
                    endereco_cidade, endereco_bairro, endereco_CEP, endereco_rua, endereco_numero)
values (9998, true, 'lar_esperanca@email.com', 'Lar Esperança', 'CASA',
        '$2a$10$KDZdDpsS30B0YID4riOWoeUwfvRB5lXRB8WOoNFiO/nidARaU4haC', '92.965.417/0002-96', '5135174877',
        'www.laresperanca.com', 'RS', 'Porto Alegre', 'Mario Quintana', '91260-370', 'R. Deodoro', 250);

insert into usuario(id, ativo, email, nome, perfil, senha, documento, telefone, site, endereco_estado,
                    endereco_cidade, endereco_bairro, endereco_CEP, endereco_rua, endereco_numero)
values (9997, true, 'cmjp@casadomenino.org.br', 'Casa do Menino Jesus', 'CASA',
        '$2a$10$KDZdDpsS30B0YID4riOWoeUwfvRB5lXRB8WOoNFiO/nidARaU4haC', '92.965.417/0002-97', '5135174877',
        'www.casadomenino.org.br', 'RS', 'Porto Alegre', 'Partenon', '91530-350', 'R. Nelsom Zang', 420);

---- Doador
insert into usuario(id, ativo, email, nome, perfil, senha, documento, telefone, endereco_estado,
                    endereco_cidade, endereco_bairro, endereco_CEP, endereco_rua, endereco_numero)
values (9995, true, 'rabelo@gmail.com', 'Gabriel', 'DOADOR',
        '$2a$10$KDZdDpsS30B0YID4riOWoeUwfvRB5lXRB8WOoNFiO/nidARaU4haC', '123.456.789-12', '102',
        'RS', 'Porto Alegre', 'Santana', '90620-110', 'R. São Manoel', 1909);

insert into usuario(id, ativo, email, nome, perfil, senha, documento, telefone, endereco_estado,
                    endereco_cidade, endereco_bairro, endereco_CEP, endereco_rua, endereco_numero)
values (9994, true, 'vini@gmail.com', 'Vinicius', 'DOADOR',
        '$2a$10$KDZdDpsS30B0YID4riOWoeUwfvRB5lXRB8WOoNFiO/nidARaU4haC', '123.456.789-13', '102',
        'RS', 'Porto Alegre', 'Santana', '90620-110', 'R. São Manoel', 1909);

-- Pedidos
-- Necessidades
insert into pedido(id, tipo_pedido, categoria, subcategoria, status, assunto, descricao, data_hora, usuario_id,
                   url_video)
values (9999, 'NECESSIDADE', 'BEM', 'SAUDE', 'PENDENTE', 'Leite em pó desnatado',
        'A.S. precisa de aparelho auditivo pois perdeu a audição quando criança', '2022-04-08 04:05:06', 9997,
        'https://www.youtube.com/watch?v=mV4Mhza_oYw');

insert into pedido(id, tipo_pedido, categoria, subcategoria, status, assunto, descricao, data_hora, usuario_id,
                   url_video)
values (9998, 'NECESSIDADE', 'SERVICO', 'SAUDE', 'FINALIZADA', 'Consulta psicológica',
        'A. C. precisa de sessões terapeuticas para superar traumas', '2022-04-01 04:05:06', 9998,
        'https://www.youtube.com/watch?v=0gBgyxXJ1GY');

insert into pedido(id, tipo_pedido, categoria, subcategoria, status, assunto, descricao, data_hora, usuario_id,
                   url_video)
values (9997, 'NECESSIDADE', 'SERVICO', 'SAUDE', 'PENDENTE', 'Consulta Nutricional',
        'A. J. está com problemas ao ingerir certos alimentos, por isso precisa de atendimento nutricional',
        '2022-04-01 04:05:06', 9998, 'https://www.youtube.com/watch?v=0gBgyxXJ1GY');

--Doacoes
insert into pedido(id, tipo_pedido, categoria, subcategoria, status, assunto, descricao, data_hora, usuario_id)
values (9995, 'DOACAO', 'SERVICO', 'SAUDE', 'PENDENTE', 'Consulta Nutricional',
        'A. J. está com problemas ao ingerir certos alimentos, por isso precisa de atendimento nutricional',
        '2022-04-01 04:05:06', 9995);

insert into pedido(id, tipo_pedido, categoria, subcategoria, status, assunto, descricao, data_hora, usuario_id)
values (9994, 'DOACAO', 'SERVICO', 'SAUDE', 'PENDENTE', 'Consulta Nutricional',
        'A. J. está com problemas ao ingerir certos alimentos, por isso precisa de atendimento nutricional',
        '2022-04-01 04:05:06', 9994);
