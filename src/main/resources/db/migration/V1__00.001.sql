-- Tabela de tipos de grupo (Entrada ou Saída)
CREATE TABLE enum_tipo_grupo (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

-- Inserção dos tipos
INSERT INTO enum_tipo_grupo (nome) VALUES 
    ('ENTRADA'), 
    ('SAIDA');


-- Tabela de frequências de entradas
CREATE TABLE enum_frequencia (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

-- Inserção dos dados
INSERT INTO enum_frequencia (nome) VALUES
('UNICA'),
('QUINZENAL'),
('MENSAL');


-- Tabela de classificações de saídas
CREATE TABLE enum_classificacao (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);

-- Inserção dos dados
INSERT INTO enum_classificacao (nome) VALUES  
('NECESSARIA'), 
('OBRIGATORIA'), 
('IMPULSO'), 
('SUPÉRFLUA');


-- Tabela de grupo de categorias
CREATE TABLE grupo_categoria (
    id BIGSERIAL PRIMARY KEY,
    descricao VARCHAR(100) NOT NULL,
    id_enum_tipo_grupo BIGINT NOT NULL,
    ativo BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_grupo_categoria_tipo FOREIGN KEY (id_enum_tipo_grupo)
        REFERENCES enum_tipo_grupo(id)
);
-- Índice para busca por tipo
CREATE INDEX idx_grupo_categoria_tipo ON grupo_categoria(id_enum_tipo_grupo);

-- Tabela de entradas financeiras
CREATE TABLE entrada (
    id BIGSERIAL PRIMARY KEY,
    idEmpresa BIGINT NOT NULL,
    id_grupo_categoria BIGINT NOT NULL,
    descricao VARCHAR(150),
    valor DECIMAL(10,2) NOT NULL,
    data_prevista DATE NOT NULL,
    data_recebimento DATE,
    id_enum_frequencia BIGINT NOT NULL,
    observacao TEXT,
    CONSTRAINT fk_entrada_grupo_categoria FOREIGN KEY (id_grupo_categoria)
        REFERENCES grupo_categoria(id),
    CONSTRAINT fk_entrada_frequencia FOREIGN KEY (id_enum_frequencia)
        REFERENCES enum_frequencia(id)
);
-- Índices para desempenho de consultas
CREATE INDEX idx_entrada_empresa ON entrada(idEmpresa);
CREATE INDEX idx_entrada_data_prevista ON entrada(data_prevista);
CREATE INDEX idx_entrada_grupo ON entrada(id_grupo_categoria);


-- Tabela de saídas financeiras
CREATE TABLE saida (
    id BIGSERIAL PRIMARY KEY,
    idEmpresa BIGINT NOT NULL,
    id_grupo_categoria BIGINT NOT NULL,
    descricao VARCHAR(150) NOT NULL,
    valor_total DECIMAL(10,2),
    valor_parcela DECIMAL(10,2),
    quantidade_parcelas INT,
    data_compra DATE NOT NULL,
    data_cadastro DATE NOT NULL,
    data_pagamento DATE,
    id_enum_classificacao BIGINT,
    observacao TEXT,
    CONSTRAINT fk_saida_grupo_categoria FOREIGN KEY (id_grupo_categoria)
        REFERENCES grupo_categoria(id),
    CONSTRAINT fk_saida_classificacao FOREIGN KEY (id_enum_classificacao)
        REFERENCES enum_classificacao(id)
);
-- Índices para melhorar desempenho nas consultas
CREATE INDEX idx_saida_empresa ON saida(idEmpresa);
CREATE INDEX idx_saida_data_compra ON saida(data_compra);
CREATE INDEX idx_saida_grupo ON saida(id_grupo_categoria);
CREATE INDEX idx_saida_classificacao ON saida(id_enum_classificacao);

-- Tabela de parcelas associadas a uma saída
CREATE TABLE parcela_saida (
    id BIGSERIAL PRIMARY KEY,
    id_saida BIGINT NOT NULL,
    numero_parcela INT NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    data_vencimento DATE NOT NULL,
    data_pagamento DATE,
    observacao TEXT,
    CONSTRAINT fk_parcela_saida FOREIGN KEY (id_saida)
        REFERENCES saida(id)
);
-- Índices para otimizar consultas
CREATE INDEX idx_parcela_saida_id_saida ON parcela_saida(id_saida);
CREATE INDEX idx_parcela_saida_vencimento ON parcela_saida(data_vencimento);


-- Tabela de pagamentos extras
CREATE TABLE pagamento_extra (
    id BIGSERIAL PRIMARY KEY,
    idEmpresa BIGINT NOT NULL,
    descricao VARCHAR(150) NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    data_pagamento DATE NOT NULL,
    id_grupo_categoria BIGINT,
    id_enum_classificacao BIGINT,
    observacao TEXT,
    CONSTRAINT fk_pagamento_extra_grupo FOREIGN KEY (id_grupo_categoria)
        REFERENCES grupo_categoria(id),
    CONSTRAINT fk_pagamento_extra_classificacao FOREIGN KEY (id_enum_classificacao)
        REFERENCES enum_classificacao(id)
);
-- Índices para otimização de buscas
CREATE INDEX idx_pagamento_extra_empresa ON pagamento_extra(idEmpresa);
CREATE INDEX idx_pagamento_extra_classificacao ON pagamento_extra(id_enum_classificacao);
