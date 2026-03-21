# La doc

## Strucure du bordel

```
Order-service <-------> Kitchen-service <-------> Stock-service
                  |                         |
                  |                         |
 envoie une commande sur order_topics       |
                                            |
                            envoie une commande sur stock_topic
```

## Services

### Order-service

- Reçoit les commandes du client
- Regarde si le burger existe
- Si oui, envoie la commande sur order_topics

#### Fichiers importants

- order-service/src/main/java/eventdriven/kafka/orderservice/controller/OrderController.java : reçoit les requêtes HTTP et envoir les commandes avec OrderProducer
- order-service/src/main/java/eventdriven/kafka/orderservice/kafka/OrderProducer.java : envoie les commandes sur order_topics

### Kitchen-service

- Ecoute order_topics
- Prépare les commandes
- Envoie la commande sur stock_topic

#### Fichiers importants

- kitchen-service/src/main/java/eventdriven/kafka/kitchenservice/kafka/OrderConsumer.java : écoute order_topics, prépare les commandes et envoie les commandes avec OrderProducer
- kitchen-service/src/main/java/eventdriven/kafka/kitchenservice/kafka/OrderProducer.java : envoie les commandes sur stock_topic

### Stock-service

- Ecoute stock_topic
- Met à jour le stock

**Attention** : il faut changer le mot de passe et le nom d'utilisateur de la base de données dans application.properties avant de lancer le projet

#### Fichiers importants

- stock-service/src/main/java/eventdriven/kafka/stockservice/kafka/OrderConsumer.java : écoute stock_topic et met à jour le stock

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
2. Lancer la base de données avec docker compose sur le port 3306
3. Tout compiler avec `mvn clean install` dans le dossier parent
4. Lancer les services dans l'ordre : stock-service, kitchen-service, order-service avec `mvn spring-boot:run` dans chaque dossier respectif
5. Envoyer une requête HTTP pour créer une commande