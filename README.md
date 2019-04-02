# Gerenciador de Posto de Combustíveis

## Projeto Original
[<h4>PostoDeGasolina<h4>](https://github.com/gdwrt/PostoDeGasolina)

## Como executar (Projeto Original e Refatorado):

Primeiro executar o script [scriptDbPostoGasolina.txt](db/scriptDbPostoGasolina.txt) 

Depois só executar o arquivo Main.java

## Padrões:
* Builder
  * [Cliente_fisica](src/com/postoGasolina/model/Cliente_fisica.java)
  * [Cliente_juridica](src/com/postoGasolina/model/Cliente_juridica.java)
  * [Endereco](src/com/postoGasolina/model/Endereco.java)
  * [Item_pedido](src/com/postoGasolina/model/Item_pedido.java)
  
* Iterator
  * [Iterator](src/com/postoGasolina/iterator/Iterator.java)
  * [MenuDataIterator](src/com/postoGasolina/iterator/MenuDataIterator.java)
  
* Singleton
  * [ConexaoUtil](src/com/postoGasolina/dao/ConexaoUtil.java)
  
  
## Classes Afetadas
* Builder
  * Cliente_fisica
    * [ClienteFisicaDao](src/com/postoGasolina/dao/ClienteFisicaDao.java#L328)
    * [TelaGerenciarClienteController](src/com/postoGasolina/controller/TelaGerenciarClienteController.java#L318)
    
  * Cliente_juridica
    * [ClienteJuridicaDao](src/com/postoGasolina/dao/ClienteJuridicaDao.java#L222)
    * [TelaGerenciarClienteJuridica](src/com/postoGasolina/controller/TelaGerenciarClienteJuridica.java#L257)
    
  * Endereco
    * [ClienteFisicaDao](src/com/postoGasolina/dao/ClienteFisicaDao.java#L316)
    * [ClienteJuridicaDao](src/com/postoGasolina/dao/ClienteJuridicaDao.java#L211)
    * [FornecedoresDao](src/com/postoGasolina/dao/FornecedoresDao.java#L215)
    * [FuncionarioDao](src/com/postoGasolina/dao/FuncionarioDao.java#L257)
    * [OrgaoGovernamentalDao](src/com/postoGasolina/dao/OrgaoGovernamentalDao.java#L193)
    * [TelaGerenciarClienteController](src/com/postoGasolina/controller/TelaGerenciarClienteController.java#L306)
    * [TelaGerenciarClienteJuridica](src/com/postoGasolina/controller/TelaGerenciarClienteJuridica.java#L246)
    * [TelaGerenciarFornecedores](src/com/postoGasolina/controller/TelaGerenciarFornecedores.java#L176)
    * [TelaGerenciarFuncionariosController](src/com/postoGasolina/controller/TelaGerenciarFuncionariosController.java#L258)
    * [TelaGerenciarOrgaoGovernamental](src/com/postoGasolina/controller/TelaGerenciarOrgaoGovernamental.java#L166)
    
  * Item_pedido
    * [CompraDao](src/com/postoGasolina/dao/CompraDao.java#L168)
    * [TelaCompraController](src/com/postoGasolina/controller/TelaCompraController.java#L87)
    * [TelaVendaController](src/com/postoGasolina/controller/TelaVendaController.java#L151)
    * [VendaDao](src/com/postoGasolina/dao/Venda.java#L174)
    
* Iterator
  * MenuDataIterator
    * [TelaPrincipalController](src/com/postoGasolina/controller/TelaPrincipalController.java#L569)
    
* Singleton
  * ConexaoUtil
    * Afeta todas as classes em [dao](src/com/postoGasolina/dao)
  
