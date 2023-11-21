# 🚀 API_JOGOS (Gerenciamento de Jogos)

Trata-se de uma **API Rest** desenvolvida com o **Spring Boot Framework 3.1.5 (JAVA)**, com o objetivo de implementar um _gerenciamento de uma lista de jogos_, de forma que que seja possível realizar um _CRUD_ completo através dos métodos HTTP. Como também, foi utilizado o sistema de Hateoas para tornar a API mais intuitiva e seguindo os padrões RESTful.
    
# <img src="https://user-images.githubusercontent.com/25181517/183891303-41f257f8-6b3d-487c-aa56-c497b880d0fb.png" width="40" height="40" alt="Spring Boot"> Back-End 
### :pushpin: Requerimentos
- Java JDK - version 17
- Banco de Dados MySQL - version 8.0.33
- Maven
- Ambiente de Desenvolvimento - IDE
------------------------------------------------------------------------------------------------------
### :computer: Instalação e Execução
1. **Na sua IDE, clone o repositório e acesse a pasta principal:**
   - git clone https://github.com/yagoprazim/ApiJogos.git
   - cd .\ApiJogos

2. **Configure o banco de dados em src/main/resources:**
   - No diretório acima, você precisará criar um arquivo chamado: **_custom.properties_** - nele você colocará as informações do seu banco de dados MySQL, servindo para substituir as variáveis de ambiente declaradas no arquivo _properties_ principal. Exemplo:
     ```sql
      spring.datasource.url=jdbc:mysql://localhost:3306/db_controle_jogos?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
      spring.datasource.username=root
      spring.datasource.password=root
      spring.jpa.show-sql=true
     ```

3. **Baixando as dependências e executando a aplicação, no terminal:**
   - ./mvnw spring-boot:run
   - Por padrão, ele irá rodar no: http://localhost:8080/
     
4. **Após a aplicação rodar corretamente, verifique se foi criada a seguinte tabela no seu banco:**
   - _tb_jogo_

------------------------------------------------------------------------------------------------------
### <img src="https://user-images.githubusercontent.com/25181517/186711335-a3729606-5a78-4496-9a36-06efcc74f800.png" width="40" height="40" alt="Swagger"> Swagger 
- Para acessar o Swagger e testar os endpoints - Com o seu projeto rodando e o banco de dados corretamente configurado, acesse: http://localhost:8080/swagger-ui/index.html
- Nele você encontrará uma breve descrição sobre a funcionalidade de cada endpoint. Contudo, caso queira saber os detalhes principais sobre a estrutura do projeto, continue a leitura abaixo.
------------------------------------------------------------------------------------------------------
### :open_file_folder: Resumo da estrutura do projeto:
1. __MODELS__:
   - Contém as Entidades da API, no caso: **JogoModel**.
   - Além das declaração dos atributos/propriedades da model, foi implementado o Lombok para não termos que criar métodos getters, setters, etc manualmente, para isso, usamos algumas annotations, como: @Data.
   - Além disso, usamos annotations que vão servir para mapear a model como Entidade JPA, para que possa ser criada a tabela no banco de dados a partir das informações da Entidade. Para isso, utilizamos o Hibernate/JPA para que, ao executarmos a aplicação, seja gerada automaticamente a tabela no banco.
   - Ademais, herdamos de _RepresentationModel_ para lidarmos com o sistema de Hateoas.

2. __DTO's__:
   - Para encapsulamento dos dados e mais liberdade para a definição correta dos dados que precisam ser passados no _body_ das _requests_, como também daqueles que são recebidos no _body_ das _responses_, foram implementados os dto's.
   - Foram criados 2 _packages_: Request e Response. O motivo fica mais claro com um exemplo: para criar um jogo, o que é preciso passar na requisição é diferente do que é preciso ser retornado (a quantidade e alguns tipos de dados mudam), portanto,
     na requisição, preciso apenas de:
     ```json
     //REQUEST
     {
      "titulo": "Cyberpunk2077",
      "genero": "RPG",
      "desenvolvedora": "CD Projekt Red",
      "plataforma": "Steam",
      "preco": 250,
      "descricao": "Trata-se de um RPG Offline em primeira pessoa."
     }
     //O id do Jogo é auto incremental e o Hiperlink é definido na lógica de negócio.
     ```
     Enquanto que na resposta, quero que retorne:
      ```json
     //RESPONSE
     {
      "id": 14,
      "titulo": "Cyberpunk2077",
      "genero": "RPG",
      "desenvolvedora": "CD Projekt Red",
      "plataforma": "Steam",
      "preco": 250,
      "descricao": "Trata-se de um RPG Offline em primeira pessoa.",
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8080/api/jogos/14"
        }
      ]
     }
     ```
     --> Com o uso de DTO's, conseguimos controlar bem isso, por isso foram criados DTO's para Response e outros para Request.
   - Além disso, foi criado outro DTO para caso específico: para personalizar as respostas do tratamento dos erros 400 gerais, que são puxados a partir das annotations definidas nos DTO's de _Resquest_ e _Response_.

  3. __MAPPERS__:
     - Para facilitar o mapeamento e conversão dos dados entre DTO's e Entidade, utilizei o MapStruct, o qual, dentre os mapeadores que existem, ele possui bastante opções de controle e boa performace. 
     - Foi criado: JogoMapper - em resumo, essa interface serve para sempre que precisarmos, chamarmos métodos para converter e mapear os dados automaticamente, seja de uma Model para um DTO, vice-versa, como também, a lógica de mapeamento das Hateoas foi implementada nela.

  4. __REPOSITORIES__:
     - Temos a interface: JogoRepository, servindo para herdarmos de JpaRepository e termos acesso aos métodos para realizar queries com o banco de dados.

  5. __SERVICES__:
     - Temos: JogoService, servindo para criar as regras de negócio e utilizar do mapeamento para lidar com os dados entre Entidade e Dto's; implementando também o repository para chamamento dos métodos de CRUD que serão utilizados pelo Controller.
     - Em JogoService, temos os métodos para: listarTodosOsJogos; listarUmJogo; registrarJogo; atualizarJogo; deletarJogo.
     - Optei pela escrita verbosa dos métodos para facilitar o entendimento do que cada um faz.
  
  6. __CONTROLLERS__:
     - Basicamente, implementa tudo que criamos nos services, tornando tudo funcional através de métodos HTTP (Get, Post, Put, Delete)... Para facilitar, deixei os nomes de cada método dos controllers iguais aos dos services, seguindo um padrão. Eles estão bem simples e com poucas linhas de código, justamente por causa da forma como o projeto foi estruturado, para não colocar tanta informação nos controllers.
     - Inclusive, optei por usar @RequiredArgsConstructor ao invés de @Autowired.
     - Tratamentos básicos, como: para verificar se um jogo existe ou não, ou se determinado dado está nulo ou não, etc, não passei isso no controller, ficando ele responsável apenas pelo chamamento dos services e ResponseEntity de forma simples - deixei essas responsabilidades de tratamento nos packages de: services + exceptions. Enquanto no controller apenas passei os statuscode de sucesso (200, 201, 204)...
  
  7. __EXCEPTIONS:
     - Em Exceptions, tratei as exceções básicas, de forma que no caso de erro 404, poderei personalizar as respostas de erro a partir das mensagens que coloquei nos services.
     - Em casos de erro 400 gerais, utilizei o dto 'ErrosListaDto' para gerarmos uma lista de erros tratados a partir do uso das annotations (@NotBlank, @NotNull, etc), de forma que na lista de erros, são retornadas mensagens personalizadas que eu defini. Como também, criei mais uma exception específica para tratamento de erro 400, para validar e personalizar a mensagem de erro que trata a restrição _'Unique'_ do título de um jogo.

  8. __CONFIGURATIONS__:
     - Contém o arquivo de configurações do Swagger e do CORS para consumo da API.
------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------






