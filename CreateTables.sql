-- Tabela Usuario
CREATE TABLE Usuario (
    id INT PRIMARY KEY,
    nome VARCHAR2(250) NOT NULL,
    email VARCHAR2(250) NOT NULL,
    senha VARCHAR2(10) NOT NULL,
    dataCriacao DATE NOT NULL
);

CREATE SEQUENCE SEQ_USUARIO_ID
START WITH 1
INCREMENT BY 1
NOCACHE;

-- Tabela TipoMoeda
CREATE TABLE TipoMoeda (
    id INT PRIMARY KEY,
    nome VARCHAR2(50) NOT NULL,
    codigo VARCHAR2(10) NOT NULL,
    precoAtual DECIMAL(30, 8) NOT NULL
);

CREATE SEQUENCE SEQ_TIPOMOEDA_ID
START WITH 1
INCREMENT BY 1
NOCACHE;

-- Tabela Conta
CREATE TABLE Conta (
    id INT PRIMARY KEY,
    id_Usuario INT NOT NULL,
    id_TipoMoeda INT NOT NULL,
    dataCriacao DATE NOT NULL,
    numeroConta VARCHAR2(10) NOT NULL,
    saldo DECIMAL(30, 8) NOT NULL,
    FOREIGN KEY (id_Usuario) REFERENCES Usuario(id),
    FOREIGN KEY (id_TipoMoeda) REFERENCES TipoMoeda(id)
);

CREATE SEQUENCE SEQ_CONTA_ID
START WITH 1
INCREMENT BY 1
NOCACHE;

-- Tabela TipoTransacao
CREATE TABLE TipoTransacao (
    id INT PRIMARY KEY,
    descricao VARCHAR2(50) NOT NULL
);

CREATE SEQUENCE SEQ_TIPOTRANSACAO_ID
START WITH 1
INCREMENT BY 1
NOCACHE;

-- Tabela Transferencia
CREATE TABLE Transferencia (
    id INT PRIMARY KEY,
    id_ContaOrigem INT NOT NULL,
    id_ContaDestino INT NOT NULL,
    FOREIGN KEY (id_ContaOrigem) REFERENCES Conta(id),
    FOREIGN KEY (id_ContaDestino) REFERENCES Conta(id)
);

CREATE SEQUENCE SEQ_TRANSFERENCIA_ID
START WITH 1
INCREMENT BY 1
NOCACHE;

-- Tabela Transacao
CREATE TABLE Transacao (
    id INT PRIMARY KEY,
    id_Conta INT NOT NULL,
    id_TipoTransacao INT NOT NULL,
    id_Transferencia INT,
    data TIMESTAMP NOT NULL,
    montante DECIMAL(30, 8) NOT NULL,
    preco DECIMAL(30, 8),
    observacao VARCHAR2(50),
    FOREIGN KEY (id_Conta) REFERENCES Conta(id),
    FOREIGN KEY (id_TipoTransacao) REFERENCES TipoTransacao(id),
    FOREIGN KEY (id_Transferencia) REFERENCES Transferencia(id)
);

CREATE SEQUENCE SEQ_TRANSACAO_ID
START WITH 1
INCREMENT BY 1
NOCACHE;
