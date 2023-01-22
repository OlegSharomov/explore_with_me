# Explore with me
### *Описание:*

Серверная часть сервиса-афиши, где можно предложить какое-либо событие от выставки до похода в кино и набрать компанию для участия в нём, реализованная на основе микросервисной архитектуры.

![Web Template](https://github.com/OlegSharomov/explore_with_me/blob/main/images/WebTemplate2.png)

### *Функциональность:*

Администраторы могут: создавать подборки с разными событиями, закреплять их на главной странице, публиковать события после проверки.

Любые пользователи могут: просматривать события по разным параметрам в коротком виде и просматривать конкретные события в полном виде.

Зарегистрированные пользователи могут: создавать события, создавать запросы на данные события и подтверждать их, набирать участников на эти события и комментировать их. При создании комментариев предусмотрена автоматическая проверка на стоп-слова.

Так же в проекте есть сервис статистики, который собирает данные о просмотрах событий.

### *Структура:*

Основной сервис содержит всю необходимую функциональность для работы с пользователями.
Сервис статистики хранит количество просмотров и позволяет делать различные выборки для анализа работы приложения.
У каждого сервиса своя база данных Postgresql, взаимодействие сервисов организовано через HttpClient.

![Database schema](https://github.com/OlegSharomov/explore_with_me/blob/main/images/DB.png)

### *Стек:*

- Java 11
- Spring Boot 
- Hibernate 
- Maven 
- PostgreSQL 
- Docker 
- Lombok 
- MapStruct 
- Swagger 
- Zalando Logbook.

### *Запуск:*
Для запуска проекта необходим установленный Maven и Docker на компьютере.
Скачиваем проект, в командной строке переходим в корневую директорию и в ней запускаем команды:
- mvn clean package
- docker compose up

При необходимости предоставить docker разрешение на доступ к файлам из проекта.

### *Спецификация:*

Для просмотра спецификации вы можете запустить проект и перейти по ссылке: 
>http://localhost:8080/swagger-ui.html

### *Аутентификация и авторизация:*

В данном проекте не реализовано.

Предполагается, что с внешним миром приложение связывается через отдельный сервис, который контактирует с системой аутентификации и авторизации, а затем перенаправляет запросы в сервисы
В настоящее время считается, что поступление запроса к закрытой или административной части API означает, что запрос успешно прошел аутентификацию и авторизацию
