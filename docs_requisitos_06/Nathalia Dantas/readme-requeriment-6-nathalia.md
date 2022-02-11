### 6º Requisito
**Objetivo:** Como comprador quero abrir um chamado para realizar a devolução de produtos comprados.

Cenário:  <br />
"O status da ordem de compra se encontra como DELIVERED. <br />
DADO QUE o comprador adicionou os produtos no carrinho de compras  <br />
E finalizou o pedido <br />
E o pedido foi entregue <br />
QUANDO o comprador desejar realizar a devolução de um ou mais produtos <br />
ENTÃO uma ordem de devolução deve ser criada <br />
E o comprador pode um saldo para uma próxima compra ou um novo produto <br />
E de acordo com o motivo da devolução esse produto deve ou não retornar ao estoque." <br />


**Endpoints:**
| HTTP | Modelo da URI | Descrição |
|--|--|--|
| GET | /api/v1/fresh-products/returnorders/?returnOrderId={id} | Retorna um pedido de devolução especificado. |
| POST | /api/v1/fresh-products/returnorders/ | Criação de um pedido de devolução  de itens contidos no pedido de compra.  |
|PUT| /api/v1/fresh-products/returnorders/?returnOrderId={id} | Permite editar um pedido de devolução específico, passando uma nova lista de itens e um novo valor para o motivo da devolução . |
|PUT| /api/v1/fresh-products/returnorders/cancel/?returnOrderId={id} | Permite o usuário cancelar o pedido de devolução especificado. |
