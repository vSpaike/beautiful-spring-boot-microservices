# La doc

## Strucure du bordel

```
                kafka
Order-service <-------> Orchestration-service
                           |             |
                      requête post       |
                           |        requête post
                           |             |
                   Kitchen-service   Stock-service
```

## Services

### Order-service

- Reçoit les commandes du client
- Regarde si le burger existe
- Si oui, envoie la commande sur order_topics
- Reçoit l'update de la commande sur update_topics

#### Fichiers importants

- order-service/src/main/java/eventdriven/kafka/orderservice/controller/OrderController.java : reçoit les requêtes HTTP et envoir les commandes avec OrderProducer
- order-service/src/main/java/eventdriven/kafka/orderservice/kafka/OrderProducer.java : envoie les commandes sur order_topics
- order-service/src/main/java/eventdriven/kafka/orderservice/kafka/OrderConsumer.java : écoute update_topics et affiche l'update de la commande

### Kitchen-service

- Prend les commandes sur l'endpoint /api/v1/cook_order
- Prépare les commandes
- N'a que 5 cuisiners, donc ne peut préparer que 5 commandes en même temps

#### Fichiers importants

- kitchen-service/src/main/java/eventdriven/kafka/kitchenservice/controller/OrderController.java : reçoit les requêtes HTTP pour préparer les commandes

### Stock-service

- Reçoit les commandes sur l'endpoint /api/v1/remove
- Met à jour le stock

#### Fichiers importants

- stock-service/src/main/java/eventdriven/kafka/stockservice/controller/OrderController.java : reçoit les requêtes HTTP pour mettre à jour le stock

## Requêtes HTTP

### POST http://loalhost:8083/api/v1/orders

```json
{
    "name": "Cheese Burger",
    "qty": 2,
    "price": 14
}
```

## Lancer le projet

1. Lancer Kafka avec docker compose sur le port 9092
3. Tout compiler avec `mvn clean install` dans le dossier parent
4. Lancer les services dans l'ordre : stock-service, kitchen-service, order-service avec `mvn spring-boot:run` dans chaque dossier respectif
5. Envoyer une requête HTTP pour créer une commande