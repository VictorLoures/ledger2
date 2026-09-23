CREATE TABLE pagamentos (
    id UUID PRIMARY KEY,
    conta_id UUID NOT NULL REFERENCES contas(id) ON DELETE CASCADE,
    mes_referencia DATE NOT NULL,
    data_pagamento DATE NOT NULL,
    valor_pago NUMERIC(12, 2) NOT NULL,
    observacoes TEXT,
    criado_em TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT uk_pagamentos_conta_mes UNIQUE (conta_id, mes_referencia)
);

CREATE INDEX idx_pagamentos_conta_id ON pagamentos (conta_id);
