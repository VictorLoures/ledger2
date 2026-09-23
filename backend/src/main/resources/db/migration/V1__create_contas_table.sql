CREATE TABLE contas (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    valor NUMERIC(12, 2) NOT NULL,
    dia_vencimento INTEGER NOT NULL CHECK (dia_vencimento BETWEEN 1 AND 31),
    categoria VARCHAR(100),
    recorrente BOOLEAN NOT NULL DEFAULT TRUE,
    data_vencimento DATE,
    observacoes TEXT,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    atualizado_em TIMESTAMPTZ NOT NULL DEFAULT now()
);
