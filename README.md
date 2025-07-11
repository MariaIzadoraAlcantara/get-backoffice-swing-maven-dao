# 💼 JavaTech Equipamentos - Sistema de Gestão com Backoffice

Sistema de gestão de equipamentos da empresa fictícia JavaTech, desenvolvido em Java com interface gráfica Swing, persistência via MySQL e arquitetura MVC. Esta versão inclui **backoffice com autenticação segura e controle de acesso por perfil**.

## 🚀 Funcionalidades

- Autenticação com senha criptografada
- Interface adaptada por tipo de utilizador (Admin, Técnico, Formador)
- Registo, edição e remoção de equipamentos
- Atribuição de responsável aos equipamentos
- Filtros por estado e sala
- Backoffice com menus visíveis conforme o perfil do utilizador

## 👥 Perfis de Utilizador

| Perfil       | Permissões                                                                 |
|--------------|-----------------------------------------------------------------------------|
| Administrador| Total acesso (CRUD completo + atribuição de responsável + menu completo)   |
| Técnico      | Inserção e edição de estado, visualização e filtros                        |
| Formador     | Apenas leitura e filtros                                                    |


## 🔐 Segurança

- Senhas armazenadas no banco em formato **criptografado**
- Validação de acesso com base no **perfil do utilizador**
- Menus e botões visíveis apenas para perfis com permissão

## 🛠️ Tecnologias

- Java 17+
- Swing (GUI)
- JDBC (conexão com banco)
- MySQL Workbench
- Padrões: MVC + DAO


