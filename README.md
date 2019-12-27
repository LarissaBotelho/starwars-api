# starwars-api

Projeto Final Spring-boot

Desenvolver uma API que contenha os dados dos planetas da galáxia distante de Star
Wars.
• A API deve ser REST
• Para cada planeta, os seguintes dados devem ser obtidos do banco de dados
da aplicação, sendo inseridos manualmente: Nome Clima Terreno
• Para cada planeta também devemos ter a quantidade de aparições em filmes,
que podem ser obtidas pela API pública do Star Wars: https://swapi.co/
• A API deverá conseguir adicionar um planeta (com nome, clima e terreno),
listar planetas, buscar por nome, buscar por ID, remover planeta

Tecnologias a serem utilizadas:
• Spring Boot
• Java 8
• MongoDB (pode-se utilizar o Spring Data)
• Aconselha-se utilizar JPA

Funções necessárias:
• Consumir do https://swapi.co/
o Quando um planeta é salvo, há a comunicação com o site, que obtém o
número de aparições
• Obter os planetas inseridos (GET)
• Incluir planeta (POST - formato JSON)
• Buscar planeta pelo nome (GET)
• Buscar planeta por id (GET)
• Remover um planeta (por id - DELETE)

Dicas:
• Lembrar de inserir o mongo na lista de dependências ao criar o projeto
• Configurar o MongoDB no arquivo application.properties:
o spring.data.mongodb.host=localhost
o spring.data.mongodb.port=27017
o spring.data.mongodb.database=starwars
• Para busca na API Star Wars (SWAPI), definir uma variável para receber o
endereço https://swapi.co/api/planets/?search=
• Usar a função a função completeURL da classe StringBuilder da biblioteca
java.lang para obter a quantidade de aparições

“Um bom software é um software bem testado”

May the force be with you!
